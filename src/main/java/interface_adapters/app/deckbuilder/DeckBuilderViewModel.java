package interface_adapters.app.deckbuilder;

import entity.Card;
import entity.Deck;
import interface_adapters.ViewModel;
import usecase.app.cardsearch.CardDisplayData;

import java.util.ArrayList;
import java.util.UUID;

public class DeckBuilderViewModel extends ViewModel{
    private String currentSubCardView = "";
    private Deck deck = new Deck("New deck", UUID.randomUUID().toString());

    public DeckBuilderViewModel() {
        super("deckbuilder");
    }

    public String getSubCardView() {
        return currentSubCardView;
    }

    public void setSubCardView(String value) {
        assert value != null;
        
        Object oldValue = currentSubCardView;
        currentSubCardView = value;

        firePropertyChanged("currentSubCardView", oldValue, value);
    }

    public void setDisplayResults(ArrayList<CardDisplayData> results) {
        firePropertyChanged("results", null, results);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck value) {
        Object oldValue = deck;
        if (deck == value) {
            oldValue = null;
        }
        deck = value;

        firePropertyChanged("deck", oldValue, value);
    }
    
    public void addToDeck(Card card) {
        deck.addCard(card);

        setDeck(deck);
    }

    public void removeFromDeck(Card card) {
        deck.removeCard(card.id);

        setDeck(deck);
    }
}
