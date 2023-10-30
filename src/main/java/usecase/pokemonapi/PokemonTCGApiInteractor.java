package usecase.pokemonapi;

import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.PokemonGuruDataAccessObject;

public class PokemonTCGApiInteractor {
    public PokemonCardDataAccessInterface apiAccessObject;

    

    public PokemonTCGApiInteractor(PokemonCardDataAccessInterface dataAccessInterface) {
        apiAccessObject = dataAccessInterface;
    }
}
