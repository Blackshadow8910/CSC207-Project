package usecase.signup;

public class SignupInteractor implements SignupInputBoundary {
    private SignupOutputBoundary presenter;

    public SignupInteractor(SignupOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void execute(SignupInputData inputData) {

    } 
}
