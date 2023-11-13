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
    public void present(SignupOutputData outputData) {

    }

    @Override
    public void presentSuccess(SignupOutputData data) {
        // LocalDateTime responseTime = LocalDateTime.parse(data.getCreationTime());
        // data.setCreationTime(responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        this.loginViewModel.setUsername(data.getUsername());
    }

    @Override
    public void presentFailure(String message) {
        signupViewModel.setFeedbackMessage(message);
    }
}
