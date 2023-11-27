package interface_adapters.app.inventory;

import java.util.ArrayList;

import usecase.app.cardsearch.CardDisplayData;
import usecase.app.inventory.InventoryOutputBoundary;
import usecase.app.inventory.InventoryOutputData;

public class InventoryPresenter implements InventoryOutputBoundary {
    private InventoryViewModel viewModel;
    
    public InventoryPresenter(InventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentInventory(ArrayList<CardDisplayData> results) {
        viewModel.setCurrentResults(results);
    }
}
