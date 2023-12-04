package data_access.pokemon;

import entity.Card;
import entity.PokemonGuruCardSearchFilter;

import java.util.ArrayList;

public interface PokemonCardDataAccessInterface {
    Card getCard(String id);
    ArrayList<Card> searchCards(PokemonGuruCardSearchFilter filter);
    ArrayList<Card> searchCards(String query);
}
