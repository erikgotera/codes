/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

import datos.Activities;
import datos.Body_parts;
import datos.Records;
import funciones.Rule;
import funciones.Rule.Pair;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import weka.associations.Apriori;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.supervised.attribute.Discretize;
import weka.filters.Filter;

        
        
/**
 *
 * @author Administrador
 */
class FuncionsBD {
    private static String INPUTDIRECTORY = "data";
    protected PersistenceManagerFactory pmf;
    protected PersistenceManager pm;
    protected Transaction tx;
    
    public FuncionsBD() {
         try {
            // Create a PersistenceManagerFactory for this datastore
            pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
            System.out.println("DataNucleus: Exemple amb JDO");
            System.out.println("============================");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }

    void addRegistro(String seq_name, String body_part, long timestamp, String date, double cordx, double cordy, double cordz, String activity, String name_body, int codi_act) 
    { 
        try
        {
            tx.begin();
           
            Body_parts body = null;
            Activities acti = null;
            boolean bodyadd = false;
            boolean actadd = false;
            Query q=pm.newQuery("SELECT tag FROM " + Body_parts.class.getName() + " where tag=='"+body_part+"'");
            List<Body_parts> bodys = (List<Body_parts>)q.execute();
            if(bodys.isEmpty())
            {
                //Body_parts body;
                body = new Body_parts(body_part,name_body);
                pm.makePersistent(body);

                bodyadd=true;
                
            }
            
            q=pm.newQuery("SELECT id FROM " + Activities.class.getName() + " where id=="+codi_act);
            List<Activities> acts = (List<Activities>)q.execute();
            if(acts.isEmpty())
            {
                acti = new Activities(codi_act,activity);
                pm.makePersistent(acti);

                actadd=true;
            }
            
                Records rec;
                rec = new Records(seq_name, timestamp, date, cordx, cordy, cordz, codi_act);
                if(bodyadd==false)
                {
                    // Agafem totes lse body_parts
                    q = pm.newQuery(Body_parts.class);

                    q.setFilter("tag == '" + body_part+"'");
                    List<Body_parts> results = (List<Body_parts>)q.execute();
                    body = results.get(0);
                }
                body.afegirTag(rec);
                if(actadd==false)
                {
                    // Agafem totes les Activities
                    q = pm.newQuery(Activities.class);

                    q.setFilter("id == " + codi_act);
                    List<Activities> results = (List<Activities>)q.execute();
                    acti = results.get(0);
                }
                acti.afegirId(rec);
                //pm.makePersistent(rec);
            
            
             tx.commit();
            
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
            pm = pmf.getPersistenceManager();
            tx = pm.currentTransaction();
        }
    }
    
    Double getConfianza() throws IOException {
        BufferedReader bf = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("***************************************");
        System.out.println("\n--> CONFIANÇA <--\n");
        System.out.println("La confiança es llegeix de la següent manera:\n"
                + "quin percentatge (en tant per u) d'entrades que inclouen el conjunt d'elements I també inclouen l'element j.\n"
                + "Com més gran sigui el valor (sempre entre 0 i 1) menys resultats s'obtindran.\n");
        System.out.println("\nIntroduïu un nou valor per a la confiança:");
        Double confianza = Double.parseDouble(bf.readLine());
        return confianza;
    }
    Double getSupport() throws IOException {
        BufferedReader bf = new BufferedReader (new InputStreamReader(System.in));
        System.out.println("\n--> SUPORT <--\n");
       System.out.println("El suport d'un element o un conjunt d'elements és el número de registres on apareix l'element\n"
                + "o el conjunt d'elements d'entre tots els registres.");
        System.out.println("Introduïu un nou valor per al límit inferior del suport mínim:");
        Double support = Double.parseDouble(bf.readLine());
        return support;
    }
    void printInterest() {
        System.out.println("\n--> INTERÈS <--\n");
        System.out.println("L'interès és la diferència entre la confiança d'una associació (I --> j) i el percentatge d'entrades\n"
                + "que inclouen j. Així es penalitza els valors que apareixen molt freqüentment en qualsevol entrada.\n");
    }
    
    //Muestra los registros de un determinado paciente
    void showRecordsFromFilterOptimitzed(String filter) {
        // Basic Extent of all Activities
        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        String ficheroCsv = INPUTDIRECTORY+"\\fromFilterOptimitzed.csv";
        String ficheroArff = INPUTDIRECTORY+"\\fromFilterOptimitzed.arff";
        String discArff = INPUTDIRECTORY+"\\fromFilterOptimitzed(disc).arff";
        
        
        try {
            // Agafem tots els registres
            Query q2 = pm.newQuery(Records.class);
            // que siguin del pacient demanat
            //q2.setFilter(filter);
            List<Records> results = (List<Records>)q2.execute();
            
            int counter = 0; // posem un comptador per saber quants resultats hem mirat
            List<Records> chunk_results = new ArrayList<Records>(); // trossos de 100 resultats
            
            HashSet<Rule> rulesHashSet = new HashSet<Rule>(); // aquí guardarem les regles que anem trobant
            
            
            // Agafem les dades que utilitzarem per l'aPriori
            Double confianza = getConfianza();
            Double support = getSupport();
            printInterest();
            
            // Passada 1 per trobar les regles candidates a partir dels chunks
            Iterator<Records> it2 = results.iterator();
            while (it2.hasNext()) {
                counter++; // pugem el comptador
                Records r = it2.next(); // agafem el següent resultat
                chunk_results.add(r); // l'afegim al chunk
                
                if (counter == 1000 || !it2.hasNext()) { // mirem si ja en portem 1000 o estem a l'últim
                    System.out.println("Nou chunk");
                    // Aplicarem a priori a cada chunk
                    BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroCsv));
                    
                    //Llamamos al metodo que pone los encabezados en el csv
                    bw = camposCsv(bw);
                    
                    // iterem dins el chunck
                    Iterator<Records> it3 = chunk_results.iterator();
                    while (it3.hasNext()) {
                        Records r3 = it3.next();
                        bw.write(r3.toString()+"\n");
                    }
            
                    bw.close();
                    
                    //Llamamos al metodo que nos crea el arff a partir del csv
                    csvToArff(ficheroCsv, ficheroArff);

                    //Llamamos al metodo que discretiza el archivo arff creado anteriormente
                    //y guardamos el archivo discretizado en otro arff
                    discretizar(ficheroArff,discArff);
                    
                    //Aplicamos Apriori
                    Apriori ruleLearner = applyApriori(discArff, confianza, support);
                    
                    // Imprimim les regles
                    String rulesString = ruleLearner.toString();
                    String rulesStringLines[] = rulesString.split("\\r?\\n");
                    
                    Boolean reading = false;
                    
                    // Parser
                    for (int i = 0; i < rulesStringLines.length; i++) {
                        if (reading && rulesStringLines[i].contains(" ==> ")) { // mirem que sigui una regla
                            
                            // la dividim en dos
                            String rulesInTwo[] = rulesStringLines[i].split(" ==> ");
                            
                            // separem la part de l'esquerra en espais
                            String rulesLeft[] = rulesInTwo[0].split(" ");
                            
                            // creem una llista de regles de l'esquerra
                            List<String> rulesLeftList = new ArrayList<String>();
                            
                            // dividim la part de la dreta pels espais
                            String rulesRight[] = rulesInTwo[1].split("    ");
                            
                            for (int j = 0; j < rulesLeft.length; j++) {
                                if (rulesLeft[j].contains("=")) { // si és una relació atribut-valor
                                   rulesLeftList.add(rulesLeft[j]); // l'afegim a la llista
                                }
                            }
                            String rulesRightLast[] = rulesRight[0].split(" "); // dividim per l'espai
                            Rule rule = new Rule(rulesLeftList,rulesRightLast[0]); // creem una regla
                            rulesHashSet.add(rule);
                        }
                        
                        if (rulesStringLines[i].equals("Best rules found:")) // llegeix a partir d'aquesta línia
                            reading = true;
                    }
                    System.out.println("************************************************************");
                    chunk_results.clear(); // buidem el chunk
                    counter = 0; // posem el comptador a 0
                }
            }
            
            // Imprimim les regles candidates
            Iterator it3;
            it3 = rulesHashSet.iterator(); // unió de les regles de tots els chuncks
            System.out.println("Regles candidates:");
            while (it3.hasNext()) {
                System.out.println(it3.next());  
            }
            
            // passem el Hashset a una llista
            List<Rule> rulesList = new ArrayList<Rule>(rulesHashSet);
            
            // Passada 2 per mirar si les regles són suficientment freqüents
            // Iterem per tots els registres
            Iterator<Records> it4 = results.iterator();
            while (it4.hasNext()) { // per cada registre
                Records r = it4.next();
                
                // iterem per totes les regles
                Iterator it5;
                it5 = rulesList.iterator(); // unió de les regles de tots els chuncks
                counter = 0; // per saber a quina posició estem de la llista
                while (it5.hasNext()){
                    Rule rule = (Rule)it5.next();
                    
                    List<Pair> leftPairs = rule.leftRules; // regles de l'esquerra
                    Pair rightPair = rule.rightRules; // regla de la dreta
                    
                    // iterem per la llista de regles de l'esquerra
                    Iterator it6 = leftPairs.iterator();
                    boolean ok = true; // hi són totes?
                    while (it6.hasNext() && ok) { // mentre en quedin i totes les anteriors hi fossin
                        Pair leftPair = (Pair)it6.next(); // itera
                        ok = hasPair(r,leftPair); // mira si el registre conté el parell
                    }
                    if (ok) { // si totes les de l'esquerra hi eren
                        ok = hasPair(r,rightPair); // mira si la de la dreta també hi és
                    }
                    if (ok) { // si tots els parells hi eren, la regla es compleix, li hem de sumar 1
                        System.out.println("OK: ");
                        System.out.println(r.toString());
                        System.out.println(rule.toString());
                        rulesList.get(counter).addOne();
                    }
                    else {
                        System.out.println("NO OK: ");
                        System.out.println(r.toString());
                        System.out.println(rule.toString());
                    }
                }
            }
            
            // El número total de registres
            int numOfRecords = rulesList.size();
            
            // Imprimim les regles candidates després de comptar les coincidències
            Iterator it7;
            it7 = rulesList.iterator(); // unió de les regles de tots els chuncks
            System.out.println("Regles candidates amb el nombre de vegades que s'ha repetit:");
            while (it7.hasNext()) {
                Rule ruleToPrint = (Rule)it7.next();
                System.out.println(ruleToPrint + "Confiança: " + ruleToPrint.counter/numOfRecords);
            }
            
            q2.close(results); // Tancar
        } catch (Exception ex) {
                System.err.println(ex.getMessage());
        }
    }
    
    // et diu si un registre "r" conté un parell (atribut-valor) "pair"
    boolean hasPair(Records r, Pair pair) {
        if (pair.attribute.equals("body_part")) {
            if (r.getBody().getTag().equals(pair.value)) {
                return true;
            }
        }
        else if (pair.attribute.equals("sequence_name")) {
            if (r.getSequence_name().equals(pair.value)) {
                return true;
            }
        }
        else if (pair.attribute.equals("activity")) {
            if (r.getActivity().getName().equals(pair.value)) {
                return true;
            }
        }
        else if (pair.attribute.equals("timestamp")) {
            long min = parseMin(pair.value).longValue();
            long max = parseMax(pair.value).longValue();
            if (r.getTimestamp() > min && r.getTimestamp() < max) {
                return true;
            }
        }
        else if (pair.attribute.equals("coord_x")) {
            Double min = parseMin(pair.value);
            Double max = parseMax(pair.value);
            if (r.getCoord_x() > min && r.getCoord_x() < max) {
                return true;
            }
        }
        else if (pair.attribute.equals("coord_y")) {
            Double min = parseMin(pair.value);
            Double max = parseMax(pair.value);
            if (r.getCoord_y() > min && r.getCoord_y() < max) {
                return true;
            }
        }
        else if (pair.attribute.equals("coord_z")) {
            Double min = parseMin(pair.value);
            Double max = parseMax(pair.value);
            if (r.getCoord_z() > min && r.getCoord_z() < max) {
                return true;
            }
        }
        return false;
    }
    
    // Los siguientes métodos parsean Strings cómo estos:
    // '(6.3379022611088E17-6.3379022625044365E17]'
    // '(-inf-6.3379022645358221E17]'
    // '(6.3379022656994368E17-inf)'
    // y devuelve el número mínimo y el máximo
    Double parseMin(String original) {
        // Obtindrem el mínim i el màxim separant els dos valors pel guió (-)
        
        // Abans, hem de substituir tots els menys (-) per NEG per no confondre-ho amb guions
        original = original.replace("(-", "(NEG");
        original = original.replace("--", "-NEG");
        String[] limits = original.substring(2, original.length()-2).split("-"); // traiem (', ') i dividim pel -
        limits[0] = limits[0].replaceAll("NEG", "-"); // tornem a posar menys on hi eren
        Double min;
        if (limits[0].equals("-inf")) {
            min = Double.MIN_VALUE;
        }
        else {
            min = Double.valueOf(limits[0]);
        }
        return min;
    }
    Double parseMax(String original) {
        original = original.replace("(-", "(NEG");
        original = original.replace("--", "-NEG");
        String[] limits = original.substring(2, original.length()-2).split("-");
        limits[1] = limits[1].replaceAll("NEG", "-");
        Double max;
        if (limits[1].equals("inf")) {
            max = Double.MAX_VALUE;
        }
        else {
            max = Double.valueOf(limits[1]);
        }
        return max;
    }
    
    // Muestra los registros a partir de un filtro dado
    void showRecordsFromFilter(String filter) {
        // Basic Extent of all Activities
        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        String ficheroCsv = INPUTDIRECTORY+"\\fromFilter.csv";
        String ficheroArff = INPUTDIRECTORY+"\\fromFilter.arff";
        String discArff = INPUTDIRECTORY+"\\fromFilter(disc).arff";
        
        try {
            // Agafem tots els registres
            Query q2 = pm.newQuery(Records.class);
            
            // Apliquem el filtre a la query
            q2.setFilter(filter);
            
            // Executem la query
            List<Records> results = (List<Records>)q2.execute();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroCsv));

            //Llamamos al metodo que pone los encabezados en el csv
            bw = camposCsv(bw);
            
            // Iterem sobre tots els resultats
            Iterator<Records> it2 = results.iterator();
            while (it2.hasNext()) {
                Records r = it2.next();
                //System.out.println(">  " + r);
                bw.write(r.toString()+"\n");
            }
            
            bw.close();
            System.out.println("************************************************************");
            
            //Llamamos al metodo que nos crea el arff a partir del csv
            csvToArff(ficheroCsv, ficheroArff);
            
            //Llamamos al metodo que discretiza el archivo arff creado anteriormente
            //y guardamos el archivo discretizado en otro arff
            discretizar(ficheroArff,discArff);
            
            // Agafem les dades que utilitzarem per l'aPriori
            Double confianza = getConfianza();
            Double support = getSupport();
            printInterest();
            
            //Aplicamos Apriori
            Apriori ruleLearner = applyApriori(discArff, confianza, support);
            
            System.out.println(ruleLearner);
            
            q2.close(results); // Tancar
        } catch (Exception ex) {
                System.err.println(ex.getMessage());
        }
    }
    //Limpia de registros las tablas de toda la Base de datos    
    void dropDatabase() {
        System.out.println("Atenció, la següent acció pot tardar alguns minuts.");
        
        // Esborrem els registres
        Query q = pm.newQuery(Records.class);
        long numberInstancesDeleted = q.deletePersistentAll();
        System.out.println("S'han esborrat correctament els registres (" + numberInstancesDeleted + " instàncies).");  
        
        // Esborrem les parts del cos
        q = pm.newQuery(Body_parts.class);
        numberInstancesDeleted = q.deletePersistentAll();
        System.out.println("S'han esborrat correctament els projectes (" + numberInstancesDeleted + " instàncies).");  

        // Esborrem les activitats
        q = pm.newQuery(Activities.class);
        numberInstancesDeleted = q.deletePersistentAll();
        System.out.println("S'han esborrat correctament les activitats (" + numberInstancesDeleted + " instàncies).");
        
        System.out.println("S'ha esborrat tot satisfactòriament.");
    }
    
    public Apriori applyApriori(String dataSetName, Double conf, Double sup) throws Exception {
        Apriori ap = new Apriori();
        Instances data2 = DataSource.read(dataSetName);
        
        ap.setMinMetric(conf); // posem la confiança
        ap.setLowerBoundMinSupport(sup); // posem el suport
        ap.setNumRules(999); // que retorni 999 regles com a màxim
        ap.buildAssociations(data2); // construeix les regles
        ap.setClassIndex(3); //indicamos el atributo clase
        
        ap.mineCARs(data2);

        return ap;
    }
    
    protected static Instances load(String filename) throws Exception {
     Instances       result;
     BufferedReader  reader;
 
     reader = new BufferedReader(new FileReader(filename));
     result = new Instances(reader);
     reader.close();
 
     return result;
   }
   
   protected static void save(Instances data, String filename) throws Exception {
     BufferedWriter  writer;
 
     writer = new BufferedWriter(new FileWriter(filename));
     writer.write(data.toString());
     writer.newLine();
     writer.flush();
     writer.close();
   }
   
   //Metodo que pasa de csv a arff
   protected static void csvToArff(String csv, String arff) throws IOException
   {
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(csv));
        Instances data = loader.getDataSet();
        data.deleteAttributeAt(5);//Eliminamos el campo date ya que utilizamos el de timestamp 
        data.deleteAttributeAt(0);//Eliminamos el campo id_records
        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(arff));
        saver.writeBatch();
   }

    //Metodo que discretiza un arff y lo guarda en otro arff
    private void discretizar(String arff, String arffDisc) throws Exception {
        Instances inputTrain;
        Instances outputTrain;
        Discretize filter;
        
        inputTrain = load(arff);
        //indicamos A weka cual es tu atributo "clase"
        inputTrain.setClassIndex(2);

        // setup filter
         filter = new Discretize();
         filter.setInputFormat(inputTrain);

         //apply filter
         outputTrain = Filter.useFilter(inputTrain, filter);

         //save output
         save(outputTrain, arffDisc);
    }

    //Metodo que pone el encabezado en los csv que se creen
    private BufferedWriter camposCsv(BufferedWriter bw) throws IOException {
        //OBTENCION DE LOS NOMBRES DE CADA CAMPO
        int i = 0;
        int total = Records.class.getDeclaredFields().length; 
        int nocontables = 0;

        //Bucle que contabilizara los campos que no hay que obtener
        for (int x=0; x < Records.class.getDeclaredFields().length; x++)
        {
            if(Records.class.getDeclaredFields()[x].toString().indexOf("jdo")!= -1)
                nocontables ++;
        }
        total = total - nocontables;

        //Escribimos en el csv la linea con los campos con la coma como separador
        for (int x=0; x < Records.class.getDeclaredFields().length; x++)
        {
            i = i+1;
            if(!(Records.class.getDeclaredFields()[x].toString().indexOf("jdo")!= -1))
            {
                if(i == total)
                {
                    bw.write(Records.class.getDeclaredFields()[x].getName().toString()+"\n");
                }else
                {
                    bw.write(Records.class.getDeclaredFields()[x].getName().toString()+",");
                }
            }
        }
        return bw;
    }
}
        

    

