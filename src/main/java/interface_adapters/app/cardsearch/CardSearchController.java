package interface_adapters.app.cardsearch;

import usecase.app.cardsearch.CardSearchInputBoundary;

public class CardSearchController {
    public CardSearchInputBoundary inputBoundary;

    public CardSearchController(CardSearchInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }
}
