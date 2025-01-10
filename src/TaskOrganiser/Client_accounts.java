package TaskOrganiser;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class Client_accounts extends AbstractSuperclass{
    public JPanel Client_accounts;
    private JTextField textFieldLocator;
    private JTable CliTable;
    private JTextField textFieldCName;
    private JButton generatePasswordButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton changeButton;
    private JButton createAccountButton;
    private JButton deleteAccountButton;
    private JPasswordField passwordField1;
    private JComboBox comboBoxStatus;
    private JButton viewRecordsButton;
    private JLabel C_Na;
    private JLabel Pass;
    private JLabel Sta;
    private JCheckBox showPasswordCheckBox;
    private JTextField textFieldUsername;
    private JLabel UserN;
    private JButton updateButton;
    private JButton exitButton;
    Connection connection = null;
    static JFrame CaccFrame = new JFrame("Client_accounts");
    static Object obj;
    static String status;
    static Client_accounts newobj = new Client_accounts();
    static MainClient_accounts objmaine = new MainClient_accounts();

    public Client_accounts() {
        status = Objects.requireNonNull(comboBoxStatus.getSelectedItem()).toString();
        connection = SQLite_Connection.dbConnector();
        textFieldCName.setVisible(false);
        textFieldUsername.setVisible(false);
        comboBoxStatus.setVisible(false);
        passwordField1.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        generatePasswordButton.setVisible(false);
        showPasswordCheckBox.setVisible(false);
        updateButton.setVisible(false);
        C_Na.setVisible(false);
        Sta.setVisible(false);
        Pass.setVisible(false);
        UserN.setVisible(false);
        deleteAccountButton.setEnabled(false);
        changeButton.setEnabled(false);
        ListSelectionModel selectionModel = CliTable.getSelectionModel();
        //Add listener to this table in this class
        selectionModel.addListSelectionListener( this);
        textFieldLocator.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try{
                    AbstractSuperclass.UseLocator(textFieldLocator.getText(), CliTable.getModel(), CliTable);
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
                    String query1 = "select * from Clients";
                    PreparedStatement pst = connection.prepareStatement(query1);
                    ResultSet rs = pst.executeQuery();
                    CliTable.setModel(DbUtils.resultSetToTableModel(rs));
                    AbstractSuperclass.showColumn(CliTable);
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
                textFieldCName.setVisible(true);
                textFieldUsername.setVisible(true);
                comboBoxStatus.setVisible(true);
                passwordField1.setVisible(true);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
                generatePasswordButton.setVisible(true);
                showPasswordCheckBox.setVisible(true);
                C_Na.setVisible(true);
                Sta.setVisible(true);
                Pass.setVisible(true);
                UserN.setVisible(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldCName.setText("");
                passwordField1.setText("");
                textFieldUsername.setText("");
                textFieldCName.setVisible(false);
                textFieldUsername.setVisible(false);
                comboBoxStatus.setVisible(false);
                passwordField1.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                generatePasswordButton.setVisible(false);
                showPasswordCheckBox.setVisible(false);
                updateButton.setVisible(false);
                C_Na.setVisible(false);
                Sta.setVisible(false);
                Pass.setVisible(false);
                UserN.setVisible(false);
            }
        });
        generatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pa =AbstractSuperclass.generatePassword("CLI");
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
                    if ((textFieldUsername.getText().equals(""))|| (textFieldCName.getText().equals(""))|| (passwordField1.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "You have left one of the fields empty");
                    }
                    //Checking that the password contains the prefix for the type of account being created. In this case "CLI".
                    String passwordcheck = passwordField1.getText();
                    if(!passwordcheck.contains("CLI")){
                        JOptionPane.showMessageDialog(null, "The password needs to have 'CLI' prefix ");
                    }
                    //Validating that the username is unique and doesn't exist in the database table where the information will be inserted. The username is the primary key.
                    Integer valuer = AbstractSuperclass.checkExistance("select * from Clients where CliUsername ='"+textFieldUsername.getText()+"'");
                    if (valuer == 1){
                        JOptionPane.showMessageDialog(null, "The username already exists");
                    }
                    //Checking the username contains the character '@'
                    if ((!textFieldUsername.getText().contains("@"))){
                        JOptionPane.showMessageDialog(null, "The character '@' is required in the username");
                    }
                    if ((valuer == 1)||(textFieldUsername.getText().equals(""))|| (textFieldCName.getText().equals(""))||
                            (passwordField1.getText().equals(""))||(!passwordcheck.contains("CLI"))||(!textFieldUsername.getText().contains("@"))){
                        JOptionPane.showMessageDialog(null, "Invalid");
                    }
                    else{
                        objmaine.setUsername(textFieldUsername.getText());
                        objmaine.setPassword(passwordField1.getText());
                        objmaine.setCName(textFieldCName.getText());
                        objmaine.setStatus(comboBoxStatus.getSelectedItem().toString());
                        AbstractSuperclass.createAccCli("INSERT INTO Clients(CliUsername,Password,Company,Status) VALUES(?,?,?,?)", objmaine.getUsername(), objmaine.getPassword(), objmaine.getCName(), objmaine.getStatus());

                        //AbstractSuperclass.createAccAdminEmp("INSERT INTO Administrators(AdminUsername,Password,Name,LastName,Status) VALUES(?,?,?,?,?)", textFieldUsername.getText(), passwordField1.getText(), textFieldName.getText(), textFieldLastName.getText(), status);
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
                AbstractSuperclass.delete("update Clients set Status='Deleted' where CliUsername='"+obj+"'");
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
                textFieldCName.setVisible(true);
                passwordField1.setVisible(true);
                cancelButton.setVisible(true);
                generatePasswordButton.setVisible(true);
                showPasswordCheckBox.setVisible(true);
                C_Na.setVisible(true);
                Pass.setVisible(true);
                updateButton.setVisible(true);
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((textFieldUsername.getText().equals("")) || (textFieldCName.getText().equals(" ")) || (passwordField1.getText().equals(" "))) {
                        JOptionPane.showMessageDialog(null, "You have left one of the fields empty");
                    }
                    String passwordcheck = passwordField1.getText();
                    if (!passwordcheck.contains("CLI")) {
                        JOptionPane.showMessageDialog(null, "The password needs to have 'CLI' prefix ");
                    } else {
                        objmaine.setPassword(passwordField1.getText());
                        objmaine.setCName(textFieldCName.getText());
                        AbstractSuperclass.update("UPDATE Clients set Password = '" + objmaine.getPassword() + "', Company = '" + objmaine.getCName() +  "'where CliUsername ='" + obj + "'");
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
                CaccFrame.dispose();

            }
        });


    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){ //return true if the selection is still
            deleteAccountButton.setEnabled(true);
            changeButton.setEnabled(true);
            int SelectedRow = CliTable.getSelectedRow(); //getSelectedRow returns an integer of the row selected
            if(SelectedRow >=0){ //selected row ha to be >=0 because it means you are selecting something from the table, a row.
                TableModel model = CliTable.getModel();
                //getting selected row and then selected column
                obj = model.getValueAt(SelectedRow, 0); //getting the value of the row selected and the column that we want. Then storing this data in obj1.
                textFieldUsername.setText(obj == null ? "": obj.toString());
                Object obj1 = model.getValueAt(SelectedRow, 1);
                passwordField1.setText(obj1 == null ? "":obj1.toString());
                Object obj2 = model.getValueAt(SelectedRow, 2);
                textFieldCName.setText(obj2 == null ? "":obj2.toString());
                Object obj3 = model.getValueAt(SelectedRow, 3);
                if(obj3.equals("Deleted")){
                    deleteAccountButton.setEnabled(false);
                }
                else{
                    deleteAccountButton.setEnabled(true);
                }

            }
        }
    }

    public static void main(String[] args) {
        CaccFrame.setContentPane(new Client_accounts().Client_accounts);
        CaccFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CaccFrame.pack();
        CaccFrame.setVisible(true);
    }


}
