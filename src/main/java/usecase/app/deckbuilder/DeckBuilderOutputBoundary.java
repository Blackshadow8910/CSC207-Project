package usecase.app.deckbuilder;

import java.util.ArrayList;

import usecase.app.cardsearch.CardDisplayData;

public interface DeckBuilderOutputBoundary {
    public void presentSearchResults(ArrayList<CardDisplayData> results);
}
