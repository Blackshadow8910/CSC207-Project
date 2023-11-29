package interface_adapters.login;

import app.GUIManager;
import interface_adapters.app.AppViewModel;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;
import view.app.AppView;

public class LoginPresenter implements LoginOutputBoundary {
    private LoginViewModel viewModel;
    private AppViewModel appViewModel;
    private GUIManager guiManager;

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
