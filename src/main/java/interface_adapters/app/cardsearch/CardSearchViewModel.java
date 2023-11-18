package interface_adapters.app.cardsearch;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Card;
import interface_adapters.ViewModel;
import usecase.app.cardsearch.CardDisplayData;

public class CardSearchViewModel extends ViewModel{

    private ArrayList<CardDisplayData> displayedResults = new ArrayList<>();

    public CardSearchViewModel() {
        super("cardsearch");
    }

    public void setDisplayedCards(ArrayList<CardDisplayData> results) {
        Object oldValue = displayedResults;
        displayedResults = results;

        firePropertyChanged("displayedResults", oldValue, results);
    }
}
