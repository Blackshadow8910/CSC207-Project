package interface_adapters.login;

import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    @Override
    public void presentSuccess(LoginOutputData data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'presentSuccess'");
    }

    @Override
    public void presentFailure(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'presentFailure'");
    }
    
}
