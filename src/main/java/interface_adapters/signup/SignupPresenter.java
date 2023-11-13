package interface_adapters.signup;

import interface_adapters.login.LoginState;
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
    public void present(SignupOutputData outputData) {

    }

    @Override
    public void presentSuccess(SignupOutputData data) {
        // LocalDateTime responseTime = LocalDateTime.parse(data.getCreationTime());
        // data.setCreationTime(responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        LoginState loginState = loginViewModel.getState();
        loginState.setUsername(data.getUsername());
        this.loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(message);
        signupViewModel.firePropertyChanged();

    }
}
