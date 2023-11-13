package interface_adapters.app;

import usecase.app.AppOutputBoundary;

public class AppPresenter implements AppOutputBoundary {
    private AppViewModel viewModel;

    public AppPresenter(AppViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void changeTab(String tab) {
        viewModel.setTab(tab);
    }
    
}
