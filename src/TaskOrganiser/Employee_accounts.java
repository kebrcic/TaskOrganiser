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

public class Employee_accounts extends AbstractSuperclass {
    public JPanel Employee_accounts;
    private JTextField textFieldLocator;
    private JTable EmpTable;
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
    static JFrame EaccFrame = new JFrame("Employee_accounts");
    static Object obj;
    static String status;
    static Employee_accounts newobj = new Employee_accounts();
    static MainEmployee_accounts objmaine = new MainEmployee_accounts();

    public Employee_accounts() {
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
        ListSelectionModel selectionModel = EmpTable.getSelectionModel();
        //Add listener to this table in this class
        selectionModel.addListSelectionListener(this);
        textFieldLocator.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try{
                    AbstractSuperclass.UseLocator(textFieldLocator.getText(), EmpTable.getModel(), EmpTable);
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
                    String query1 = "select * from Employees";
                    PreparedStatement pst = connection.prepareStatement(query1);
                    ResultSet rs = pst.executeQuery();
                    EmpTable.setModel(DbUtils.resultSetToTableModel(rs));
                    AbstractSuperclass.showColumn(EmpTable);
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
                String pa =AbstractSuperclass.generatePassword("EMP");
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
                    if ((textFieldUsername.getText().equals(""))|| (textFieldName.getText().equals(""))||(textFieldLastName.getText().equals(""))|| (passwordField1.getText().equals(""))){
                        JOptionPane.showMessageDialog(null, "You have left one of the fields empty");
                    }
                    String passwordcheck = passwordField1.getText();
                    if(!passwordcheck.contains("EMP")){
                        JOptionPane.showMessageDialog(null, "The password needs to have 'EMP' prefix ");
                    }
                    Integer valuer = AbstractSuperclass.checkExistance("select * from Employees where EmpUsername ='"+textFieldUsername.getText()+"'");
                    if (valuer == 1){
                        JOptionPane.showMessageDialog(null, "The username already exists");
                    }
                    if ((!textFieldUsername.getText().contains("@"))){
                        JOptionPane.showMessageDialog(null, "The character '@' is required in the username");
                    }

                    if ((valuer == 1)||(textFieldUsername.getText().equals(""))|| (textFieldName.getText().equals(""))||(textFieldLastName.getText().equals(""))||
                            (passwordField1.getText().equals(""))||(!passwordcheck.contains("EMP"))||(!textFieldUsername.getText().contains("@"))){
                        JOptionPane.showMessageDialog(null, "Invalid");
                    }
                    else{
                        objmaine.setUsername(textFieldUsername.getText());
                        objmaine.setPassword(passwordField1.getText());
                        objmaine.setName(textFieldName.getText());
                        objmaine.setLastName(textFieldLastName.getText());
                        objmaine.setStatus(comboBoxStatus.getSelectedItem().toString());
                        AbstractSuperclass.createAccAdminEmp("INSERT INTO Employees(EmpUsername,Password,Name,LastName,Status) VALUES(?,?,?,?,?)", objmaine.getUsername(), objmaine.getPassword(), objmaine.getName(), objmaine.getLastName(), objmaine.getStatus());

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
                AbstractSuperclass.delete("update Employees set Status='Deleted' where EmpUsername='"+obj+"'");
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
                    if (!passwordcheck.contains("EMP")) {
                        JOptionPane.showMessageDialog(null, "The password needs to have 'EMP' prefix ");
                    } else {
                        objmaine.setPassword(passwordField1.getText());
                        objmaine.setName(textFieldName.getText());
                        objmaine.setLastName(textFieldLastName.getText());
                        AbstractSuperclass.update("UPDATE Employees set Password = '" + objmaine.getPassword() + "', Name = '" + objmaine.getName() + "', LastName= '" + objmaine.getLastName() + "'where EmpUsername ='" + obj + "'");
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
                EaccFrame.dispose();

            }
        });


    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){ //return true if the selection is still
            deleteAccountButton.setEnabled(true);
            changeButton.setEnabled(true);
            int SelectedRow = EmpTable.getSelectedRow(); //getSelectedRow returns an integer of the row selected
            if(SelectedRow >=0){ //selected row ha to be >=0 because it means you are selecting something from the table, a row.
                TableModel model = EmpTable.getModel();
                //getting selected row and then selected column
                obj = model.getValueAt(SelectedRow, 0); //getting the value of the row selected and the column that we want. Then storing this data in obj1.
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
        EaccFrame.setContentPane(new Employee_accounts().Employee_accounts);
        EaccFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EaccFrame.pack();
        EaccFrame.setVisible(true);
    }
}
