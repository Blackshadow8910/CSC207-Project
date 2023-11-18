package usecase.signup;

import usecase.login.LoginOutputData;

public interface SignupOutputBoundary {

    public void presentSuccess(SignupOutputData data);

    public void presentFailure(String message);
}
