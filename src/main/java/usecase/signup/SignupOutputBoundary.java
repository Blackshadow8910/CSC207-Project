package usecase.signup;

public interface SignupOutputBoundary {

    void presentSuccess(SignupOutputData data);

    void presentFailure(String message);
}
