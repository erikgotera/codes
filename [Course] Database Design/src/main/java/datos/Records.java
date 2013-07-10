package datos;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Records {
   @PrimaryKey
   long id_record;
   String sequence_name=null;
   private Body_parts body_part = null;
   private Activities activity = null;
   long timestamp;
   String date=null;
   double coord_x;
   double coord_y;
   double coord_z;

   
    public Records(String nouSequence_name,long nouTimestamp, String nouDate, double nouCoord_x, double nouCoord_y, double nouCoord_z, int nouActivity) {
        this.sequence_name = nouSequence_name;
        this.timestamp = nouTimestamp;
        this.date = nouDate;
        this.coord_x = nouCoord_x;
        this.coord_y = nouCoord_y;
        this.coord_z = nouCoord_z;
    }
       
    public Body_parts getBody() {
        return body_part;
    }

    public void setBody(Body_parts body) {
        this.body_part = body;
    }
    
    public Activities getActivity() {
        return activity;
    }

    public void setActivity(Activities act) {
        this.activity = act;
    }
    
    public double getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(double coord_x) {
        this.coord_x = coord_x;
    }

    public double getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(double coord_y) {
        this.coord_y = coord_y;
    }

    public double getCoord_z() {
        return coord_z;
    }

    public void setCoord_z(double coord_z) {
        this.coord_z = coord_z;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId_record() {
        return id_record;
    }

    public void setId_record(long id_record) {
        this.id_record = id_record;
    }

    public String getSequence_name() {
        return sequence_name;
    }

    public void setSequence_name(String sequence_name) {
        this.sequence_name = sequence_name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return id_record + "," + sequence_name + "," + body_part.getTag() + "," + activity.getName() + "," + timestamp + "," + date + "," + coord_x + "," + coord_y + "," + coord_z ;
    }
   
    /*public String toString() {
        return "Records{" + "id_record=" + id_record + ", sequence_name=" + sequence_name + ", body_part=" + body_part.getTag() + ", timestamp=" + timestamp + ", date=" + date + ", coord_x=" + coord_x + ", coord_y=" + coord_y + ", coord_z=" + coord_z + ", activity=" + activity.getName() + '}';
    }*/
   
}
