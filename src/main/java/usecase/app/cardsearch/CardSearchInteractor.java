package usecase.app.cardsearch;

import data_access.pokemon.PokemonCardDataAccessInterface;

public class CardSearchInteractor implements CardSearchInputBoundary {
    private CardSearchOutputBoundary presenter;
    private PokemonCardDataAccessInterface dataAccessObject;

    public CardSearchInteractor(CardSearchOutputBoundary presenter, PokemonCardDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    public void execute(CardSearchInputData inputData) {

    } 
}
