package deniz;

import java.sql.ResultSet;

/**
 * Created by wagum on 03.11.2015.
 */
public interface DatabaseConnection {
    public ResultSet execQuery();
    public boolean close();
    public boolean query(String s);
    public boolean execute();
    


}
