package view;

import com.formdev.flatlaf.FlatDarculaLaf;

import app.GUIManager;

import javax.imageio.ImageIO;
import javax.swing.*;

import interface_adapters.app.AppViewModel;
import interface_adapters.login.LoginController;
import interface_adapters.login.LoginPresenter;
import interface_adapters.login.LoginViewModel;
import usecase.login.LoginDataAccessInterface;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import usecase.login.LoginOutputBoundary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class LoginView extends JPanel {
    private LoginViewModel viewModel;
    private LoginController loginController;

    private JLabel usernameLabel = new JLabel("Username: ");
    private JTextField usernameField = new JTextField();
    private JPanel usernameInputPanel = new JPanel();

    private JPanel iconPanel = new JPanel();

    private JPanel backgroundPanel = new JPanel();
    private JLabel passwordLabel = new JLabel("Password: ");
    private JPasswordField passwordField = new JPasswordField();
    private JPanel passwordInputPanel = new JPanel();

    private JButton submitButton = new JButton("Log in");
    private JButton signupButton = new JButton("Sign up");
    private JPanel buttonPanel = new JPanel();

    private JPanel loginPanel = new JPanel(new BorderLayout());

    private JPanel overlayPanel = new JPanel();

    private JLayeredPane layeredPane = new JLayeredPane();


    private BoxLayout boxLayout = new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS);

    public LoginView(LoginViewModel viewModel, LoginController loginController) {
        this.viewModel = viewModel;
        this.loginController = loginController;

        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/java/view/PokeTraderLogo.png"));
            JLabel picIcon = new JLabel(new ImageIcon(myPicture));
            iconPanel.add(picIcon);
            loginPanel.add(iconPanel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedImage myPicture = ImageIO.read(new File("src/main/java/view/PokeTraderBackground.png"));
            JLabel picIcon = new JLabel(new ImageIcon(myPicture));
            backgroundPanel.add(picIcon);
            overlayPanel.add(backgroundPanel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        usernameInputPanel.add(usernameLabel);
        usernameInputPanel.add(usernameField);

        passwordInputPanel.add(passwordLabel);
        passwordInputPanel.add(passwordField);
        
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitButton.doClick();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.setUsername(usernameField.getText());
                viewModel.setPassword(String.valueOf(passwordField.getPassword()));

                loginController.login(viewModel.getUsername(), viewModel.getPassword());
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    LoginView.this, 
                    "Signup has not been implemented", 
                    "Unimplemented feature", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(signupButton);
        loginPanel.setLayout(boxLayout);

        loginPanel.add(usernameInputPanel);
        loginPanel.add(passwordInputPanel);
        loginPanel.add(buttonPanel);
        loginPanel.setSize(340, 240);
        loginPanel.setLocation(310, 100);
        overlayPanel.setSize(960, 600);
        overlayPanel.setLocation(10, -50);

        layeredPane.setPreferredSize(new Dimension(980, 600));
        layeredPane.setLocation(100, 200);
        layeredPane.add(loginPanel, 1);
        layeredPane.add(overlayPanel, 2);

        viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("feedbackMessage")) {
                    JOptionPane.showMessageDialog(
                        LoginView.this, 
                        viewModel.getFeedbackMessage(), 
                        "", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        add(layeredPane);
    }

    public static LoginView create(LoginViewModel loginViewModel, AppViewModel appViewModel, LoginDataAccessInterface dataAccessObject, GUIManager guiManager) {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(loginViewModel, appViewModel, guiManager);
        LoginInputBoundary loginInputBoundary = new LoginInteractor(dataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInputBoundary);
        LoginView loginView = new LoginView(loginViewModel, loginController);
        
        return loginView;
    }
}
