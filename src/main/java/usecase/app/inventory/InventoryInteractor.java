package usecase.app.inventory;

public class InventoryInteractor implements InventoryInputBoundary {
    private InventoryDataAccessInterface dataAccessObject;
    private InventoryOutputBoundary presenter;

    public InventoryInteractor(InventoryDataAccessInterface dataAccessObject, InventoryOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    public void execute(InventoryInputData inputData) {

    } 
}
