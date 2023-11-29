package interface_adapters.signup;

import kotlin.NotImplementedError;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInputData;

import static interface_adapters.signup.SignupRestrictions.*;

public class SignupController {
    public SignupInputBoundary inputBoundary;

    public SignupController(SignupInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void signup(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        switch(username_verifier(username))
        {
            case "Alphanum Error":
                // Create JPanel for Length Error
                return;
            case "LengthError":
                // Create JPanel for Length Error
                return;
        }
        switch(password_verifier(password))
        {
            case "LengthError":
                // Create JPanel for Length Error
                return;
            case "" :
                return;
        }
        if (username_in_password(username, password))
        {
            // Create JPanel for username in password error
            return;
        }
        //TODO Remove the next line, using it to test stuff
        System.out.println("Successfully Signed Up!");
        inputBoundary.register(new SignupInputData(username, password));



    }

}
