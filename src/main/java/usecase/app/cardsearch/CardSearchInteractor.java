package usecase.app.cardsearch;

import java.awt.Image;
import java.util.ArrayList;

import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Card;

public class CardSearchInteractor implements CardSearchInputBoundary {
    private CardSearchOutputBoundary presenter;
    private PokemonCardDataAccessInterface dataAccessObject;
    private ImageDataAccessInterface imageAccessObject;

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
