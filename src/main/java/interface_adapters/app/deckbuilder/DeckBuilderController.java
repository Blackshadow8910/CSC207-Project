package interface_adapters.app.deckbuilder;

import entity.Deck;
import usecase.app.deckbuilder.DeckBuilderInputBoundary;
import usecase.app.deckbuilder.DeckBuilderInputData;

public class DeckBuilderController {
    public DeckBuilderInputBoundary inputBoundary;

    public DeckBuilderController(DeckBuilderInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void search(DeckBuilderInputData data) {
        inputBoundary.search(data);
    }

    public void saveDeck(Deck deck) {
        inputBoundary.saveDeck(deck);
    }
}
