package interface_adapters.app.inventory;

import usecase.app.cardsearch.CardDisplayData;
import usecase.app.inventory.InventoryOutputBoundary;

import java.util.ArrayList;

public class InventoryPresenter implements InventoryOutputBoundary {
    private final InventoryViewModel viewModel;
    
    public InventoryPresenter(InventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentInventory(ArrayList<CardDisplayData> results) {
        viewModel.setCurrentResults(results);
    }
}
