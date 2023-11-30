package interface_adapters.app.deckbuilder;

import usecase.app.deckbuilder.DeckBuilderOutputBoundary;
import usecase.app.deckbuilder.DeckBuilderOutputData;

public class DeckBuilderPresenter implements DeckBuilderOutputBoundary {
    private DeckBuilderViewModel viewModel;
    
    public DeckBuilderPresenter(DeckBuilderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(DeckBuilderOutputData outputData) {

    }
}
