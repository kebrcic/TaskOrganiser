package TaskOrganiser;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;


public class Admin_accounts extends AbstractSuperclass{
    public JPanel Admin_accounts;
    private JTextField textFieldLocator;
    private JTable AdminTable;
    private JTextField textFieldName;
    private JTextField textFieldLastName;
    private JButton generatePasswordButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton changeButton;
    private JButton createAccountButton;
    private JButton deleteAccountButton;
    private JPasswordField passwordField1;
    private JComboBox comboBoxStatus;
    private JButton viewRecordsButton;
    private JLabel LN;
    private JLabel Na;
    private JLabel Pass;
    private JLabel Sta;
    private JCheckBox showPasswordCheckBox;
    private JTextField textFieldUsername;
    private JLabel UserN;
    private JButton updateButton;
    private JButton exitButton;
    Connection connection = null;
    static JFrame aaccFrame = new JFrame("Admin_accounts");
    static Object obj;
    static String status;
    static Admin_accounts newobj = new Admin_accounts();
    static MainAdmin_accounts objmain = new MainAdmin_accounts();


    public Admin_accounts() {

        status = Objects.requireNonNull(comboBoxStatus.getSelectedItem()).toString();
        connection = SQLite_Connection.dbConnector();
        textFieldName.setVisible(false);
        textFieldLastName.setVisible(false);
        textFieldUsername.setVisible(false);
        comboBoxStatus.setVisible(false);
        passwordField1.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        generatePasswordButton.setVisible(false);
        showPasswordCheckBox.setVisible(false);
        updateButton.setVisible(false);
        Na.setVisible(false);
        Sta.setVisible(false);
        LN.setVisible(false);
        Pass.setVisible(false);
        UserN.setVisible(false);
        deleteAccountButton.setEnabled(false);
        changeButton.setEnabled(false);
        ListSelectionModel selectionModel = AdminTable.getSelectionModel();
        TableModel model = AdminTable.getModel();
        //Add listener to this table in this class
        selectionModel.addListSelectionListener(this);
        //This keyword means that the method is only happening in this specific class
        textFieldLocator.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try{
                    AbstractSuperclass.UseLocator(textFieldLocator.getText(), AdminTable.getModel(), AdminTable );
                }
                catch(Exception e2){
                    e2.printStackTrace();
                }
            }
        });
        viewRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String query1 = "select * from Administrators";
                    PreparedStatement pst = connection.prepareStatement(query1);
                    ResultSet rs = pst.executeQuery();
                    AdminTable.setModel(DbUtils.resultSetToTableModel(rs));
                    AbstractSuperclass.showColumn(AdminTable);
                    pst.close();
                    rs.close();
                }
                catch(Exception e2){
                    e2.printStackTrace();}
            }
        });
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldName.setVisible(true);
                textFieldLastName.setVisible(true);
                textFieldUsername.setVisible(true);
                comboBoxStatus.setVisible(true);
                passwordField1.setVisible(true);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
                generatePasswordButton.setVisible(true);
                showPasswordCheckBox.setVisible(true);
                Na.setVisible(true);
                Sta.setVisible(true);
                LN.setVisible(true);
                Pass.setVisible(true);
                UserN.setVisible(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldName.setText("");
                textFieldLastName.setText("");
                passwordField1.setText("");
                textFieldUsername.setText("");
                textFieldName.setVisible(false);
                textFieldLastName.setVisible(false);
                textFieldUsername.setVisible(false);
                comboBoxStatus.setVisible(false);
                passwordField1.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                generatePasswordButton.setVisible(false);
                showPasswordCheckBox.setVisible(false);
                updateButton.setVisible(false);
                Na.setVisible(false);
                Sta.setVisible(false);
                LN.setVisible(false);
                Pass.setVisible(false);
                UserN.setVisible(false);
            }
        });
        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pa =AbstractSuperclass.generatePassword("ADMIN");
                passwordField1.setText(pa);
            }
        });
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()){
                    // echoe the character and 0 means that it will show everything and start echoing characters
                    // from the beginning (from index position 0)
                    passwordField1.setEchoChar((char)0);
                }
                else{
                    //If the checkbox is not selected, the password will hide again with *
                    passwordField1.setEchoChar('*');
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //Checking if any of the fields is empty.
                    if ((textFieldUsername.getText().equals(""))|| (textFieldName.getText().equals(""))||(textFieldLastName.getText().equals(""))||
                            (passwordField1.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "You have left one of the fields empty");
                    }
                    //Checking that the password contains the prefix for the type of account being created. In this case "ADMIN".
                    String passwordcheck = passwordField1.getText();
                    if(!passwordcheck.contains("ADMIN")){
                        JOptionPane.showMessageDialog(null, "The password needs to have 'ADMIN' prefix ");
                    }
                    //Validating that the username is unique and doesn't exist in the database table where the information will be inserted. The username is the primary key.
                    Integer valuer = AbstractSuperclass.checkExistance("select * from Administrators where AdminUsername ='"+textFieldUsername.getText()+"'");
                    if (valuer == 1){
                        JOptionPane.showMessageDialog(null, "The username exists");
                    }
                    //Checking the username contains the character '@'
                    if ((!textFieldUsername.getText().contains("@"))){
                        JOptionPane.showMessageDialog(null, "The character '@' is required in the username");
                    }

                    if ((valuer == 1)||(textFieldUsername.getText().equals(""))|| (textFieldName.getText().equals(""))||
                            (textFieldLastName.getText().equals(""))||(passwordField1.getText().equals(""))||(!passwordcheck.contains("ADMIN"))||
                            (!textFieldUsername.getText().contains("@"))){
                        JOptionPane.showMessageDialog(null, "Invalid");
                    }
                    else {
                        objmain.setUsername(textFieldUsername.getText());
                        objmain.setPassword(passwordField1.getText());
                        objmain.setName(textFieldName.getText());
                        objmain.setLastName(textFieldLastName.getText());
                        objmain.setStatus(comboBoxStatus.getSelectedItem().toString());
                        AbstractSuperclass.createAccAdminEmp("INSERT INTO Administrators(AdminUsername,Password,Name,LastName,Status) VALUES(?,?,?,?,?)",
                                objmain.getUsername(), objmain.getPassword(), objmain.getName(), objmain.getLastName(), objmain.getStatus());
                    }
                }
                catch (Exception e2){
                    e2.printStackTrace();
                }
            }
        });
        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AbstractSuperclass.delete("update Administrators set Status='Deleted' where AdminUsername='"+obj+"'");
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldUsername.setVisible(false);
                comboBoxStatus.setVisible(false);
                Sta.setVisible(false);
                UserN.setVisible(false);
                saveButton.setVisible(false);
                textFieldName.setVisible(true);
                textFieldLastName.setVisible(true);
                passwordField1.setVisible(true);
                cancelButton.setVisible(true);
                generatePasswordButton.setVisible(true);
                showPasswordCheckBox.setVisible(true);
                Na.setVisible(true);
                LN.setVisible(true);
                Pass.setVisible(true);
                updateButton.setVisible(true);
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((textFieldUsername.getText().equals("")) || (textFieldName.getText().equals(" ")) || (textFieldLastName.getText().equals(" ")) || (passwordField1.getText().equals(" "))) {
                        JOptionPane.showMessageDialog(null, "You have left one of the fields empty");
                    }
                    String passwordcheck = passwordField1.getText();
                    if (!passwordcheck.contains("ADMIN")) {
                        JOptionPane.showMessageDialog(null, "The password needs to have 'ADMIN' prefix ");
                    } else {
                        objmain.setPassword(passwordField1.getText());
                        objmain.setName(textFieldName.getText());
                        objmain.setLastName(textFieldLastName.getText());
                        AbstractSuperclass.update("UPDATE Administrators set Password = '" + objmain.getPassword() + "', Name = '" + objmain.getName() + "', LastName= '" + objmain.getLastName() + "'where AdminUsername ='" + obj + "'");
                    }
                }catch(Exception e7){e7.printStackTrace();}


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
                aaccFrame.dispose();

            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){ //return true if the selection is still
            deleteAccountButton.setEnabled(true);
            changeButton.setEnabled(true);
            int SelectedRow = AdminTable.getSelectedRow(); //getSelectedRow returns an integer of the row selected
            if(SelectedRow >=0){ //selected row ha to be >=0 because it means you are selecting something from the table, a row.
                TableModel model = AdminTable.getModel();
                //getting selected row and then selected column
                obj = model.getValueAt(SelectedRow, 0); //getting the value of the row selected and the column that we want.
                // Then storing this data in obj.
                textFieldUsername.setText(obj == null ? "": obj.toString());
                Object obj1 = model.getValueAt(SelectedRow, 1);
                passwordField1.setText(obj1 == null ? "":obj1.toString());
                Object obj2 = model.getValueAt(SelectedRow, 2);
                textFieldName.setText(obj2 == null ? "":obj2.toString());
                Object obj3 = model.getValueAt(SelectedRow, 3);
                textFieldLastName.setText(obj3 == null ? "":obj3.toString());
                Object obj4 = model.getValueAt(SelectedRow, 4);
                if(obj4.equals("Deleted")){
                    deleteAccountButton.setEnabled(false);
                }
                else{
                    deleteAccountButton.setEnabled(true);
                }
            }
        }

    }

    public static void main(String[] args) {
        aaccFrame.setContentPane(new Admin_accounts().Admin_accounts);
        aaccFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aaccFrame.pack();
        aaccFrame.setVisible(true);
    }


}
