package interface_adapters.app;

import usecase.app.AppInputBoundary;

public class AppController {
    private final AppInputBoundary appInteractor;

    public AppController(AppInputBoundary appInteractor) {
        this.appInteractor = appInteractor;
    }

    public void changeTab(String tab) {
        appInteractor.changeTab(tab);
    }
}
