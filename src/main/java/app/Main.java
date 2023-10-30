package app;

import data_access.database.DatabaseAccessObject;
import entity.Card;

import usecase.PokemonTCGApiUseCase;

public class Main {
    public static void main(String[] args) {
        PokemonTCGApiUseCase pokemonTCGApiUseCase = new PokemonTCGApiUseCase();
        DatabaseAccessObject db = new DatabaseAccessObject();

        //GUIManager guiManager = new GUIManager();
        Card c = pokemonTCGApiUseCase.apiInteractor.searchCards("mega lopunny").get(0);
        System.out.println(c.name);
    }
}
