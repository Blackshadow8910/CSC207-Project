package interface_adapters.app;

import usecase.app.AppInputBoundary;
import usecase.app.AppInteractor;

public class AppController {
    private AppInputBoundary appInteractor;

    public AppController(AppInputBoundary appInteractor) {
        this.appInteractor = appInteractor;
    }

    public void changeTab(String tab) {
        appInteractor.changeTab(tab);
    }
}
