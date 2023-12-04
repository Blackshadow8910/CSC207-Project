package usecase.app.deckbuilder;

import usecase.app.cardsearch.CardDisplayData;

import java.util.ArrayList;

public interface DeckBuilderOutputBoundary {
    void presentSearchResults(ArrayList<CardDisplayData> results);
}
