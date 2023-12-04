package usecase.signup;

import data_access.database.UserAlreadyExistsException;

import static interface_adapters.signup.SignupRestrictions.passwordVerifier;
import static interface_adapters.signup.SignupRestrictions.usernameVerifier;

public class SignupInteractor implements SignupInputBoundary {
    private final SignupOutputBoundary presenter;
    private final SignupDataAccessInterface dataAccessObject;

    public SignupInteractor(SignupOutputBoundary presenter, SignupDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    public void register(SignupInputData inputData) {
        switch(usernameVerifier(inputData.getUsername()))
        {
            case "AlphanumError":
                presenter.presentFailure("Make sure your username consists of only letters and numbers");
                return;
            case "LengthError":
                presenter.presentFailure("Make sure your username has at least 3 and at most 30 character");
                return;
        }
        switch(passwordVerifier(inputData.getUsername(), inputData.getPassword()))
        {
            case "LengthError":
                presenter.presentFailure("Make sure your password has at least 8 characters and at most 30");
                return;
            case "AlphanumError" :
                presenter.presentFailure("Make sure your password consists of only letters and numbers");
                return;
            case "ComplexityError":
                presenter.presentFailure("Make sure your password has at least one uppercase character, one lowercase character, and one number");
                return;
            case "UsernameInPasswordError":
                presenter.presentFailure("Make your password stronger by not putting your username in it");
                return;
        }

        try {
            dataAccessObject.registerUser(inputData.getUsername(), inputData.getPassword());
            presenter.presentSuccess(new SignupOutputData(inputData.getUsername()));
        } catch (UserAlreadyExistsException e) {
            presenter.presentFailure("User %s already exists!".formatted(inputData.getUsername()));
        }

    }
}
