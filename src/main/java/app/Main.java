package app;

import usecase.PokemonTCGApiUseCase;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            PokemonTCGApiUseCase pokemonTCGApiUseCase = new PokemonTCGApiUseCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GUIManager guiManager = new GUIManager();

    }
}
