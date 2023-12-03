import data_access.database.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usecase.signup.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SignupInteractorTest {

    @Mock
    private SignupOutputBoundary mockPresenter;

    @Mock
    private SignupDataAccessInterface mockDao;

    @InjectMocks
    private SignupInteractor signupInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        // Arrange
        SignupInputData mockInputData = new SignupInputData("MockUser", "MockPassword");

        // Act
        signupInteractor.register(mockInputData);

        // Assert
        verify(mockPresenter).presentSuccess(any(SignupOutputData.class));
        verify(mockPresenter, never()).presentFailure(anyString());
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        // Arrange
        SignupInputData mockInputData = new SignupInputData("ExistingUser", "MockPassword");
        try {
            doThrow(UserAlreadyExistsException.class).when(mockDao).registerUser("ExistingUser", "MockPassword");
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

        // Act
        signupInteractor.register(mockInputData);

        // Assert
        verify(mockPresenter, never()).presentSuccess(any(SignupOutputData.class));
        verify(mockPresenter).presentFailure("User ExistingUser already exists!");
    }

    @Test
    public void testGetUser() {
        SignupInputData mockInputData = new SignupInputData("MockUser", "MockPassword");

        // Act
        signupInteractor.register(mockInputData);
        SignupOutputData user = new SignupOutputData("MockUser");

        // Assert
        assertEquals(user.getUsername(), "MockUser");


    }
}
