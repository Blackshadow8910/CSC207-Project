package app;

import interface_adapters.login.LoginViewModel;
import interface_adapters.signup.SignupController;
import interface_adapters.signup.SignupPresenter;
import interface_adapters.signup.SignupViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.app.AppViewModel;

import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;
import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;
import view.SignupView;

import javax.swing.*;
import java.io.IOException;

public class SignupUseCaseFactory {

    /** Prevent instantiation. */
    private SignupUseCaseFactory() {}

    public static SignupView create(SignupViewModel signupViewModel, AppViewModel appViewModel,
                                    LoginDataAccessInterface dataAccessObject, GUIManager guiManager) {

        LoginViewModel loginViewModel = new LoginViewModel();
        ViewManagerModel viewManagerModel = null;
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        SignupInputBoundary signupInputBoundary = new SignupInteractor(signupOutputBoundary);
        SignupController signupController = new SignupController(signupInputBoundary);

        return new SignupView(signupViewModel, signupController);

    }
}
