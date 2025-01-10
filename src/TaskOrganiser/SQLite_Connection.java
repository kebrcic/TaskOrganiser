package TaskOrganiser;
import java.sql.*;
import javax.swing.*;

public class SQLite_Connection {
    Connection conn = null;
    public static Connection dbConnector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/KevinBrcic/Downloads/Computer_Science_IA/src/TaskOrganiser/Task_Organiser.sqlite");
            //JOptionPane.showMessageDialog(null, "Connection is successful with Database!");
            return conn;
        }

        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }



}
