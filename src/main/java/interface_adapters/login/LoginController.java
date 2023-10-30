package interface_adapters.login;

import usecase.login.LoginInputBoundary;

public class LoginController {
    public LoginInputBoundary loginInteractor;

    public LoginController(LoginInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }
}
