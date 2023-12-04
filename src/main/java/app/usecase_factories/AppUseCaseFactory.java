package app.usecase_factories;

import app.GUIManager;
import interface_adapters.app.AppController;
import interface_adapters.app.AppPresenter;
import interface_adapters.app.AppViewModel;
import usecase.app.AppInputBoundary;
import usecase.app.AppInteractor;
import usecase.app.AppOutputBoundary;
import view.app.AppView;

public class AppUseCaseFactory {

    /** Prevent instantiation. */
    private AppUseCaseFactory() {}

    public static AppView create(AppViewModel appViewModel, GUIManager guiManager) {
        AppOutputBoundary appOutputBoundary = new AppPresenter(appViewModel);
        AppInputBoundary appInputBoundary = new AppInteractor(appOutputBoundary);
        AppController appController = new AppController(appInputBoundary);

        return new AppView(appViewModel, appController, guiManager);
    }
}
