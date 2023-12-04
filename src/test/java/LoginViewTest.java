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

public class LoginViewTest {

    @Mock
    private LoginController mockLoginController;

    @Mock
    private GUIManager mockGUIManager;

    @InjectMocks
    private LoginViewModel loginViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Additional setup if needed

        LoginView loginView = new LoginView(loginViewModel, mockLoginController, mockGUIManager);
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

}