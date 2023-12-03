import app.GUIManager;
import app.Main;
import interface_adapters.login.LoginController;
import interface_adapters.login.LoginViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.LoginView;
import static org.junit.Assert.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginViewTest {

    @Mock
    private LoginController mockLoginController;

    @Mock
    private GUIManager mockGUIManager;

    @InjectMocks
    private LoginViewModel loginViewModel;

    private LoginView loginView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Additional setup if needed

        loginView = new LoginView(loginViewModel, mockLoginController, mockGUIManager);
    }

    public JButton getButtonlogin() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        assertNotNull(app); // found the window?

        Component root = app.getComponent(0);

        Component cp = ((JRootPane) root).getContentPane();

        JPanel jp = (JPanel) cp;

        JPanel jp2 = (JPanel) jp.getComponent(0);

        LoginView lv = (LoginView) jp2.getComponent(0);

        return lv.getLoginButton();
    }

    public JButton getButtonSign() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        assertNotNull(app); // found the window?

        Component root = app.getComponent(0);

        Component cp = ((JRootPane) root).getContentPane();

        JPanel jp = (JPanel) cp;

        JPanel jp2 = (JPanel) jp.getComponent(0);

        LoginView lv = (LoginView) jp2.getComponent(0);

        return lv.getSignButton();
    }

    @Test
    public void testLoginButtonPresent() {
        Main.main(null);
        JButton button = getButtonlogin();
        assert(button.getText().equals("Log in"));
    }

    @Test
    public void testSignUpButtonPresent() {
        Main.main(null);
        JButton button = getButtonSign();
        assert(button.getText().equals("Sign up"));
    }

    /* @Test
    public void testSubmitButtonAction() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        loginView.setUsernameFieldText(username);
        loginView.setPasswordFieldText(password);

        // Act
        loginView.simulateSubmitButtonClick();

        // Assert
        // Verify that the loginController's login method was called with the correct arguments
        verify(mockLoginController, times(1)).login(username, password);
    }

    @Test
    public void testSignupButtonAction() {
        // Arrange
        // ...

        // Act
        loginView.simulateSignupButtonClick();

        // Assert
        // Verify that the GUIManager's showView method was called with the correct argument
        verify(mockGUIManager, times(1)).showView("signup");
    }

    // Add more test methods as needed to cover other functionalities

    // Helper methods for testing interactions with UI components

    private static class MockTextField extends JTextField {
        private String text;

        @Override
        public String getText() {
            return text;
        }

        @Override
        public void setText(String text) {
            this.text = text;
        }
    }

    private void setUsernameFieldText(String text) {
        ((MockTextField) loginView.getUsernameField()).setText(text);
    }

    private void setPasswordFieldText(String text) {
        ((MockTextField) loginView.getPasswordField()).setText(text);
    }

    private void simulateSubmitButtonClick() {
        ActionListener[] actionListeners = loginView.getSubmitButton().getActionListeners();
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
    }

    private void simulateSignupButtonClick() {
        ActionListener[] actionListeners = loginView.getSignupButton().getActionListeners();
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
    }
    */
}