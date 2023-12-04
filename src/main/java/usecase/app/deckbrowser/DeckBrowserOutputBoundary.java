package usecase.app.deckbrowser;

import entity.Deck;

import java.awt.*;
import java.util.ArrayList;

public interface DeckBrowserOutputBoundary {
    void presentDecks(ArrayList<Deck> decks);
    void setDefaultDeckImage(Image image);
}
