package usecase.app;

public class AppInteractor implements AppInputBoundary {
    private AppOutputBoundary presenter;

    public AppInteractor(AppOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void changeTab(String tab) {
        String a = tab;
    }
}
