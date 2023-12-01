package usecase.app.deckbrowser;

import java.util.ArrayList;

import entity.Deck;

public interface DeckBrowserDataAccessInterface {
    public ArrayList<Deck> getDecks(DeckSearchFilter filter);
}
