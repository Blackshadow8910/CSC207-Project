package usecase.login;

public interface LoginOutputBoundary {
    public void presentSuccess(LoginOutputData data);

    public void presentFailure(String message);
}
