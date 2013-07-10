package datos;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Body_parts {
    @PrimaryKey
    private String tag=null;
    String name=null;
    private Set<Records> tagCol = null;
    
    
    public Body_parts(String nouTag, String nouName) {
        tag = nouTag;
        tagCol = new HashSet<Records>();
        name = nouName;
    }

    private Body_parts(){}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
     public void afegirTag(Records rec){        
        rec.setBody(this);
        tagCol.add(rec);
        // Modificar els dos costats de la relació        
    }

    public void treureTag(Records rec){        
        rec.setBody(null);
        tagCol.remove(rec);
        // Modificar els dos costats de la relació        
    }
 

    public String toString() {
        return "Body_parts{" + "tag=" + tag + ", name=" + name + '}';
    }


    
}
