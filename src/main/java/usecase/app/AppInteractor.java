package usecase.app;

public class AppInteractor implements AppInputBoundary {
    private final AppOutputBoundary presenter;

    public AppInteractor(AppOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void changeTab(String tab) {
        presenter.changeTab(tab);
    }
}
