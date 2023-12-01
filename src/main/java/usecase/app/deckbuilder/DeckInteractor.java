package usecase.app.deckbuilder.inventory;

import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.ArrayListCardDataAccessObject;
import data_access.pokemon.PokemonGuruCardSearchFilter;
import entity.Card;
import entity.User;
import usecase.app.cardsearch.CardDisplayData;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class InventoryInteractor implements deckInputBoundary {
    private deckDataAccessInterface dataAccessObject;
    private ImageDataAccessInterface imageDataAccessInterface;
    private InventoryOutputBoundary presenter;

    public InventoryInteractor(InventoryOutputBoundary presenter, deckDataAccessInterface dataAccessObject, ImageDataAccessInterface imageDataAccessInterface) {
        this.dataAccessObject = dataAccessObject;
        this.imageDataAccessInterface = imageDataAccessInterface;
        this.presenter = presenter;
    }

    public void displayInventory(User user, PokemonGuruCardSearchFilter filter) {
        ArrayListCardDataAccessObject inventoryDAO = new ArrayListCardDataAccessObject(new ArrayList<>(user.getOwnedCards()));
        ArrayList<Card> cards = inventoryDAO.searchCards(filter);
        ArrayList<CardDisplayData> results = new ArrayList<>();

        for (Card card : cards) {
            BufferedImage image = imageDataAccessInterface.getImage(card.imageURL);

            results.add(new CardDisplayData(card, image));
        }

        presenter.presentInventory(results);
    }

    @Override
    public void removeCard(User user, Card card) {
        user.removeOwnedCard(card);
    } 
}
