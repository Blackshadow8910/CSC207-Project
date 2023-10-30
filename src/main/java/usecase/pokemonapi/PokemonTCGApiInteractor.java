package usecase.pokemonapi;

import data_access.PokemonCardDataAccessInterface;
import data_access.PokemonGuruDataAccessObject;

public class PokemonTCGApiInteractor {
    public PokemonCardDataAccessInterface apiAccessObject;

    

    public PokemonTCGApiInteractor(PokemonCardDataAccessInterface dataAccessInterface) {
        apiAccessObject = dataAccessInterface;
    }
}
