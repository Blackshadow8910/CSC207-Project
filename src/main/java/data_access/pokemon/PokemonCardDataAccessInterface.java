package data_access.pokemon;

import java.util.ArrayList;

import entity.Card;
import entity.PokemonGuruCardSearchFilter;

public interface PokemonCardDataAccessInterface {
    public Card getCard(String id);
    public ArrayList<Card> searchCards(PokemonGuruCardSearchFilter filter);
    public ArrayList<Card> searchCards(String query);
}
