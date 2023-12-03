import interface_adapters.signup.SignupViewModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignupViewModelTest {

    @org.junit.Test
    public void testSetFeedbackMessage() {
        // Arrange
        SignupViewModel signupViewModel = new SignupViewModel();

        // Act
        signupViewModel.setFeedbackMessage("Test message");

        // Assert
        assert signupViewModel.getFeedbackMessage().equals("Test message");
    }

    @org.junit.Test
    public void testSetPassword() {
        // Arrange
        SignupViewModel signupViewModel = new SignupViewModel();

        // Act
        signupViewModel.setPassword("testPassword");

        // Assert
        assert signupViewModel.getPassword().equals("testPassword");
    }

    @org.junit.Test
    public void testSetUsername() {
        // Arrange
        SignupViewModel signupViewModel = new SignupViewModel();

        // Act
        signupViewModel.setUsername("testUser");

        // Assert
        assert signupViewModel.getUsername().equals("testUser");
    }

    // Add more test methods as needed
}