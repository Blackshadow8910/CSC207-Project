package app.usecase_factories;

import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import interface_adapters.app.deckbuilder.DeckBuilderController;
import interface_adapters.app.deckbuilder.DeckBuilderPresenter;
import interface_adapters.app.deckbuilder.DeckBuilderViewModel;
import usecase.app.deckbuilder.DeckBuilderDataAccessInterface;
import usecase.app.deckbuilder.DeckBuilderInputBoundary;
import usecase.app.deckbuilder.DeckBuilderInteractor;
import usecase.app.deckbuilder.DeckBuilderOutputBoundary;
import view.app.DeckBuilderView;

public class DeckBuilderUserCaseFactory {
    public static DeckBuilderView create(DeckBuilderViewModel viewModel, DeckBuilderDataAccessInterface dao, PokemonCardDataAccessInterface pokemonDAO, ImageDataAccessInterface imageDAO) {
        DeckBuilderOutputBoundary outputBoundary = new DeckBuilderPresenter(viewModel);
        DeckBuilderInputBoundary inputBoundary = new DeckBuilderInteractor(outputBoundary, dao, pokemonDAO, imageDAO);
        DeckBuilderController controller= new DeckBuilderController(inputBoundary);

        return new DeckBuilderView(viewModel, controller);
    }
}
