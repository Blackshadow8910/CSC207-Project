package usecase.app.deckbuilder.inventory;

import usecase.app.cardsearch.CardDisplayData;

import java.util.ArrayList;

public interface InventoryOutputBoundary {
    public void presentInventory(ArrayList<CardDisplayData> results);
}
