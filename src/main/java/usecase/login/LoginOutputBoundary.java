package usecase.login;

public interface LoginOutputBoundary {
    void presentSuccess(LoginOutputData data);

    void presentFailure(String message);
}
