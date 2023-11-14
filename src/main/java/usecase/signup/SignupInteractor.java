package usecase.signup;

import data_access.database.UserAlreadyExistsException;

public class SignupInteractor implements SignupInputBoundary {
    private SignupOutputBoundary presenter;
    private SignupDataAccessInterface dataAccessObject;

    public SignupInteractor(SignupOutputBoundary presenter, SignupDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    public void register(SignupInputData inputData) {
        try {
            dataAccessObject.registerUser(inputData.getUsername(), inputData.getPassword());
            presenter.presentSuccess(new SignupOutputData(inputData.getUsername()));
        } catch (UserAlreadyExistsException e) {
            presenter.presentFailure("User %s already exists!".formatted(inputData.getUsername()));
        }

    }
}
