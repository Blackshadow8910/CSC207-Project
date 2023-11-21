package usecase.app.inventory;

import data_access.image.ImageDataAccessInterface;

public class InventoryInteractor implements InventoryInputBoundary {
    private InventoryDataAccessInterface dataAccessObject;
    private ImageDataAccessInterface imageDataAccessInterface;
    private InventoryOutputBoundary presenter;

    public InventoryInteractor(InventoryOutputBoundary presenter, InventoryDataAccessInterface dataAccessObject, ImageDataAccessInterface imageDataAccessInterface) {
        this.dataAccessObject = dataAccessObject;
        this.imageDataAccessInterface = imageDataAccessInterface;
        this.presenter = presenter;
    }

    public void execute(InventoryInputData inputData) {

    } 
}
