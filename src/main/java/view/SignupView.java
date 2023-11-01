package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import interface_adapters.login.LoginController;
import interface_adapters.login.LoginViewModel;
import interface_adapters.signup.SignupController;
import interface_adapters.signup.SignupPresenter;
import interface_adapters.signup.SignupViewModel;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;

public class SignupView extends JPanel {
    private SignupViewModel viewModel;
    private SignupController controller;

    private JLabel usernameLabel = new JLabel("Username: ");
    private JTextField usernameField = new JTextField();
    private JPanel usernameInputPanel = new JPanel();

    private JLabel passwordLabel = new JLabel("Password: ");
    private JPasswordField passwordField = new JPasswordField();
    private JPanel passwordInputPanel = new JPanel();

    private JLabel repeatPasswordLabel = new JLabel("Confirm Password: ");
    private JPasswordField repeatPasswordField = new JPasswordField();
    private JPanel repeatPasswordInputPanel = new JPanel();

    private JPanel iconPanel = new JPanel();
    private JPanel backgroundPanel = new JPanel();

    private JButton submitButton = new JButton("Sign up");
    private JButton loginButton = new JButton("Log in");
    private JPanel buttonPanel = new JPanel();

    private JPanel signupPanel = new JPanel(new BorderLayout());

    private JPanel overlayPanel = new JPanel();

    private JLayeredPane layeredPane = new JLayeredPane();


    private BoxLayout boxLayout = new BoxLayout(signupPanel, BoxLayout.PAGE_AXIS);

    public SignupView(SignupViewModel viewModel, SignupController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        // try {
        //     BufferedImage myPicture = ImageIO.read(new File("src/main/java/view/PokeTraderLogo.png"));
        //     JLabel picIcon = new JLabel(new ImageIcon(myPicture));
        //     iconPanel.add(picIcon);
        //     loginPanel.add(iconPanel);

        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        // try {
        //     BufferedImage myPicture = ImageIO.read(new File("src/main/java/view/PokeTraderBackground.png"));
        //     JLabel picIcon = new JLabel(new ImageIcon(myPicture));
        //     backgroundPanel.add(picIcon);
        //     overlayPanel.add(backgroundPanel);

        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        usernameInputPanel.add(usernameLabel);
        usernameInputPanel.add(usernameField);

        passwordInputPanel.add(passwordLabel);
        passwordInputPanel.add(passwordField);

        repeatPasswordInputPanel.add(repeatPasswordLabel);
        repeatPasswordInputPanel.add(repeatPasswordField);
        
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

                controller.signup(viewModel.getUsername(), viewModel.getPassword());
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                    SignupView.this, 
                    "Signup has not been implemented", 
                    "Unimplemented feature", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(loginButton);
        signupPanel.setLayout(boxLayout);

        signupPanel.add(usernameInputPanel);
        signupPanel.add(passwordInputPanel);
        signupPanel.add(repeatPasswordInputPanel);
        signupPanel.add(buttonPanel);
        signupPanel.setSize(340, 240);
        signupPanel.setLocation(310, 100);
        overlayPanel.setSize(960, 600);
        overlayPanel.setLocation(10, -50);

        layeredPane.setPreferredSize(new Dimension(980, 600));
        layeredPane.setLocation(100, 200);
        layeredPane.add(signupPanel, 1);
        layeredPane.add(overlayPanel, 2);

        add(layeredPane);
    }

    public static SignupView create(SignupViewModel signupViewModel) {
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(signupViewModel);
        SignupInputBoundary signupInputBoundary = new SignupInteractor(signupOutputBoundary);
        SignupController signupController = new SignupController(signupInputBoundary);

        return new SignupView(signupViewModel, signupController);
    }
}
