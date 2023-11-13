package usecase.signup;

import usecase.login.LoginOutputData;

public interface SignupOutputBoundary {
    public void presentSuccess();

    public void presentFailure(String message);
}
