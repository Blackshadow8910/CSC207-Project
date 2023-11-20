package interface_adapters.app.cardsearch;

import usecase.app.cardsearch.CardSearchInputBoundary;
import usecase.app.cardsearch.CardSearchInputData;

public class CardSearchController {
    public CardSearchInputBoundary inputBoundary;

    public CardSearchController(CardSearchInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void performSearch(String query) {
        System.out.println("Searching for: %s".formatted(query));
        inputBoundary.performSearch(new CardSearchInputData(query));
    }

    public void performSearch(CardSearchInputData data) {
        inputBoundary.performSearch(data);
    }
}
