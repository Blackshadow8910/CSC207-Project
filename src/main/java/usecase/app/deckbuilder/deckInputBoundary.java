package usecase.app.deckbuilder.inventory;

import data_access.pokemon.PokemonGuruCardSearchFilter;
import entity.Card;
import entity.User;

public interface deckInputBoundary {
    public void displayInventory(User user, PokemonGuruCardSearchFilter filter);
    public void removeCard(User user, Card card); 
}
