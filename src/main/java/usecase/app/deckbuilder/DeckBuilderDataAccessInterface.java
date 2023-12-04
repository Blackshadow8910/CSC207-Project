package usecase.app.deckbuilder;

import entity.Deck;

public interface DeckBuilderDataAccessInterface {
    Deck getDeck(String id);
    void uploadDeck(Deck deck);
}
