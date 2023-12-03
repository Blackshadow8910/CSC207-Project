package app.usecase_factories;



import data_access.image.ImageDataAccessInterface;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.inventory.InventoryViewModel;
import interface_adapters.app.trade.TradeController;
import interface_adapters.app.trade.TradePresenter;
import interface_adapters.app.trade.TradeViewModel;
import usecase.app.trade.TradeDataAccessInterface;
import usecase.app.trade.TradeInputBoundary;
import usecase.app.trade.TradeInteractor;
import usecase.app.trade.TradeOutputBoundary;
import view.app.TradeView;

public class TradeUseCaseFactory {

    /** Prevent instantiation. */
    private TradeUseCaseFactory() {}

    public static TradeView create(TradeViewModel tradeViewModel,
                                                  TradeDataAccessInterface dataAccessObject,
                                                  ImageDataAccessInterface imageAccessObject, 
                                                  AppViewModel appViewModel,
                                                  InventoryViewModel inventoryViewModel) {

        TradeOutputBoundary tradeOutputBoundary = new TradePresenter(tradeViewModel);
        TradeInputBoundary tradeInputBoundary = new TradeInteractor(tradeOutputBoundary, dataAccessObject);
        TradeController tradeController = new TradeController(tradeInputBoundary);

        return new TradeView(tradeViewModel, tradeController, appViewModel, inventoryViewModel);

    }
}
