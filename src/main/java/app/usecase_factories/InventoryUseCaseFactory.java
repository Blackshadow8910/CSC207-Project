package app.usecase_factories;



import data_access.image.ImageDataAccessInterface;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.inventory.InventoryController;
import interface_adapters.app.inventory.InventoryPresenter;
import interface_adapters.app.inventory.InventoryViewModel;
import interface_adapters.app.trade.TradeViewModel;
import usecase.app.inventory.InventoryDataAccessInterface;
import usecase.app.inventory.InventoryInputBoundary;
import usecase.app.inventory.InventoryInteractor;
import usecase.app.inventory.InventoryOutputBoundary;
import view.app.InventoryView;

public class InventoryUseCaseFactory {

    /** Prevent instantiation. */
    private InventoryUseCaseFactory() {}

    public static InventoryView create(InventoryViewModel inventoryViewModel,
                                       InventoryDataAccessInterface dataAccessObject,
                                       ImageDataAccessInterface imageAccessObject,
                                       AppViewModel appViewModel,
                                       TradeViewModel tradeViewModel) {

        InventoryOutputBoundary inventoryOutputBoundary = new InventoryPresenter(inventoryViewModel);
        InventoryInputBoundary inventoryInputBoundary = new InventoryInteractor(inventoryOutputBoundary, dataAccessObject, imageAccessObject);
        InventoryController inventoryController = new InventoryController(inventoryInputBoundary);

        return new InventoryView(inventoryViewModel, inventoryController, appViewModel, tradeViewModel);

    }
}
