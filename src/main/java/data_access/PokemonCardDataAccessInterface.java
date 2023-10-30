package data_access;

import java.awt.Image;
import java.util.ArrayList;

import entity.Card;

public interface PokemonCardDataAccessInterface {
    public Card getCard(String id);
    public ArrayList<Card> searchCards(PokemonGuruCardSearchFilter filter);
    public ArrayList<Card> searchCards(String query);
}
