package interface_adapters.app.deckbrowser;

import entity.Deck;
import interface_adapters.ViewModel;

import java.awt.*;
import java.util.ArrayList;

public class DeckBrowserViewModel extends ViewModel{
    private ArrayList<Deck> currentDecks = new ArrayList<>();
    public Image defaultDeckImage;

    public DeckBrowserViewModel() {
        super("deckbrowser");
    }

    public ArrayList<Deck> getDecks() {
        return currentDecks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        Object oldValue = currentDecks;
        currentDecks = decks;

        firePropertyChanged("currentDecks", oldValue, decks);
    }
}
