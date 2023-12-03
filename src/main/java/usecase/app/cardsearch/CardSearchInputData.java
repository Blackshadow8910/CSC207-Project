package usecase.app.cardsearch;

import entity.PokemonGuruCardSearchFilter;

public class CardSearchInputData {
    public final PokemonGuruCardSearchFilter filter;
    public CardSearchInputData(PokemonGuruCardSearchFilter searchFilter) {
        filter = searchFilter;
    }

    public CardSearchInputData(String query) {
        filter = new PokemonGuruCardSearchFilter(query);
    }
}
