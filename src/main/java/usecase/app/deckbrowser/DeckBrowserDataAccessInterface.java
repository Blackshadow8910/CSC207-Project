package usecase.app.deckbrowser;

import entity.Deck;

import java.util.ArrayList;

public interface DeckBrowserDataAccessInterface {
    ArrayList<Deck> getDecks(DeckSearchFilter filter);
}
