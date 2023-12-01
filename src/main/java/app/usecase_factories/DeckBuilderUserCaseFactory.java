package app.usecase_factories;

import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.deckbrowser.DeckBrowserController;
import interface_adapters.app.deckbrowser.DeckBrowserPresenter;
import interface_adapters.app.deckbrowser.DeckBrowserViewModel;
import interface_adapters.app.deckbuilder.DeckBuilderController;
import interface_adapters.app.deckbuilder.DeckBuilderPresenter;
import interface_adapters.app.deckbuilder.DeckBuilderViewModel;
import usecase.app.deckbrowser.DeckBrowserDataAccessInterface;
import usecase.app.deckbrowser.DeckBrowserInputBoundary;
import usecase.app.deckbrowser.DeckBrowserInteractor;
import usecase.app.deckbrowser.DeckBrowserOutputBoundary;
import usecase.app.deckbuilder.DeckBuilderDataAccessInterface;
import usecase.app.deckbuilder.DeckBuilderInputBoundary;
import usecase.app.deckbuilder.DeckBuilderInteractor;
import usecase.app.deckbuilder.DeckBuilderOutputBoundary;
import view.app.DeckBrowserView;
import view.app.DeckBuilderView;

public class DeckBuilderUserCaseFactory {
    public static DeckBuilderView create(DeckBuilderViewModel viewModel, AppViewModel appViewModel, DeckBuilderDataAccessInterface dao, PokemonCardDataAccessInterface pokemonDAO, ImageDataAccessInterface imageDAO) {
        DeckBuilderOutputBoundary outputBoundary = new DeckBuilderPresenter(viewModel);
        DeckBuilderInputBoundary inputBoundary = new DeckBuilderInteractor(outputBoundary, dao, pokemonDAO, imageDAO);
        DeckBuilderController controller= new DeckBuilderController(inputBoundary);

        return new DeckBuilderView(viewModel, controller, appViewModel);
    }

    public static DeckBrowserView createBrowser(DeckBrowserViewModel viewModel, DeckBuilderViewModel deckBuilderViewModel, DeckBrowserDataAccessInterface dao, PokemonCardDataAccessInterface pokemonDAO, ImageDataAccessInterface imageDAO) {
        DeckBrowserOutputBoundary outputBoundary = new DeckBrowserPresenter(viewModel);
        DeckBrowserInputBoundary inputBoundary = new DeckBrowserInteractor(outputBoundary, dao, pokemonDAO, imageDAO);
        DeckBrowserController controller= new DeckBrowserController(inputBoundary);

        return new DeckBrowserView(viewModel, controller, deckBuilderViewModel);
    }
}
