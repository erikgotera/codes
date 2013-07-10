package datos;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Activities {
    @PrimaryKey
    int id=0;
    String name=null;
    private Set<Records> idCol = null;
    
    
    public Activities(int nouId, String nouName) {
        id = nouId;
        idCol = new HashSet<Records>();
        name = nouName;
    }

    private Activities(){}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void afegirId(Records rec){        
        rec.setActivity(this);
        idCol.add(rec);
        // Modificar els dos costats de la relació        
    }

    public void treureId(Records rec){        
        rec.setActivity(null);
        idCol.remove(rec);
        // Modificar els dos costats de la relació        
    }
    

    public String toString() {
        return "Activities{" + "id_activities=" + id + ", name= " + name + '}';
    }
    
}