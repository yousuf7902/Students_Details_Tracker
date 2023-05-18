
package java_lab_project;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;


public class database {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/admin_data", "root","");
            return connect;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
}
