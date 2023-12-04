package usecase.app.deckbrowser;

import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Deck;

import java.util.ArrayList;

public class DeckBrowserInteractor implements DeckBrowserInputBoundary {
    private final DeckBrowserOutputBoundary presenter;
    private final DeckBrowserDataAccessInterface dao;
    private final PokemonCardDataAccessInterface pokemonDAO;
    private final ImageDataAccessInterface imageDAO;

    public DeckBrowserInteractor(DeckBrowserOutputBoundary presenter, DeckBrowserDataAccessInterface dao, PokemonCardDataAccessInterface pokemonDAO, ImageDataAccessInterface imageDAO) {
        this.presenter = presenter;
        this.dao = dao;
        this.pokemonDAO = pokemonDAO;
        this.imageDAO = imageDAO;
    }

    public void displayDecks(DeckSearchFilter filter) {
        ArrayList<Deck> decks = dao.getDecks(filter);

        presenter.setDefaultDeckImage(imageDAO.getImage("img/deck-icon.png"));
        presenter.presentDecks(decks);
    } 

    public void displayDecks() {
        displayDecks(new DeckSearchFilter());
    }
}
