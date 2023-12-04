package usecase.pokemonapi;

import data_access.pokemon.PokemonCardDataAccessInterface;

public class PokemonTCGApiInteractor {
    public PokemonCardDataAccessInterface apiAccessObject;

    

    public PokemonTCGApiInteractor(PokemonCardDataAccessInterface dataAccessInterface) {
        apiAccessObject = dataAccessInterface;
    }
}
