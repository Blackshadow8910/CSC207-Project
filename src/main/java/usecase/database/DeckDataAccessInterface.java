package usecase.database;

import entity.Deck;

public interface DeckDataAccessInterface {
    public Deck getDeck(String id);
    public void uploadDeck(Deck deck);
}
