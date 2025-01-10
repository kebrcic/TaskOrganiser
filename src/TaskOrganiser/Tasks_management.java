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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;



public class Tasks_management extends AbstractSuperclass{
    private JComboBox StatusCbox;
    private JComboBox PriorityCbox;
    private JButton filterButton;
    private JTextField textFieldClient;
    private JTable tableTasks;
    private JButton assignButton;
    private JButton approveButton;
    private JButton editButton;
    private JTextField textFieldEusername;
    private JTextArea textAreaCommentAdmin;
    private JComboBox comboBoxnewStatus;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel Eu;
    private JLabel Comm;
    private JLabel Stat;
    private JButton rejectButton;
    private JButton viewButton;
    public JPanel Tasks_management;
    private JButton exitButton;
    private JPanel calendar;
    private JCheckBox CheckBoxdate;
    private JLabel FDD;
    Connection connection = null;
    static JFrame tasksFrame = new JFrame("Tasks_management");
    static Object obj;
    static Object obj3;
    static Object obj4;
    static String prefix;
    static String dateYN;
    static String filter_query;
    static Tasks_management newobj = new Tasks_management();
    static MainTasks_management objmaine = new MainTasks_management();
    Calendar calen = Calendar.getInstance();
    JDateChooser datechos = new JDateChooser(calen.getTime());
    Hashtable<String, String> filterQuerys = new Hashtable<>();
    ArrayList<String> filters_chosen = new ArrayList<String>(5);

    public Tasks_management() {
        Eu.setVisible(false);
        Comm.setVisible(false);
        Stat.setVisible(false);
        textFieldEusername.setVisible(false);
        textAreaCommentAdmin.setVisible(false);
        comboBoxnewStatus.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        assignButton.setEnabled(false);
        connection = SQLite_Connection.dbConnector();
        ListSelectionModel selectionModel = tableTasks.getSelectionModel();
        //Add listener to this table in this class
        selectionModel.addListSelectionListener(this);
        datechos.setDateFormatString("dd/MM/yyyy");
        calendar.add(datechos);

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(CheckBoxdate.isSelected()){
                        dateYN = "";
                    }
                    else{
                        dateYN = AbstractSuperclass.Calendar(datechos);
                    }
                    prefix = "select * from Tasks where TaskNum != '"+"-1"+"'";
                    filterQuerys.put("Stat", " AND Status ='"+StatusCbox.getSelectedItem().toString()+"'");
                    filterQuerys.put("Prior", " AND Priority ='"+ PriorityCbox.getSelectedItem().toString()+"'");
                    filterQuerys.put("Cli", "AND CliUsername = '"+textFieldClient.getText()+"'");
                    filterQuerys.put("DueDate", " AND DueDate ='"+dateYN+"'");
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
                    for (int x = 0; x < filters_chosen.size(); x++) {
                            String info = filterQuerys.get(filters_chosen.get(x));
                            filter_query1 = filter_query1 + " " + info + " " ;
                        }
                    filter_query =  prefix + " "+ filter_query1 ;
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
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String query1 = "select * from Tasks ";
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
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                objmaine.setTstatus("Approved");
                AbstractSuperclass.update("UPDATE Tasks set Status = '"+objmaine.getTstatus()+"' where TaskNum ='"+obj+"'");
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                objmaine.setTstatus("Rejected");
                AbstractSuperclass.update("UPDATE Tasks set Status = '"+objmaine.getTstatus()+"' where TaskNum ='"+obj+"'");

            }
        });
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Eu.setVisible(true);
                Comm.setVisible(true);
                textFieldEusername.setVisible(true);
                textAreaCommentAdmin.setVisible(true);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);

                //approveButton.setEnabled(false);
                //rejectButton.setEnabled(false);
                //editButton.setEnabled(false);
                // assignButton.setEnabled(false);
                //  viewButton.setEnabled(false);
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Eu.setVisible(true);
                Comm.setVisible(true);
                Stat.setVisible(true);
                textFieldEusername.setVisible(true);
                textAreaCommentAdmin.setVisible(true);
                comboBoxnewStatus.setVisible(true);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);

                // approveButton.setEnabled(false);
                //rejectButton.setEnabled(false);
                //editButton.setEnabled(false);
                //assignButton.setEnabled(false);
                //viewButton.setEnabled(false);


            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //validation
                    if((textAreaCommentAdmin.getText().equals(""))||(textFieldEusername.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "There are fields empty");
                    }
                    Integer valuer = AbstractSuperclass.checkExistance("select * from Employees where EmpUsername ='"+textFieldEusername.getText()+"' AND Status ='"+"Existing"+"'");
                    if (valuer ==0 ){
                        JOptionPane.showMessageDialog(null, "The employee username does not exist");
                    }

                    if ((valuer == 0)||(textAreaCommentAdmin.getText().equals(""))|| (textFieldEusername.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "Invalid");
                    }

                    else {
                        Integer ConfMess = JOptionPane.showConfirmDialog(null, "Are you sure you want to save?", "Save", JOptionPane.YES_NO_OPTION);
                        if (ConfMess== 0) {
                            objmaine.setTstatus(comboBoxnewStatus.getSelectedItem().toString());
                            objmaine.setCommentAD(textAreaCommentAdmin.getText());
                            objmaine.setEusername(textFieldEusername.getText());
                            AbstractSuperclass.update("UPDATE Tasks set Status = '" + objmaine.getTstatus() + "', EmpUsername = '" + objmaine.getEusername() + "', CommentAD= '" + objmaine.getCommentAD() + "'where TaskNum ='" + obj + "'");
                            assignButton.setEnabled(false);
                        }
                    }
                }
                catch(Exception e1){e1.printStackTrace();}
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButton.setVisible(false);
                Eu.setVisible(false);
                Comm.setVisible(false);
                Stat.setVisible(false);
                textFieldEusername.setVisible(false);
                textAreaCommentAdmin.setVisible(false);
                comboBoxnewStatus.setVisible(false);
                cancelButton.setVisible(false);


            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Menu");
                frame.setContentPane(new Menu().Menu);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                tasksFrame.dispose();

            }
        });
        CheckBoxdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CheckBoxdate.isSelected()){
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
            if(SelectedRow >=0){ //selected row ha to be >=0 because it means you are selecting something from the table, a row.
                TableModel model = tableTasks.getModel();
                //getting selected row and then selected column
                obj = model.getValueAt(SelectedRow, 0); //getting the value of the row selected and the column that we want. Then storing this data in obj1.
                Object obj1 = model.getValueAt(SelectedRow, 2);
                textFieldEusername.setText(obj1 == null ? "":obj1.toString());
                Object obj2 = model.getValueAt(SelectedRow, 9);
                textAreaCommentAdmin.setText(obj2 == null ? "":obj2.toString());
                obj3 = model.getValueAt(SelectedRow, 7);
                obj4 = model.getValueAt(SelectedRow, 2);
                if ((!obj3.equals("Pending"))){
                    approveButton.setEnabled(false);
                    if (obj3.equals("Approved")){
                        rejectButton.setEnabled(true);
                    }
                    else{rejectButton.setEnabled(false);}
                    if((obj4==null)&(!obj3.equals("Deleted"))&(!obj3.equals("Pending"))&(!obj3.equals("Rejected"))){
                        assignButton.setEnabled(true);
                    }
                    else{assignButton.setEnabled(false);}
                }
                else{
                    approveButton.setEnabled(true);
                    rejectButton.setEnabled(true);
                }



            }
        }
    }

    public static void main(String[] args) {
        //tasksFrame is an object of the frame
        tasksFrame.setContentPane(new Tasks_management().Tasks_management);//name of class and then name of panel
        tasksFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tasksFrame.pack();
        tasksFrame.setVisible(true);
    }

}
