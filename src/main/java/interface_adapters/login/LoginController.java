package interface_adapters.login;

import usecase.login.LoginInputBoundary;
import usecase.login.LoginInputData;

public class LoginController {
    public final LoginInputBoundary loginInteractor;

    public LoginController(LoginInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    public void login(String username, String password) {
        loginInteractor.login(new LoginInputData(username, password));
    }
}
