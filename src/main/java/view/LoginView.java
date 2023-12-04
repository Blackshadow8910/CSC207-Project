package view;

import app.GUIManager;
import interface_adapters.login.LoginController;
import interface_adapters.login.LoginViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    public final String viewName = "log in";
    private final GUIManager guiManager;
    private final LoginViewModel loginviewModel;

    private final LoginController loginController;

    private final JLabel usernameLabel = new JLabel("Username: ");
    private final JTextField usernameField = new JTextField();
    private final JPanel usernameInputPanel = new JPanel();

    private final JPanel iconPanel = new JPanel();

    private final JPanel backgroundPanel = new JPanel();
    private final JLabel passwordLabel = new JLabel("Password: ");
    private final JPasswordField passwordField = new JPasswordField();
    private final JPanel passwordInputPanel = new JPanel();

    private final JButton submitButton = new JButton("Log in");
    private final JButton signupButton = new JButton("Sign up");
    private final JPanel buttonPanel = new JPanel();

    private final JPanel loginPanel = new JPanel(new BorderLayout());

    private final JPanel overlayPanel = new JPanel();

    private final JLayeredPane layeredPane = new JLayeredPane();


    private final BoxLayout boxLayout = new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS);

    public LoginView(LoginViewModel viewModel, LoginController loginController, GUIManager guiManager) {
        this.loginviewModel = viewModel;
        this.loginController = loginController;
        this.guiManager = guiManager;

        try {
            BufferedImage myPicture = ImageIO.read(new File("resources/img/PokeTraderLogo.png"));
            JLabel picIcon = new JLabel(new ImageIcon(myPicture));
            iconPanel.add(picIcon);
            loginPanel.add(iconPanel);

            myPicture = ImageIO.read(new File("resources/img/PokeTraderBackground.png"));
            picIcon = new JLabel(new ImageIcon(myPicture));
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
                guiManager.showView("signup");
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
                } else if (evt.getPropertyName().equals("username")) {
                    usernameField.setText((String)evt.getNewValue());
                } else if (evt.getPropertyName().equals("password")) {
                    passwordField.setText((String)evt.getNewValue());
                }
            }
        });

        add(layeredPane);
    }

    public JButton getLoginButton() {
        return submitButton;
    }

    public JButton getSignButton() {
        return signupButton;
    }
}