package usecase.app.deckbuilder;

import entity.Deck;

public interface DeckBuilderInputBoundary {
    void search(DeckBuilderInputData inputData);
    void saveDeck(Deck deck);
}
