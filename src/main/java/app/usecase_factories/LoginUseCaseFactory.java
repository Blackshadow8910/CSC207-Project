package app.usecase_factories;


import interface_adapters.login.LoginController;
import interface_adapters.login.LoginPresenter;
import interface_adapters.login.LoginViewModel;
import interface_adapters.app.AppViewModel;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginDataAccessInterface;
import view.LoginView;

import javax.swing.*;

import app.GUIManager;

import java.io.IOException;

public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {}

    public static LoginView create(LoginViewModel loginViewModel, AppViewModel appViewModel,
                                   LoginDataAccessInterface dataAccessObject, GUIManager guiManager) {

        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(loginViewModel, appViewModel, guiManager);
        LoginInputBoundary loginInputBoundary = new LoginInteractor(dataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInputBoundary);

        return new LoginView(loginViewModel, loginController, guiManager);

    }
}
