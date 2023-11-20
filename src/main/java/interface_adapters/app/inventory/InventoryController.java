package interfaceInventoryadapters.app.inventory;

public class InventoryController {
    public InventoryInputBoundary inputBoundary;

    public InventoryController(InventoryInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }
}
