package interface_adapters.signup;

import usecase.signup.SignupOutputBoundary;
import usecase.signup.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {
    private SignupViewModel viewModel;
    
    public SignupPresenter(SignupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SignupOutputData outputData) {

    }
}
