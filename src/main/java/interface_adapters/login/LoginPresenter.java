package interface_adapters.login;

import app.GUIManager;
import interface_adapters.app.AppViewModel;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel viewModel;
    private final AppViewModel appViewModel;
    private final GUIManager guiManager;

    public LoginPresenter(LoginViewModel viewModel, AppViewModel appViewModel, GUIManager guiManager) {
        this.viewModel = viewModel;
        this.appViewModel = appViewModel;
        this.guiManager = guiManager;
    } 


    @Override
    public void presentSuccess(LoginOutputData data) {
        viewModel.setPassword("");
        viewModel.setUsername("");
        appViewModel.setCurrentUser(data.user);
        guiManager.showView(appViewModel.viewName);
    }

    @Override
    public void presentFailure(String message) {
        viewModel.setFeedbackMessage(message);
    }
    
}
