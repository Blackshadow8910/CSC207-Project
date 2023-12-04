package usecase.app.cardsearch;

import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Card;

import java.awt.*;
import java.util.ArrayList;

public class CardSearchInteractor implements CardSearchInputBoundary {
    private final CardSearchOutputBoundary presenter;
    private final PokemonCardDataAccessInterface dataAccessObject;
    private final ImageDataAccessInterface imageAccessObject;

    public CardSearchInteractor(CardSearchOutputBoundary presenter, PokemonCardDataAccessInterface dataAccessObject, ImageDataAccessInterface imageAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
        this.imageAccessObject = imageAccessObject;
    }

    public void performSearch(CardSearchInputData inputData) {
        CardSearchOutputData outputData = new CardSearchOutputData();
        ArrayList<Card> results = dataAccessObject.searchCards(inputData.filter);

        for (Card card : results) {
            Image image = imageAccessObject.getImage(card.imageURL);
            outputData.add(card, image);
        }

        presenter.present(outputData);
    } 
}
