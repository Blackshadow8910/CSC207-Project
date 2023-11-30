package usecase.app.deckbuilder;

public class DeckBuilderInteractor implements DeckBuilderInputBoundary {
    private DeckBuilderOutputBoundary presenter;

    public DeckBuilderInteractor(DeckBuilderOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void execute(DeckBuilderInputData inputData) {

    } 
}
