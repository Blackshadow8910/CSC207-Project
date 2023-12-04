package usecase.app.cardsearch;

import entity.Card;

import java.awt.*;
import java.util.ArrayList;

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
