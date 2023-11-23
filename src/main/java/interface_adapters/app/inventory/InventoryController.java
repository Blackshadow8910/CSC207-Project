package interface_adapters.app.inventory;

import usecase.app.inventory.InventoryInputBoundary;

public class InventoryController {
    public InventoryInputBoundary inputBoundary;

    public InventoryController(InventoryInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void displayInventory(User user) {
        inputBoundary.displayInventory();
    }
}
