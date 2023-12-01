package usecase.app.deckbrowser;

import java.awt.Image;
import java.util.ArrayList;

import entity.Deck;

public interface DeckBrowserOutputBoundary {
    public void presentDecks(ArrayList<Deck> decks);
    public void setDefaultDeckImage(Image image);
}
