package TaskOrganiser;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/*
Calendar functionality from: Words, G., Idea, W., asktejas, B., Flojea, M., freaks, U., 问说网, J., Ethics, I., softwareengineeringcodefoethic, S., Website, J., Aplikasi, D., JCalender &#8211; Tech Blicks | Tips, V., JAVA, K. and java.lang.IllegalStateException: Attempt to mutate in notification (JDateChooser, J. -. J. D. D. -. P.
Words, Google et al. "Jcalendar – Toedter.Com". Toedter.Com, 2023, https://toedter.com/jcalendar/. Accessed 20 Sept 2023.

Downloaded from: http://www.java2s.com/Code/Jar/j/Downloadjcalendar14jar.htm
 */

public abstract class AbstractSuperclass extends JFrame implements ListSelectionListener {
    static int value;
    static Connection connection = null;
    public static String USERNAME;


    public static void update(String queryUpdate) {
        /*parameterized method to enhance standardization of update of records.
        Many classes can use the update method by evoking it and sending their own query as an argument of the function
         */
        connection = SQLite_Connection.dbConnector();
        try {
            //Prepared statement used to execute the query
            PreparedStatement pstt = connection.prepareStatement(queryUpdate);
            pstt.execute();
            //message to the user to indicate that the operation was successful
            JOptionPane.showMessageDialog(null, "Record has been updated successfully");
            pstt.close();

        } catch (Exception e4) {
            e4.printStackTrace();

        }

    }

    public static void delete(String queryDelete) {
        connection = SQLite_Connection.dbConnector();
        //making sure the user wants to delete through a YES_NO_OPTION
        int action = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete", "Delete", JOptionPane.YES_NO_OPTION);
        //If the "yes" option is selected...
        if (action == 0) {
            try {
                PreparedStatement pst4 = connection.prepareStatement(queryDelete);
                pst4.execute();
                //Notify the user that the account has been deleted
                JOptionPane.showMessageDialog(null, "Account is now 'deleted'");
                pst4.close();
            } catch (Exception e2) {

            }
        }
    }

    public static void createAccAdminEmp(String queryCreate, String TXTusername, String TXTpassword, String TXTname, String TXTlastname, String TXTstatus ) {
        //parameters in the method to enable reusability of this code. Hence, the algorithm is standardized and was coded in general terms.
        connection = SQLite_Connection.dbConnector();
        try{
        //inserting into the Administrator table unknown values initially into the fields that are being stated
        PreparedStatement pst = connection.prepareStatement(queryCreate);
        //Only able to execute command (query) with a prepared statement. The query is ready for execution
        //Therefore, the prepared statement is running the query

        pst.setString(1, TXTusername);
        //Grab what the user has inputted in the fields and send it to the database in the correct column index, 1 being the first one.
        pst.setString(2, TXTpassword);
        pst.setString(3, TXTname);
        pst.setString(4, TXTlastname);
        pst.setString(5, TXTstatus);

        pst.execute();
        JOptionPane.showMessageDialog(null, "Account has been successfully created");
        pst.close();

        }
        catch(Exception e1){

        }

    }

    public static void createAccCli(String queryCreate, String TXTusername, String TXTpassword, String TXTCname, String TXTstatus ) {
        //The connection with the database needs to be established.
        connection = SQLite_Connection.dbConnector();
        try{
            //inserting into the Administrator table unknown values initially into the fields that are being stated.
            //This is written on the query.
            PreparedStatement pst = connection.prepareStatement(queryCreate);
            /*Only able to execute command (query) with a prepared statement. The query is ready for execution
            Therefore the prepared statement is running the query
             */
            pst.setString(1, TXTusername);
            //Temporarily store what the user has inputted in the fields and send it to the database.
            pst.setString(2, TXTpassword);
            pst.setString(3, TXTCname);
            pst.setString(4, TXTstatus);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Account has been successfully created");
            pst.close();

        }
        catch(Exception e1){

        }

    }

    public static @NotNull String generatePassword(String prefix) {

        //creating a string of all characters
        String gen = "abcdefghijklmnopqrstuvwxyz123456789!·$%/()=¿?*^¨ç><'¡#";
        //creating a random string builder
        StringBuilder code = new StringBuilder();
        StringBuilder codef = new StringBuilder();
        // create an object of the random class
        Random random = new Random();
        // create a variable to specify the length of the randomly picked
        int length = 3;
        // for loop to loop through the selected characters and keep generating different strings
        // looping from 1-38, not through the characters.
        // Getting a random index and then use the random index to choose the character from gen
        for (int i = 0; i < length; i++) {
            // generate random index number
            int index = random.nextInt(gen.length());
            //generating character from string by specified index
            char randomChar = gen.charAt(index);
            // append the character to a string builder
            code.append(randomChar);
            //Join the prefix string to the randomly selected character to have the password
        }
        codef.append(prefix + code);

        //Turning the final code into a string using the toString method
        String FinalRandomString = codef.toString();
        return FinalRandomString;

    }

    public static void UseLocator(@NotNull String Locatortxt, TableModel tMod, @NotNull JTable x  ){
        //Parameters to allow all interfaces to make use of it by sending their JTable, TableModel and the text of the locator.
        //TableRowSorter to implement the RowSorter for a TableModel
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tMod);
        //sorting the JTable
        x.setRowSorter(sorter);
        //.trim() eliminates spaces in the text on the textField entered
        if (Locatortxt.trim().length() ==0){
            sorter.setRowFilter(null);
            //nothing on the textfield means no filter
        }
        else {
            sorter.setRowFilter(RowFilter.regexFilter(Locatortxt));
            //filtering takes place according to what has been written by the user in the locatorTextField
        }
    }

    public static Integer checkExistance(String queryCheck){
        connection = SQLite_Connection.dbConnector();
        try {
            PreparedStatement pstt2 = connection.prepareStatement(queryCheck);
            /*A ResultSet is necessary to represent the database results after the execution of a query
            by the PreparedStatement. The ResultSet object brings data from the database that satisfies the query.
             */
            ResultSet r2 = pstt2.executeQuery();
            int counting = 0;
            /*A count needs to be established to all the rows selected from the database that satisfy the query.
            If the count is 1, then there is a record that already exists with the requirements from the query.
            So existance is true. If the count is 0 then existance is false.
             */
            value = 0;
            while(r2.next()) {
                counting += 1;
            }
            if(counting == 1){
               value = 1;
               return value;
            }
            else if(counting == 0){
                value = 0;
                return value;
            }
            r2.close();
            pstt2.close();
        }
        catch(Exception e1){
            e1.printStackTrace();}
        return value;
    }

    public static String Calendar( JDateChooser dchooser ){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dt = sdf.format(dchooser.getDate());
            return dt;

    }

    public static void showColumn(JTable table){
        JTableHeader th = table.getTableHeader();
        th.setForeground(Color.BLUE);
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(new Font("Tahoma",Font.BOLD, 10));
    }

}

