package christoph;
 
public class Enrollment {
    
    private DatabaseConnection dbconn;
    
    public Enrollment() {
        
    }
    
    public Enrollment(DatabaseConnection dbconn) {
        this.dbconn = dbconn;
    }
    
    public void matriculate(int id, String name) {
        dbconn.query("INSERT INTO stud (id, name) VALUES (" + id + ", '" + name + "')");
        dbconn.execute();
        dbconn.close();
    }
    
}
