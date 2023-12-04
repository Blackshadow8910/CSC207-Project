package interface_adapters.signup;

import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInputData;

public class SignupController {
    public final SignupInputBoundary inputBoundary;

    public SignupController(SignupInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void signup(String username, String password) {
        inputBoundary.register(new SignupInputData(username, password));
    }

}
