package TaskOrganiser;

import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Objects;

public class Client_portal extends AbstractSuperclass {
    private JComboBox StatusCbox;
    private JComboBox PriorityCbox;
    private JButton filterButton;
    private JTable tableTasks;
    private JTextArea textAreaCommentCli;
    private JComboBox comboBoxnewPriority;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel DD;
    private JLabel Comm;
    private JLabel Priority;
    private JButton viewButton;
    public JPanel Client_portal;
    private JButton exitButton;
    private JTextField textFieldDetails;
    private JButton createTaskButton;
    private JButton deleteTaskButton;
    private JButton editTaskButton;
    private JLabel detailsLA;
    private JButton updateButton;
    private JPanel calendar;
    private JPanel FilCalen;
    private JCheckBox checkBoxdate;
    private JLabel FDD;
    Connection connection = null;
    static JFrame clientFrame = new JFrame("Client_portal");
    static Object obj;
    static Object obj3;
    static Object obj4;
    static String status;
    static MainClient_portal objmaine = new MainClient_portal();
    Calendar calen = Calendar.getInstance();
    JDateChooser datechos = new JDateChooser(calen.getTime());
    static String prefix;
    static String dateYN;
    static String filter_query;
    Hashtable<String, String> filterQuerys = new Hashtable<>();
    ArrayList<String> filters_chosen = new ArrayList<String>(5);
    Calendar FiltCalen = Calendar.getInstance();
    JDateChooser fdatechos = new JDateChooser(FiltCalen.getTime());


    public Client_portal() {
        connection = SQLite_Connection.dbConnector();
        ListSelectionModel selectionModel = tableTasks.getSelectionModel();
        //Add listener to this table in this class
        selectionModel.addListSelectionListener(this);

        //formatting the date
        datechos.setDateFormatString("dd/MM/yyyy");
        fdatechos.setDateFormatString("dd/MM/yyyy");
        //displaying chosen date on panel
        calendar.add(datechos);
        FilCalen.add(fdatechos);


        calendar.setVisible(false);
        DD.setVisible(false);
        Comm.setVisible(false);
        textAreaCommentCli.setVisible(false);
        comboBoxnewPriority.setVisible(false);
        Priority.setVisible(false);
        textFieldDetails.setVisible(false);
        detailsLA.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        updateButton.setVisible(false);



        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Login");
                frame.setContentPane(new Login().Login);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                clientFrame.dispose();
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String query1 = "select TaskNum,Details,CommentCLI,DueDate,Priority,Status,CommentEMP from Tasks where CliUsername = '"+USERNAME+"'";
                    PreparedStatement pst = connection.prepareStatement(query1);
                    ResultSet rs = pst.executeQuery();
                    tableTasks.setModel(DbUtils.resultSetToTableModel(rs));
                    AbstractSuperclass.showColumn(tableTasks);
                    pst.close();
                    rs.close();
                }
                catch(Exception e2){
                    e2.printStackTrace();}
            }
        });
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(checkBoxdate.isSelected()){
                        //Due date filter not used
                        dateYN = "";
                    }
                    else{
                        //Obtaining the date chosen
                        dateYN = AbstractSuperclass.Calendar(fdatechos);
                    }
                    //Standard query that all filters require to select the correct data from the database
                    prefix = "select TaskNum,Details,CommentCLI,DueDate,Priority,Status,CommentEMP from Tasks where CliUsername='"+USERNAME+"'";
                    //HashTable that defines a name to each filter and its corresponding query that will append the "prefix" if the filter is selected.
                    filterQuerys.put("Stat", " AND Status ='"+StatusCbox.getSelectedItem().toString()+"'");
                    filterQuerys.put("Prior", "AND Priority ='"+ PriorityCbox.getSelectedItem().toString()+"'");
                    filterQuerys.put("DueDate", "AND DueDate ='"+dateYN+"'");
                    //series of conditions to check the filters being used and append them to an array list in an order.
                    if (!StatusCbox.getSelectedItem().toString().equals("")){
                        filters_chosen.add(0, "Stat");
                        if (!PriorityCbox.getSelectedItem().toString().equals("")){
                            filters_chosen.add(1, "Prior");
                            if (!dateYN.equals("")){
                                filters_chosen.add(2, "DueDate");
                            }
                        }
                        if (!dateYN.equals("")){
                            filters_chosen.add(1, "DueDate");

                        }
                    }
                    else if (!PriorityCbox.getSelectedItem().toString().equals("")){
                        filters_chosen.add(0, "Prior");
                        if (!StatusCbox.getSelectedItem().toString().equals("")){
                            filters_chosen.add(1, "Stat");
                            if (!dateYN.equals("")){
                                filters_chosen.add(2, "DueDate");
                            }
                        }
                        if (!dateYN.equals("")){
                            filters_chosen.add(1, "DueDate");
                        }
                    }
                    else {
                        filters_chosen.add(0, "DueDate");
                        if (!StatusCbox.getSelectedItem().toString().equals("")) {
                            filters_chosen.add(1, "Stat");
                            if (!PriorityCbox.getSelectedItem().toString().equals("")){
                                filters_chosen.add(2, "Prior");}
                        }
                        if (!PriorityCbox.getSelectedItem().toString().equals("")){
                            filters_chosen.add(1, "Prior");
                        }

                    }
                    String filter_query1 = "";
                    //Looping through the array that has the "keys" of the filters used
                    for(int x =0;x<filters_chosen.size();x++){
                        //Accesing the "value" of the "keys" of the filters which are querys.
                        String info = filterQuerys.get(filters_chosen.get(x));
                        //Joining the "values" which are querys of the filters selected.
                        filter_query1 = filter_query1  +" "+ info+ " ";
                    }
                    //Adding the prefix query to the query constructed by the filters selected.
                    filter_query =  prefix + " "+ filter_query1 ;
                    //Executing the query
                    PreparedStatement pst = connection.prepareStatement(filter_query);
                    ResultSet rs = pst.executeQuery();
                    tableTasks.setModel(DbUtils.resultSetToTableModel(rs));
                    AbstractSuperclass.showColumn(tableTasks);
                    pst.close();
                    rs.close();
                    filters_chosen.clear();
                }
                catch(Exception e2){
                    e2.printStackTrace();}

            }
        });
        createTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setVisible(true);
                DD.setVisible(true);
                Comm.setVisible(true);
                textAreaCommentCli.setVisible(true);
                comboBoxnewPriority.setVisible(true);
                Priority.setVisible(true);
                textFieldDetails.setVisible(true);
                detailsLA.setVisible(true);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
                updateButton.setVisible(false);
                deleteTaskButton.setEnabled(false);
                editTaskButton.setEnabled(false);
            }
        });

        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractSuperclass.update("UPDATE Tasks set Status = '"+"Deleted"+"' where TaskNum ='"+obj+"'");
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if((textAreaCommentCli.getText().equals(""))||(textFieldDetails.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "There are fields empty");
                    }
                    else{
                        //inserting into the Administrator table unknown values initially into the fields that are being stated
                        String query = "INSERT INTO Tasks(TaskNum,CliUsername,EmpUsername,Details,CommentCLI,DueDate,Priority,Status,CommentEMP,CommentAD) VALUES(?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pst = connection.prepareStatement(query);
                        //Only able to execute command (query) with a prepared statement. The query is ready for execution
                        //Therefore the prepared statement is running the query
                        objmaine.setDetails(textFieldDetails.getText());
                        objmaine.setCommentCLI(textAreaCommentCli.getText());
                        objmaine.setDueDate(AbstractSuperclass.Calendar(datechos));
                        objmaine.setPriority(comboBoxnewPriority.getSelectedItem().toString());
                        objmaine.setTstatus("Pending");


                        pst.setInt(3, 1);
                        pst.setString(2, USERNAME);
                        pst.setString(3, null);
                        //Grab what the user has inputted in the textfields and send it to the database
                        pst.setString(4, objmaine.getDetails());
                        pst.setString(5, objmaine.getCommentCLI());
                        pst.setString(6, objmaine.getDueDate());
                        pst.setString(7, objmaine.getPriority());
                        pst.setString(8, objmaine.getTstatus());
                        pst.setString(9, null);
                        pst.setString(10, null);

                        pst.execute();
                        JOptionPane.showMessageDialog(null, "Task has been successfully created");
                        pst.close();}

                }
                catch(Exception e1){

                }

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if((textAreaCommentCli.getText().equals(""))||(textFieldDetails.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "There are fields empty");
                    }
                    else {
                        Integer ConfMess = JOptionPane.showConfirmDialog(null, "Are you sure you want to update?", "Update", JOptionPane.YES_NO_OPTION);
                        if (ConfMess== 0) {
                            objmaine.setDetails(textFieldDetails.getText());
                            objmaine.setCommentCLI(textAreaCommentCli.getText());
                            objmaine.setDueDate(AbstractSuperclass.Calendar(datechos));
                            AbstractSuperclass.update("UPDATE Tasks set CommentCLI = '" + objmaine.getCommentCLI() + "', Details = '" + objmaine.getDetails() + "', DueDate= '" + objmaine.getDueDate() + "'where TaskNum ='" + obj + "'");
                        }
                    }
                }
                catch(Exception e1){e1.printStackTrace();}
            }
        });
        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setVisible(true);
                DD.setVisible(true);
                Comm.setVisible(true);
                textAreaCommentCli.setVisible(true);
                comboBoxnewPriority.setVisible(false);
                Priority.setVisible(false);
                textFieldDetails.setVisible(true);
                detailsLA.setVisible(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(true);
                updateButton.setVisible(true);
                createTaskButton.setEnabled(false);
                deleteTaskButton.setEnabled(false);


            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setVisible(false);
                DD.setVisible(false);
                Comm.setVisible(false);
                textAreaCommentCli.setVisible(false);
                comboBoxnewPriority.setVisible(false);
                Priority.setVisible(false);
                textFieldDetails.setVisible(false);
                detailsLA.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                updateButton.setVisible(false);
                editTaskButton.setEnabled(true);
                createTaskButton.setEnabled(true);

            }
        });
        checkBoxdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBoxdate.isSelected()){
                    FilCalen.setVisible(false);
                    FDD.setVisible(false);
                    dateYN = "";
                }
                else{
                    FilCalen.setVisible(true);
                    FDD.setVisible(true);
                }
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){ //return true if the selection is still
            int SelectedRow = tableTasks.getSelectedRow(); //getSelectedRow returns an integer of the row selected
            if(SelectedRow >=0){ //selected row ha to be >=0 because it means you are selecting something from the table, a row.
                TableModel model = tableTasks.getModel();
                //getting selected row and then selected column
                obj = model.getValueAt(SelectedRow, 0); //getting the value of the row selected and the column that we want. Then storing this data in obj1.
                Object obj2 = model.getValueAt(SelectedRow, 1);
                textFieldDetails.setText(obj2 == null ? "":obj2.toString());
                Object obj5 = model.getValueAt(SelectedRow, 2 );
                textAreaCommentCli.setText(obj5 == null ? "":obj5.toString());
                Object obj3 = model.getValueAt(SelectedRow, 5);

                if (!obj3.equals("Pending")){
                    deleteTaskButton.setEnabled(false);
                }
                else{
                    deleteTaskButton.setEnabled(true);
                }
                if (obj3.equals("Deleted")){
                    editTaskButton.setEnabled(false);
                }
                else{editTaskButton.setEnabled(true);}

            }
        }
    }

    public static void main(String[] args) {
        clientFrame.setContentPane(new Client_portal().Client_portal);
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.pack();
        clientFrame.setVisible(true);
    }
}
