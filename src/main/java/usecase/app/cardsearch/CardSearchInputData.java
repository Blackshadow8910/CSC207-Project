package usecase.app.cardsearch;

import data_access.pokemon.PokemonGuruCardSearchFilter;

public class CardSearchInputData {
    public final PokemonGuruCardSearchFilter filter;

    public CardSearchInputData(String query) {
        filter = new PokemonGuruCardSearchFilter(query);
    }
}
