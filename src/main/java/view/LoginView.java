package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import interface_adapters.login.LoginController;
import interface_adapters.login.LoginViewModel;
import usecase.login.LoginDataAccessInterface;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginView extends JPanel {
    private LoginViewModel viewModel;
    private LoginController loginController;

    private JLabel usernameLabel = new JLabel("Username: ");
    private JTextField usernameField = new JTextField();
    private JPanel usernameInputPanel = new JPanel();

    private JPanel iconPanel = new JPanel();
    private JLabel passwordLabel = new JLabel("Password: ");
    private JPasswordField passwordField = new JPasswordField();
    private JPanel passwordInputPanel = new JPanel();

    private JButton submitButton = new JButton("Log in");
    private JButton signupButton = new JButton("Sign up");
    private JPanel buttonPanel = new JPanel();

    private JPanel mainPanel = new JPanel(new BorderLayout());


    private BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);

    public LoginView(LoginViewModel viewModel, LoginController loginController) {
        this.viewModel = viewModel;
        this.loginController = loginController;

        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/java/view/PokeTraderLogo.png"));
            JLabel picIcon = new JLabel(new ImageIcon(myPicture));
            iconPanel.add(picIcon);
            mainPanel.add(iconPanel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    public static LoginView create(LoginViewModel loginViewModel, LoginDataAccessInterface dataAccessObject) {
        LoginInputBoundary loginInputBoundary = new LoginInteractor(dataAccessObject);
        LoginController loginController = new LoginController(loginInputBoundary);
        LoginView loginView = new LoginView(loginViewModel, loginController);
        
        return loginView;
    }
}
