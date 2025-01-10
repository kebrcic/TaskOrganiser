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
import java.util.Calendar;
import java.util.Hashtable;
import java.util.*;

public class Employee_portal extends AbstractSuperclass {
    private JComboBox StatusCbox;
    private JComboBox PriorityCbox;
    private JButton filterButton;
    private JTable tableTasks;
    private JTextArea textAreaCommentEmp;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel StatusL;
    private JLabel Comm;
    private JButton viewButton;
    public JPanel Employee_portal;
    private JButton exitButton;
    private JButton editTaskButton;
    private JTextField textFieldClient;
    private JComboBox comboBoxnewStatus;
    private JPanel calendar;
    private JCheckBox checkBoxdate;
    private JLabel FDD;
    Connection connection = null;
    static JFrame employeeFrame = new JFrame("Employee_portal");
    static Object obj;
    //static Employee_portal newobj = new Employee_portal();
    static MainEmployee_portal objmaine = new MainEmployee_portal();
    static String prefix;
    static String dateYN;
    static String filter_query;
    Calendar calen = Calendar.getInstance();
    JDateChooser datechos = new JDateChooser(calen.getTime());
    Hashtable<String, String> filterQuerys = new Hashtable<>();
    ArrayList<String> filters_chosen = new ArrayList<String>(5);




    public Employee_portal() {
        connection = SQLite_Connection.dbConnector();
        ListSelectionModel selectionModel = tableTasks.getSelectionModel();
        //Add listener to this table in this class
        selectionModel.addListSelectionListener(this);
        textAreaCommentEmp.setVisible(false);
        Comm.setVisible(false);
        StatusL.setVisible(false);
        comboBoxnewStatus.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        datechos.setDateFormatString("dd/MM/yyyy");
        calendar.add(datechos);





        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Login");
                frame.setContentPane(new Login().Login);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                employeeFrame.dispose();
            }
        });
        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaCommentEmp.setVisible(true);
                Comm.setVisible(true);
                StatusL.setVisible(true);
                comboBoxnewStatus.setVisible(true);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaCommentEmp.setVisible(false);
                Comm.setVisible(false);
                StatusL.setVisible(false);
                comboBoxnewStatus.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String query1 = "select TaskNum,CliUsername,Details,CommentCLI,DueDate,Priority,Status,CommentEMP,CommentAD from Tasks where EmpUsername = '"+USERNAME+"'";
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
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if((textAreaCommentEmp.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "The comment area needs to be filled");
                    }
                    else {
                        Integer ConfMess = JOptionPane.showConfirmDialog(null, "Are you sure you want to update?", "Update", JOptionPane.YES_NO_OPTION);
                        if (ConfMess== 0) {
                            objmaine.setCommentEMP(textAreaCommentEmp.getText());
                            objmaine.setTstatus(comboBoxnewStatus.getSelectedItem().toString());
                            AbstractSuperclass.update("UPDATE Tasks set CommentEMP = '" + objmaine.getCommentEMP()+ "', Status = '" + objmaine.getTstatus() + "'where TaskNum ='" + obj + "'");
                        }
                    }
                }
                catch(Exception e1){e1.printStackTrace();}
            }
        });
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(checkBoxdate.isSelected()){
                        dateYN = "";
                    }
                    else{
                        dateYN = AbstractSuperclass.Calendar(datechos);
                    }
                    prefix = "select TaskNum,CliUsername,Details,CommentCLI,DueDate,Priority,Status,CommentEMP,CommentAD from Tasks where EmpUsername='"+USERNAME+"'";
                    filterQuerys.put("Stat", " AND Status ='"+StatusCbox.getSelectedItem().toString()+"'");
                    filterQuerys.put("Prior", "AND Priority ='"+ PriorityCbox.getSelectedItem().toString()+"'");
                    filterQuerys.put("Cli", "AND CliUsername = '"+textFieldClient.getText()+"'");
                    filterQuerys.put("DueDate", "AND DueDate ='"+dateYN+"'");
                    if (!StatusCbox.getSelectedItem().toString().equals("")){
                        filters_chosen.add(0, "Stat");
                        if (!PriorityCbox.getSelectedItem().toString().equals("")){
                            filters_chosen.add(1, "Prior");
                            if (!dateYN.equals("")){
                                filters_chosen.add(2, "DueDate");
                                if (!textFieldClient.getText().equals("")){
                                    filters_chosen.add(3, "Cli");
                                }
                            }
                            else if (!textFieldClient.getText().equals("")){
                                filters_chosen.add(2, "Cli");
                            }
                        }
                        else if (!dateYN.equals("")){
                            filters_chosen.add(1, "DueDate");
                            if (!textFieldClient.getText().equals("")){
                                filters_chosen.add(2, "Cli");
                            }
                        }
                        else if (!textFieldClient.getText().equals("")){
                            filters_chosen.add(1, "Cli");}
                    }
                    else if (!PriorityCbox.getSelectedItem().toString().equals("")){
                        filters_chosen.add(0, "Prior");
                        if (!dateYN.equals("")){
                            filters_chosen.add(1, "DueDate");
                            if (!textFieldClient.getText().equals("")){
                                filters_chosen.add(2, "Cli");
                                }
                            }

                        else if (!textFieldClient.getText().equals("")) {
                            filters_chosen.add(1, "Cli");
                        }
                    }
                   else if (!dateYN.equals("")){
                        filters_chosen.add(0, "DueDate");
                        if (!textFieldClient.getText().equals("")){
                            filters_chosen.add(1, "Cli");
                        }

                    }
                   else if(!textFieldClient.getText().equals("")){
                        filters_chosen.add(0, "Cli");

                    }
                    String filter_query1 = "";
                    for(int x =0;x<filters_chosen.size();x++){
                        String info = filterQuerys.get(filters_chosen.get(x));
                        filter_query1 = filter_query1  +" "+ info+ " ";
                    }
                    filter_query =  prefix + " "+ filter_query1 ;
                    PreparedStatement pst = connection.prepareStatement(filter_query);
                    ResultSet rs = pst.executeQuery();
                    tableTasks.setModel(DbUtils.resultSetToTableModel(rs));
                    pst.close();
                    rs.close();
                    filters_chosen.clear();


                    //String query1 = "select TaskNum,CliUsername,Details,CommentCLI,DueDate,Priority,Status,CommentEMP,CommentAD from Tasks where Priority ='"+ PriorityCbox.getSelectedItem().toString()+"'AND Status ='"+StatusCbox.getSelectedItem().toString()+"'AND DueDate ='"+AbstractSuperclass.Calendar(datechos)+"'AND EmpUsername='"+USERNAME+"'" ;
                    //PreparedStatement pst = connection.prepareStatement(query1);
                    //ResultSet rs = pst.executeQuery();
                    //tableTasks.setModel(DbUtils.resultSetToTableModel(rs));
                    //newobj.show_column();
                    //pst.close();
                    //rs.close();
                }
                catch(Exception e2){
                    e2.printStackTrace();}
            }
        });
        checkBoxdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBoxdate.isSelected()){
                    calendar.setVisible(false);
                    FDD.setVisible(false);
                    dateYN = "";
                }
                else{
                    calendar.setVisible(true);
                    FDD.setVisible(true);
                }

            }
        });
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){ //return true if the selection is still
            int SelectedRow = tableTasks.getSelectedRow(); //getSelectedRow returns an integer of the row selected
            if(SelectedRow >=0) { //selected row ha to be >=0 because it means you are selecting something from the table, a row.
                TableModel model = tableTasks.getModel();
                //getting selected row and then selected column
                obj = model.getValueAt(SelectedRow, 0); //getting the value of the row selected and the column that we want. Then storing this data in obj1.
                Object obj1 = model.getValueAt(SelectedRow, 7);
                textAreaCommentEmp.setText(obj1 == null ? "" : obj1.toString());
                Object obj2 = model.getValueAt(SelectedRow, 6);
                if(obj2.equals("Deleted")){
                    editTaskButton.setEnabled(false);
                }
                else{
                    editTaskButton.setEnabled(true);
                }

            }
        }
    }


    public static void main(String[] args) {
        employeeFrame.setContentPane(new Employee_portal().Employee_portal);
        employeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employeeFrame.pack();
        employeeFrame.setVisible(true);
    }

}




