package usecase.app.inventory;

import usecase.app.cardsearch.CardDisplayData;

import java.util.ArrayList;

public interface InventoryOutputBoundary {
    void presentInventory(ArrayList<CardDisplayData> results);
}
