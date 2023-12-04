package interface_adapters.app.cardsearch;

import entity.Card;
import interface_adapters.ViewModel;
import usecase.app.cardsearch.CardDisplayData;

import java.util.ArrayList;

public class CardSearchViewModel extends ViewModel{

    private ArrayList<CardDisplayData> displayedResults = new ArrayList<>();
    private Card currentCard = null;

    public CardSearchViewModel() {
        super("cardsearch");
    }

    public void setDisplayedCards(ArrayList<CardDisplayData> results) {
        Object oldValue = displayedResults;
        displayedResults = results;

        firePropertyChanged("displayedResults", oldValue, results);
    }

    public void setCurrentCard(Card card) {
        Object oldValue = currentCard;
        currentCard = card;

        firePropertyChanged("currentCard", oldValue, currentCard);
    }

    public Card getCurrentCard() {
        return currentCard;
    }
}
