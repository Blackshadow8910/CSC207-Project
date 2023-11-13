package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import app.GUIManager;
import interface_adapters.ViewManagerModel;
import interface_adapters.login.LoginViewModel;
import interface_adapters.signup.SignupController;
import interface_adapters.signup.SignupPresenter;
import interface_adapters.signup.SignupViewModel;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;

public class SignupView extends JPanel {
    public final String viewName = "sign up";
    private final GUIManager guiManager;
    private SignupViewModel viewModel;
    private SignupController controller;

    private LoginViewModel loginViewModel;

    private JLabel usernameLabel = new JLabel("Username: ");
    private JTextField usernameField = new JTextField();
    private JPanel usernameInputPanel = new JPanel();

    private JLabel existingAccountLabel = new JLabel("Already have an account?");
    private JPanel existingAccountPanel = new JPanel();

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

    private JPanel signupPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();

    private JPanel overlayPanel = new JPanel();

    private JLayeredPane layeredPane = new JLayeredPane();


    public SignupView(SignupViewModel viewModel, SignupController controller, GUIManager guiManager) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.guiManager = guiManager;

        // try {
        //     BufferedImage myPicture = ImageIO.read(new File("src/main/java/view/PokeTraderLogo.png"));
        //     JLabel picIcon = new JLabel(new ImageIcon(myPicture));
        //     iconPanel.add(picIcon);
        //     loginPanel.add(iconPanel);

        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        try {
            BufferedImage myPicture = ImageIO.read(new File("resources/img/PokeSignupBackground.png"));
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

        repeatPasswordInputPanel.add(repeatPasswordLabel);
        repeatPasswordInputPanel.add(repeatPasswordField);

        existingAccountPanel.add(existingAccountLabel);

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
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String repeatPassword = String.valueOf(repeatPasswordField.getPassword());

                if (!password.equals(repeatPassword)) {
                    JOptionPane.showMessageDialog(
                            SignupView.this,
                            "Passwords must match.",
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }

                viewModel.setUsername(username);
                viewModel.setPassword(password);

                controller.signup(viewModel.getUsername(), viewModel.getPassword());
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.showView("login");
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(loginButton);

        gbc.gridy = -1;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        signupPanel.add(usernameInputPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        signupPanel.add(passwordInputPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0;
        signupPanel.add(repeatPasswordInputPanel, gbc);

        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        signupPanel.add(submitButton, gbc);

        gbc.gridy = 4;
        gbc.weighty = 1;
        signupPanel.add(new JPanel(), gbc);

        gbc.gridy = 5;
        gbc.weighty = 0;
        signupPanel.add(existingAccountPanel, gbc);

        gbc.gridy = 6;
        gbc.weighty = 0;
        signupPanel.add(loginButton, gbc);

        gbc.gridy = 7;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);

        signupPanel.setSize(new Dimension(245, 240));
        signupPanel.setLocation(360, 100);


        overlayPanel.setSize(960, 600);
        overlayPanel.setLocation(10, -50);

        layeredPane.setPreferredSize(new Dimension(980, 600));
        layeredPane.setLocation(100, 200);
        layeredPane.add(signupPanel, 1);
        layeredPane.add(overlayPanel, 2);

        add(layeredPane);
    }

    public void setLoginViewModel(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }
}