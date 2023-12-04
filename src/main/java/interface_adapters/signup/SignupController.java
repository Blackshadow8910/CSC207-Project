package interface_adapters.signup;

import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInputData;

import static interface_adapters.signup.SignupRestrictions.*;

public class SignupController {
    public SignupInputBoundary inputBoundary;

    public SignupController(SignupInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void signup(String username, String password) {
        inputBoundary.register(new SignupInputData(username, password));
    }

}
