package interface_adapters.app.cardsearch;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Card;
import interface_adapters.ViewModel;
import usecase.app.cardsearch.CardSearchResult;

public class CardSearchViewModel extends ViewModel{

    private ArrayList<CardSearchResult> displayedResults = new ArrayList<>();

    public CardSearchViewModel() {
        super("cardsearch");
    }

    public void setDisplayedCards(ArrayList<CardSearchResult> results) {
        Object oldValue = displayedResults;
        displayedResults = results;

        firePropertyChanged("displayedResults", oldValue, results);
    }
}
