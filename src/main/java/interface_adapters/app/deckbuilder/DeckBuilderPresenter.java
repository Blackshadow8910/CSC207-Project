package interface_adapters.app.deckbuilder;

import java.util.ArrayList;

import usecase.app.cardsearch.CardDisplayData;
import usecase.app.deckbuilder.DeckBuilderOutputBoundary;
import usecase.app.deckbuilder.DeckBuilderOutputData;

public class DeckBuilderPresenter implements DeckBuilderOutputBoundary {
    private DeckBuilderViewModel viewModel;
    
    public DeckBuilderPresenter(DeckBuilderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSearchResults(ArrayList<CardDisplayData> results) {
        viewModel.setDisplayResults(results);
    }
}
