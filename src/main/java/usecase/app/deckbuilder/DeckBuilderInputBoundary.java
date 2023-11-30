package usecase.app.deckbuilder;

import entity.Deck;

public interface DeckBuilderInputBoundary {
    public void search(DeckBuilderInputData inputData);
    public void saveDeck(Deck deck);
}
