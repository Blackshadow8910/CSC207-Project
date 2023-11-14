package interface_adapters.signup;

import kotlin.NotImplementedError;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInputData;

public class SignupController {
    public SignupInputBoundary inputBoundary;

    public SignupController(SignupInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void signup(String username, String password) {
        inputBoundary.register(new SignupInputData(username, password));
    }
}
