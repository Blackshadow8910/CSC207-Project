package usecase.login;

public class LoginInteractor implements LoginInputBoundary {
    public LoginDataAccessInterface databaseAccessObject;

    public LoginInteractor(LoginDataAccessInterface databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;
    }

    @Override
    public void login(LoginInputData inputData) {
        
    }
}
