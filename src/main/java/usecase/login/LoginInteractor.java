package usecase.login;

import entity.User;

public class LoginInteractor implements LoginInputBoundary {
    public LoginDataAccessInterface databaseAccessObject;
    public LoginOutputBoundary presenter;

    public LoginInteractor(
        LoginDataAccessInterface databaseAccessObject, 
        LoginOutputBoundary presenter
    ) {
        this.databaseAccessObject = databaseAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void login(LoginInputData inputData) {
        User u = databaseAccessObject.getUser(inputData.username, inputData.password);

        if (u == null) {
            presenter.presentFailure("Incorrect username or password.");
        } else {
            presenter.presentSuccess(new LoginOutputData(u));
        }
    }
}
