package app.usecase_factories;



import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import interface_adapters.app.cardsearch.CardSearchController;
import interface_adapters.app.cardsearch.CardSearchPresenter;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import interface_adapters.app.inventory.InventoryController;
import interface_adapters.app.inventory.InventoryPresenter;
import interface_adapters.app.inventory.InventoryViewModel;
import usecase.app.cardsearch.CardSearchInputBoundary;
import usecase.app.cardsearch.CardSearchInteractor;
import usecase.app.cardsearch.CardSearchOutputBoundary;
import usecase.app.inventory.InventoryDataAccessInterface;
import usecase.app.inventory.InventoryInputBoundary;
import usecase.app.inventory.InventoryInteractor;
import usecase.app.inventory.InventoryOutputBoundary;
import view.app.CardSearchView;
import view.app.InventoryView;

public class InventoryUseCaseFactory {

    /** Prevent instantiation. */
    private InventoryUseCaseFactory() {}

    public static InventoryView create(InventoryViewModel inventoryViewModel,
                                                  InventoryDataAccessInterface dataAccessObject,
                                                  ImageDataAccessInterface imageAccessObject) {

        InventoryOutputBoundary inventoryOutputBoundary = new InventoryPresenter(inventoryViewModel);
        InventoryInputBoundary inventoryInputBoundary = new InventoryInteractor(inventoryOutputBoundary, dataAccessObject, imageAccessObject);
        InventoryController inventoryController = new InventoryController(inventoryInputBoundary);

        return new InventoryView(inventoryViewModel, inventoryController);

    }
}
