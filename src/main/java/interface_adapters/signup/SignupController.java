package interface_adapters.signup;

import usecase.signup.SignupInputBoundary;

public class SignupController {
    public SignupInputBoundary inputBoundary;

    public SignupController(SignupInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }
}
