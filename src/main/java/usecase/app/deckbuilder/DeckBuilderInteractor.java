package usecase.app.deckbuilder;

import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.ArrayListCardDataAccessObject;
import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Card;
import entity.Deck;
import entity.PokemonGuruCardSearchFilter;
import usecase.app.cardsearch.CardDisplayData;

import java.awt.*;
import java.util.ArrayList;

public class DeckBuilderInteractor implements DeckBuilderInputBoundary {
    private final DeckBuilderOutputBoundary presenter;
    private final DeckBuilderDataAccessInterface dao;
    private final PokemonCardDataAccessInterface pokemonDAO;
    private final ImageDataAccessInterface imageDAO;

    public DeckBuilderInteractor(DeckBuilderOutputBoundary presenter, DeckBuilderDataAccessInterface dao, PokemonCardDataAccessInterface pokemonDAO, ImageDataAccessInterface imageDAO) {
        this.presenter = presenter;
        this.dao = dao;
        this.pokemonDAO = pokemonDAO;
        this.imageDAO = imageDAO;
    }

    @Override
    public void search(DeckBuilderInputData inputData) {
        if (inputData.target == DeckBuilderInputData.ADD) {
            searchForAdd(inputData.filter);
        } else {
            searchForRemove(inputData.filter, inputData.searchDeck);
        }
    } 

    private void searchForAdd(PokemonGuruCardSearchFilter filter) {
        ArrayList<Card> cards = pokemonDAO.searchCards(filter);
        ArrayList<CardDisplayData> results = new ArrayList<>();

        for (Card card : cards) {
            Image image = imageDAO.getImage(card.imageURL);

            results.add(new CardDisplayData(card, image));
        }
        
        presenter.presentSearchResults(results);
    }

    private void searchForRemove(PokemonGuruCardSearchFilter filter, Deck searchDeck) {
        ArrayListCardDataAccessObject dao = new ArrayListCardDataAccessObject(searchDeck.getCards());
        ArrayList<Card> cards = dao.searchCards(filter);
        ArrayList<CardDisplayData> results = new ArrayList<>();

        for (Card card : cards) {
            Image image = imageDAO.getImage(card.imageURL);

            results.add(new CardDisplayData(card, image));
        }
        
        presenter.presentSearchResults(results);
    }

    @Override
    public void saveDeck(Deck deck) {
        dao.uploadDeck(deck);
    }
}
