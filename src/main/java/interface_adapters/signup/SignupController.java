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
        switch(username_verifier(username))
        {
            case "AlphanumError":
                windowCreator("Username Error", "Make sure your username consists of only letters and number");
                return;
            case "LengthError":
                windowCreator("Username Error", "Make sure your username has at least 3 and at most 30 character");
                return;
        }
        switch(password_verifier(username ,password))
        {
            case "LengthError":
                windowCreator("Password Error", "Make sure your password has at least 8 characters and at most 30");
                return;
            case "AlphanumError" :
                windowCreator("Password Error", "Make sure your password consists of only letters and numbers");
                return;
            case "ComplexityError":
                windowCreator("Password Error", "Make sure your password has at least one uppercase character, one lowercase character, and one number");
                return;
            case "UsernameInPasswordError":
                windowCreator("Password Error", "Make your password stronger by not putting your username in it");
                return;
        }
        inputBoundary.register(new SignupInputData(username, password));



    }

}
