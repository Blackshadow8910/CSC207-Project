package data_access.pokemon;

import data_access.image.ImageCacheAccessInterface;
import entity.Card;
import entity.PokemonCard;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
            if (card.name.contains(filter.name)) {
                results.add(card);
            }
        }
        return results;
    }

    @Override
    public ArrayList<Card> searchCards(String query) {
        return searchCards(new PokemonGuruCardSearchFilter(query));
    }

    
    public void removeCard(Card card) {
        cards.remove(card);
    }
}
