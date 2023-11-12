package usecase.signup;

import usecase.login.LoginOutputData;

public interface SignupOutputBoundary {
    public void present(SignupOutputData outputData);

    public void presentSuccess(SignupOutputData data);

    public void presentFailure(String message);
}
