

import entity.User;
import org.junit.Before;
import org.junit.Test;
import usecase.login.*;

import static org.mockito.Mockito.*;

public class LoginInteractorTest {
    private LoginDataAccessInterface databaseAccessObject;
    private LoginOutputBoundary presenter;
    private LoginInteractor loginInteractor;

    @Before
    public void setUp() {
        // Create mock objects for dependencies
        databaseAccessObject = mock(LoginDataAccessInterface.class);
        presenter = mock(LoginOutputBoundary.class);

        // Initialize the LoginInteractor with mock variables
        loginInteractor = new LoginInteractor(databaseAccessObject, presenter);
    }

    @Test
    public void testLoginSuccess() {
        // Arrange
        LoginInputData inputData = new LoginInputData("validUsername", "validPassword");
        User mockedUser = new User("validUsername", "hashedValidPassword"); // Assuming a hashed password

        // Mock the behavior of the databaseAccessObject
        when(databaseAccessObject.getUser("validUsername", "validPassword")).thenReturn(mockedUser);

        loginInteractor.login(inputData);

        // Assert
        verify(presenter).presentSuccess(any(LoginOutputData.class));
    }

    @Test
    public void testLoginFailure() {
        // Arrange
        LoginInputData inputData = new LoginInputData("invalidUsername", "invalidPassword");

        // Mock the behavior of the databaseAccessObject
        when(databaseAccessObject.getUser("invalidUsername", "invalidPassword")).thenReturn(null);

        // Act
        loginInteractor.login(inputData);

        // Assert
        verify(presenter).presentFailure("Incorrect username or password.");
    }
}
