package interface_adapters.app.deckbuilder;

import usecase.app.cardsearch.CardDisplayData;
import usecase.app.deckbuilder.DeckBuilderOutputBoundary;

import java.util.ArrayList;

public class DeckBuilderPresenter implements DeckBuilderOutputBoundary {
    private final DeckBuilderViewModel viewModel;
    
    public DeckBuilderPresenter(DeckBuilderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSearchResults(ArrayList<CardDisplayData> results) {
        viewModel.setDisplayResults(results);
    }
}
