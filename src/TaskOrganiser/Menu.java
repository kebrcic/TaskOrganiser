package TaskOrganiser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    public JPanel Menu;
    private JButton clientsAccountsButton;
    private JButton employeesAccountsButton;
    private JButton administratorsAccountsButton;
    private JButton viewTasksButton;
    private JButton exitButton;
    static JFrame menuFrame = new JFrame("Menu");

    public Menu() {
        administratorsAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                JFrame frame = new JFrame("Admin_accounts");
                frame.setContentPane(new Admin_accounts().Admin_accounts);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        clientsAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                JFrame frame = new JFrame("Client_accounts");
                frame.setContentPane(new Client_accounts().Client_accounts);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        employeesAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                JFrame frame = new JFrame("Employee_accounts");
                frame.setContentPane(new Employee_accounts().Employee_accounts);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        viewTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                JFrame frame = new JFrame("Tasks_management");
                frame.setContentPane(new Tasks_management().Tasks_management);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                JFrame frame = new JFrame("Login");
                frame.setContentPane(new Login().Login);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        menuFrame.setContentPane(new Menu().Menu);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.pack();
        menuFrame.setVisible(true);

    }
}


