package interface_adapters.app.deckbrowser;

import java.awt.Image;
import java.util.ArrayList;

import entity.Deck;
import interface_adapters.ViewModel;

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
