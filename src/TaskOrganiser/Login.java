package TaskOrganiser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Login extends AbstractSuperclass {
    //CREATE ENCAPSULATED CLASS
    public JPanel Login;
    private JComboBox RolecomboBox1;
    private JPasswordField passwordField1;
    private JTextField UsernametextField;
    private JButton submitButton;
    private JCheckBox showPasswordCheckBox;
    static Integer Rvalue;
    static JFrame framelogin = new JFrame("Login");


    static String role;

    Connection connection = null;
    public Login() {
        connection= SQLite_Connection.dbConnector();
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

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    role = RolecomboBox1.getSelectedItem().toString();
                    if (UsernametextField.getText().equals("")||(passwordField1.getText().equals(""))){
                        JOptionPane.showMessageDialog(null,"You have left field/s empty");
                    }
                    else {
                        if (role.equals("Administrator")) {
                            Rvalue = AbstractSuperclass.checkExistance("select * from Administrators where AdminUsername ='"+UsernametextField.getText()+"' AND Password ='"+passwordField1.getText()+"' AND Status ='"+"Existing"+"'");

                            if (Rvalue == 1) {
                                JOptionPane.showMessageDialog(null, "Valid fields ");

                                framelogin.dispose();
                                JFrame frame = new JFrame("Menu");
                                frame.setContentPane(new Menu().Menu);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.pack();
                                frame.setVisible(true);

                            }else if (Rvalue == 0) {
                                JOptionPane.showMessageDialog(null, "Invalid fields ");

                            }
                        }
                        else if (role.equals("Employee")) {
                            Rvalue = AbstractSuperclass.checkExistance("select * from Employees where EmpUsername ='"+UsernametextField.getText()+"' AND Password ='"+passwordField1.getText()+"'AND Status ='"+"Existing"+"'");
                            if (Rvalue == 1) {
                                //SHOW CORRESPONDING PORTAL
                                USERNAME = UsernametextField.getText();
                                JOptionPane.showMessageDialog(null, "Valid fields");
                                framelogin.dispose();
                                JFrame frame = new JFrame("Employee_portal");
                                frame.setContentPane(new Employee_portal().Employee_portal);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.pack();
                                frame.setVisible(true);
                            }else if (Rvalue == 0) {
                                JOptionPane.showMessageDialog(null, "Invalid fields ");

                            }
                        }
                        else if (role.equals("Client")) {
                            Rvalue = AbstractSuperclass.checkExistance("select * from Clients where CliUsername ='"+UsernametextField.getText()+"' AND Password ='"+passwordField1.getText()+"'AND Status ='"+"Existing"+"'");
                            if (Rvalue == 1) {
                                JOptionPane.showMessageDialog(null, "Valid fields");
                                USERNAME = UsernametextField.getText();
                                framelogin.dispose();
                                JFrame frame = new JFrame("Client_portal");
                                frame.setContentPane(new Client_portal().Client_portal);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.pack();
                                frame.setVisible(true);


                            }else if (Rvalue == 0) {
                                JOptionPane.showMessageDialog(null, "Invalid fields");

                            }
                        }
                    }

                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        framelogin.setContentPane(new Login().Login);
        framelogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framelogin.pack();
        framelogin.setVisible(true);

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}


