import app.GUIManager;
import entity.User;
import interface_adapters.app.AppViewModel;
import interface_adapters.login.LoginPresenter;
import interface_adapters.login.LoginViewModel;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import usecase.login.LoginOutputData;

public class LoginPresenterTest {

    @Mock
    private LoginViewModel mockViewModel;

    @Mock
    private AppViewModel mockAppViewModel;

    @Mock
    private GUIManager mockGuiManager;

    @InjectMocks
    private LoginPresenter loginPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPresentSuccess() {
        // Arrange
        User mockUser = new User("MockUser", "123456");
        LoginOutputData mockOutputData = new LoginOutputData(mockUser);


        // Act
        loginPresenter.presentSuccess(mockOutputData);

        // Assert
        verify(mockViewModel).setPassword("");
        verify(mockViewModel).setUsername("");
        verify(mockAppViewModel).setCurrentUser(mockUser);
        verify(mockGuiManager).showView(any());
    }

    @Test
    public void testPresentFailure() {
        // Arrange
        String errorMessage = "Invalid credentials";

        // Act
        loginPresenter.presentFailure(errorMessage);

        // Assert
        verify(mockViewModel).setFeedbackMessage(errorMessage);
    }

    @Test
    public void testViewModel() {
        // Arrange
        LoginViewModel loginViewModel = new LoginViewModel();


        // Act
        loginViewModel.setPassword("hi");
        loginViewModel.setUsername("test");
        loginViewModel.setFeedbackMessage("message");

        // Assert
        assertEquals("test", loginViewModel.getUsername());
        assertEquals("hi", loginViewModel.getPassword());
        assertEquals("message", loginViewModel.getFeedbackMessage());

    }

}