package usecase;

import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.PokemonGuruDataAccessObject;

public class PokemonTCGApiUseCase {
    // TODO: Make this private, its public for now to test
    public PokemonCardDataAccessInterface apiAccessObject;

    public PokemonTCGApiUseCase(PokemonCardDataAccessInterface dataAccessObject) {
        apiAccessObject = dataAccessObject;
    }
}
