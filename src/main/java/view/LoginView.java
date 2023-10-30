package view;

import javax.swing.JTextField;

import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class LoginView extends JPanel {
    private JLabel usernameLabel = new JLabel("Username: ");
    private JTextField usernameField = new JTextField();
    private JPanel usernameInputPanel = new JPanel();

    private JLabel passwordLabel = new JLabel("Password: ");
    private JPasswordField passwordField = new JPasswordField();
    private JPanel passwordInputPanel = new JPanel();

    private JButton submitButton = new JButton("Log in");
    private JButton signupButton = new JButton("Sign up");
    private JPanel buttonPanel = new JPanel();

    private JPanel mainPanel = new JPanel(new BorderLayout());


    private BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);


    public LoginView() {

        usernameInputPanel.add(usernameLabel);
        usernameInputPanel.add(usernameField);
        usernameInputPanel.setSize(520, 342);
        usernameField.setSize(520, 342);
        
        passwordInputPanel.add(passwordLabel);
        passwordInputPanel.add(passwordField);

        buttonPanel.add(submitButton);
        buttonPanel.add(signupButton);
        mainPanel.setLayout(boxLayout);

        mainPanel.add(usernameInputPanel);
        mainPanel.add(passwordInputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.setSize(520, 342);
        setLayout(new GridBagLayout());

        add(mainPanel);

        setSize(520, 2304);

    }
}
