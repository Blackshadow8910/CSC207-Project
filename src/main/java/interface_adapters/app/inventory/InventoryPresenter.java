package interface_adapters.app.inventory;

import usecase.app.inventory.InventoryOutputBoundary;
import usecase.app.inventory.InventoryOutputData;

public class InventoryPresenter implements InventoryOutputBoundary {
    private InventoryViewModel viewModel;
    
    public InventoryPresenter(InventoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void present(InventoryOutputData outputData) {

    }
}
