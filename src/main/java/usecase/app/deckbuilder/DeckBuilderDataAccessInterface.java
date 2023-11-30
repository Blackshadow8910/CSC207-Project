package usecase.app.deckbuilder;

import entity.Deck;

public interface DeckBuilderDataAccessInterface {
    public Deck getDeck(String id);
    public void uploadDeck(Deck deck);
}
