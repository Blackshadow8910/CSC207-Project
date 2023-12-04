package usecase.app.deckbuilder;

import entity.Deck;
import entity.PokemonGuruCardSearchFilter;

public class DeckBuilderInputData {
    public final PokemonGuruCardSearchFilter filter;
    public final int target;
    public final Deck searchDeck;
    public static final int ADD = 0;
    public static final int REMOVE = 1; 

    public DeckBuilderInputData(PokemonGuruCardSearchFilter filter, int target, Deck searchDeck) {
        this.filter = filter;
        this.target = target;
        this.searchDeck = searchDeck;
    }

    public DeckBuilderInputData(PokemonGuruCardSearchFilter filter, int target) {
        this(filter, target, null);
    }
}
