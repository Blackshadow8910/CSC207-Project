package data_access.pokemon;

import entity.Card;
import entity.PokemonCard;
import entity.PokemonGuruCardSearchFilter;

import java.util.ArrayList;

public class ArrayListCardDataAccessObject implements PokemonCardDataAccessInterface {

    protected final ArrayList<Card> cards;

    public ArrayListCardDataAccessObject(ArrayList<Card> src) {
        cards = (ArrayList<Card>) src.clone();
    }

    public ArrayListCardDataAccessObject() {
        this(new ArrayList<>());
    }
    
    @Override
    public Card getCard(String id) {
        for (Card card : cards) {
            if (card.id.equals(id)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Card> searchCards(PokemonGuruCardSearchFilter filter) {
        ArrayList<Card> results = new ArrayList<>();
        for (Card card : cards) {
            if (matchesFilter(card, filter)) {
                results.add(card);
            }
        }
        return results;
    }

    private boolean matchesFilter(Card card, PokemonGuruCardSearchFilter filter) {
        if (!(card instanceof PokemonCard pokemonCard)) {
            return false;
        }

        if (filter.name != "*" && !pokemonCard.name.toLowerCase().contains(filter.name.toLowerCase())) {
            return false;
        }

        if (filter.minPrice != 0 || filter.maxPrice != Double.MAX_VALUE) {
            if (filter.maxPrice < pokemonCard.marketPrice || filter.minPrice > pokemonCard.marketPrice) {
                return false;
            }
        }

        if (!filter.setID.isEmpty() && !filter.setID.equalsIgnoreCase(pokemonCard.setID)) {
            return false;
        }

        if (!filter.types.isEmpty()) {
            boolean match = false;
            for (String type : pokemonCard.types) {
                if (filter.types.contains(type)) {
                    match = true;
                    break;
                }
            }
            if (!match)
                return false;
        }

        if (!filter.subtypes.isEmpty()) {
            boolean match = false;
            for (String type : pokemonCard.subtypes) {
                if (filter.subtypes.contains(type)) {
                    match = true;
                    break;
                }
            }
            return match;
        }

        return true;
    }

    @Override
    public ArrayList<Card> searchCards(String query) {
        return searchCards(new PokemonGuruCardSearchFilter(query));
    }

    
    public void removeCard(Card card) {
        cards.remove(card);
    }
}
