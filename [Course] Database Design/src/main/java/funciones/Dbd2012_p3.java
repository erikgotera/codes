// Exemple DataNucleus senzill. "Hello World" per a DataNucleus. 
// No gaire ben estructurat.

package funciones;

//import datos.Empleat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.Scanner;

public class Dbd2012_p3
{
    private FuncionsBD funciones; // Classe que té les funcions per accedir a la
    // base de dades (SQL, etc)
           
    public static void main(String args[])
    {
        Dbd2012_p3 prova = new Dbd2012_p3();
         while(true) {
            System.out.println("\n\n--------------------------------"
                    + "\nQuè voleu fer?"
                    + "\n(1) _Carregar_ el fitxer amb  dades"
                    + "\n(2) _Esborrar_ les dades de les taules"
                    + "\n(3) _Mostrar_ regles..."
                    + "\n(0) _Sortir_");
            Scanner scanner = new Scanner(System.in);
            String queFer;
            queFer = scanner.nextLine();
            if (queFer.equalsIgnoreCase("carregar") || queFer.equals("1")) {
                prova.readFile();
            }
            else if (queFer.equalsIgnoreCase("esborrar") || queFer.equals("2")) {
                prova.dropDatabase();
            }
            else if (queFer.equalsIgnoreCase("mostrar") || queFer.equals("3")) {
                System.out.println("\n\n--------------------------------"
                        + "\nQui registres voleu utilitzar per a generar les regles?"
                        + "\n(1) Tots els _registres_"
                        + "\n(2) Registres d'un determinat pacient"
                        + "\n(3) Registres per franja horària"
                        + "\n(4) Registres per localizació (coordenades x, y, z)"
                        + "\n(0) Tornar _enrere_");
                        String queMostrar;
                        queMostrar = scanner.nextLine();
                        if (queMostrar.equalsIgnoreCase("registres") || queMostrar.equals("1")) {
                            prova.show("records");
                        }
                        else if (queMostrar.equals("2")) {
                            prova.show("recordsFromPatient");
                        }
                        else if (queMostrar.equals("3")) {
                            prova.show("recordsFromTime");
                        }
                        else if (queMostrar.equals("4")) {
                            prova.show("recordsFromCoord");
                        }
            }
            else if (queFer.equalsIgnoreCase("sortir") || queFer.equals("0")) {
                break;
            }
            System.out.println("\n\n");
         }

    }
  
    Dbd2012_p3() {
        funciones = new FuncionsBD();
        
    }
    
    private void dropDatabase() {
         try {
           funciones.dropDatabase();
         }
         catch(Exception e){
         }
    }
    
    // Función para centralizar todas las llamadas a show
    private void show(String categoryToDisplay) {
         try {
            Scanner scanner = new Scanner(System.in);
            String filter = "";
            if(categoryToDisplay.equals("recordsFromPatient")) {
                 System.out.println("\n\n--------------------------------"
                         + "\nIndica quin pacient vols (A01, A02, ..., C01, ..., E05)");
                 String patient;
                 patient = scanner.nextLine();
                 filter = "sequence_name == '" + patient + "'";
            }
            else if(categoryToDisplay.equals("recordsFromTime")) {
                 System.out.println("\n\n--------------------------------"
                         + "\nIntroduïu una hora del dia (per exemple, en introduir 15 es buscarà els registres entre les 3 i les 4 de la tarda):");
                 int hour;
                 hour = scanner.nextInt();
                 
                 int seconds = hour*3600; // Segundos desde medianoche en la hora de inicio
                 int secondsAfter = seconds + 3600; // Segundos desde medianoche en la hora final (hora de inicio + 1 hora)
            
                 System.out.println("Resultats entre el segon " + seconds + " i el " + secondsAfter + " del dia.\n");
                 
                 // timestamp % 86400000 = els milisegons que han passat des de l'inici del dia
                 filter = "timestamp % 86400000 > " + seconds + " && timestamp % 86400000 < " + secondsAfter;
            }
            else if(categoryToDisplay.equals("recordsFromCoord")) {
                 System.out.println("\n\n--------------------------------"
                         + "\nIndiqueu 3 coordenades (una per línia, en el següent ordre: x, y, z), per exemple x=4, y=2 z=0:");
                 
                 // Agafem les dades
                 double x, y, z;
                 x = scanner.nextDouble();
                 y = scanner.nextDouble();
                 z = scanner.nextDouble();
                 
                 // Definim l'interval que permetem
                 double min_x = x-0.5;
                 double max_x = x+0.5;
                 double min_y = y-0.5;
                 double max_y = y+0.5;
                 double min_z = z-0.5;
                 double max_z = z+0.5;

                 // que estiguin entre el màxim i el mínim
                 filter = "coord_x > " + min_x + " &&"
                        + "coord_x < " + max_x + " &&"
                        + "coord_y > " + min_y + " &&"
                        + "coord_y < " + max_y + " &&"
                        + "coord_z > " + min_z + " &&"
                        + "coord_z < " + max_z;
            }
            
            // Preguntem si vol utilitzar el SON
            System.out.println("\n\n--------------------------------"
                    + "\nVoleu utilitzar el SON algorithm? Introduïu un 0 si no el voleu utilitzar o un 1 si sí que el voleu.");
            int son;
            son = scanner.nextInt();
            
            // Anem a un mètode o altre segons el que ens hagi dit
            if (son == 0)
                funciones.showRecordsFromFilter(filter);
            else if (son == 1)
                funciones.showRecordsFromFilterOptimitzed(filter);
            else
                 System.out.println("Valor incorrecte, havíeu d'introduir 0 ò 1.");
         }
         catch(Exception e){
         }
    }
    
    private void readFile() {
        //File fichero = new File("C:\\ConfLongDemo_JSI.txt");
        File fichero = new File("/home/albert/Escriptori/ConfLongDemo_JSI.txt");
        BufferedReader in = null;
        
         try {
             in = new BufferedReader(new InputStreamReader(new FileInputStream(fichero))); 
             // Lectura del fichero
             String linea;
             int total=0;
             System.out.println("Añadiendo datos a la BD...\n");
             
             while ((linea = in.readLine()) != null) {
                 total = total + 1;                 
                    if (!linea.isEmpty()){
                          procesarFichero(linea);
                          System.out.println("Procesando linea ("+total+")");
                    }
              }
              System.out.println("S'han afegit: "+total+" registres correctament");
         }
         catch(Exception e){
            e.printStackTrace();
        }finally{
             // En el finally cerramos el fichero, para asegurarnos
             // que se cierra tanto si todo va bien como si salta 
             // una excepcion.
             try{                    
                if (in != null) {
                        in.close();
                  }                
             }catch (Exception e2){ 
                e2.printStackTrace();
             }    
        }
    }
    
    private void procesarFichero(String linea) {
        String body_part = null;
        int activity = 0;
        String[] fields = linea.split(",");

        
        //MAPEO DE CAMPOS
        if("010-000-024-033".equals(fields[1]))
        {
            body_part = "ANKLE_LEFT";
        }else if("010-000-030-096".equals(fields[1])){
            body_part = "ANKLE_RIGHT";
        }else if("020-000-033-111".equals(fields[1])){
            body_part = "CHEST";
        }else if("020-000-032-221".equals(fields[1])){
            body_part = "BELT";
        }

        if("walking".equals(fields[7]))
        {
            activity = 1;
        }else if("falling".equals(fields[7])){
            activity = 2;
        }else if("lying down".equals(fields[7])){
            activity = 3;
        }else if("lying".equals(fields[7])){
            activity = 4;
        }else if("sitting down".equals(fields[7])){
            activity = 5;
        }else if("sitting".equals(fields[7])){
            activity = 6;
        }else if("standing up from lying".equals(fields[7])){
            activity = 7;
        }else if("on all fours".equals(fields[7])){
            activity = 8;
        }else if("sitting on the ground".equals(fields[7])){
            activity = 9;
        }else if("standing up from sitting".equals(fields[7])){
            activity = 10;
        }else if("standing up from sitting on the ground".equals(fields[7])){
            activity = 11;
        }
        
         long timestamp = Long.parseLong(fields[2]);
         double cordx = Double.parseDouble(fields[4]);
         double cordy = Double.parseDouble(fields[5]);
         double cordz = Double.parseDouble(fields[6]);

        funciones.addRegistro(fields[0], fields[1], timestamp, fields[3], cordx, cordy, cordz, fields[7], body_part, activity);
    }
}
