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

import app.GUIManager;
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
    private JPanel usernameLabelPanel = new JPanel();
    private JPanel usernameInputPanel = new JPanel();
    private JLabel existingAccountLabel = new JLabel("Already have an account?");
    private JPanel existingAccountPanel = new JPanel();

    private JLabel passwordLabel = new JLabel("Password: ");
    private JPasswordField passwordField = new JPasswordField();

    private JLabel repeatPasswordLabel = new JLabel("Confirm Password:     ");
    private JPasswordField repeatPasswordField = new JPasswordField();

    private JPanel backgroundPanel = new JPanel();

    private JButton submitButton = new JButton("Sign up");
    private JButton loginButton = new JButton("Log in");

    private JPanel signupPanel = new JPanel(new GridBagLayout());
    private JPanel mainPanel = new JPanel(new GridBagLayout());
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

        gbc.gridy = 0;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = 1;
        signupPanel.add(usernameLabel, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);
        gbc.gridy = 3;
        gbc.weighty = 1;
        signupPanel.add(passwordLabel, gbc);
        gbc.gridy = 4;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);
        gbc.gridy = 5;
        gbc.weighty = 1;
        signupPanel.add(repeatPasswordLabel, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.gridx = 1;
        signupPanel.add(usernameField, gbc);
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);
        gbc.gridy = 3;
        gbc.weighty = 1;
        signupPanel.add(passwordField, gbc);
        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.weighty = 5;
        signupPanel.add(new JPanel(), gbc);
        gbc.gridy = 5;
        gbc.weighty = 1;
        signupPanel.add(repeatPasswordField, gbc);

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
                controller.signup(username, password);
                usernameField.setText("");
                passwordField.setText("");
                repeatPasswordField.setText("");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiManager.showView("login");
            }
        });

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 0;
        mainPanel.add(signupPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.5;
        mainPanel.add(new JPanel(), gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weighty = 0;
        mainPanel.add(submitButton, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1;
        mainPanel.add(new JPanel(), gbc);

        gbc.gridy = 4;
        gbc.weighty = 0;
        mainPanel.add(existingAccountPanel, gbc);

        gbc.gridy = 5;
        gbc.weighty = 0;
        mainPanel.add(loginButton, gbc);

        gbc.gridy = 6;
        gbc.weighty = 5;
        mainPanel.add(new JPanel(), gbc);

        mainPanel.setSize(new Dimension(245, 218));
        mainPanel.setLocation(360, 100);


        overlayPanel.setSize(960, 600);
        overlayPanel.setLocation(10, -50);

        layeredPane.setPreferredSize(new Dimension(980, 600));
        layeredPane.setLocation(100, 200);
        layeredPane.add(mainPanel, 1);
        layeredPane.add(overlayPanel, 2);

        viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("feedbackMessage")) {
                    JOptionPane.showMessageDialog(
                            SignupView.this,
                            viewModel.getFeedbackMessage(),
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else if (evt.getPropertyName().equals("view")) {
                    guiManager.showView((String)evt.getNewValue());
                }
            }
        });

        add(layeredPane);
    }

    public void setLoginViewModel(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }
}