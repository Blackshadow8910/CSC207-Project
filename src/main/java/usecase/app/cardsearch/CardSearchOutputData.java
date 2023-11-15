package usecase.app.cardsearch;

import java.awt.Image;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import entity.Card;

public class CardSearchOutputData {
    private final ArrayList<CardDisplayData> results = new ArrayList<>();

    public CardSearchOutputData() {
        
    }

    public void add(Card card, Image image) {
        results.add(new CardDisplayData(card, image));
    }

    public ArrayList<CardDisplayData> getResults() {
        return results;
    }
}
