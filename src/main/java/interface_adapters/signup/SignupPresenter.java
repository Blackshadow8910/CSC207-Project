package interface_adapters.signup;

import interface_adapters.login.LoginViewModel;
import usecase.signup.SignupOutputBoundary;
import usecase.signup.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModel;

    private final LoginViewModel loginViewModel;

    public SignupPresenter(SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel) {
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void presentSuccess(SignupOutputData data) {
        signupViewModel.setFeedbackMessage("You are now registered with PokeTrader.");
        signupViewModel.setView("login");
        loginViewModel.setUsername(data.getUsername());
    }

    @Override
    public void presentFailure(String message) {
        signupViewModel.setFeedbackMessage(message);
    }
}
