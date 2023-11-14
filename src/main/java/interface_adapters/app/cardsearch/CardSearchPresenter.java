package interface_adapters.app.cardsearch;

import usecase.app.cardsearch.CardSearchOutputBoundary;
import usecase.app.cardsearch.CardSearchOutputData;

public class CardSearchPresenter implements CardSearchOutputBoundary {
    private CardSearchViewModel viewModel;
    
    public CardSearchPresenter(CardSearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(CardSearchOutputData outputData) {

    }
}
