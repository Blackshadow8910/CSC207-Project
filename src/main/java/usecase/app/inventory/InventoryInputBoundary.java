package usecase.app.inventory;

import data_access.pokemon.ArrayListCardDataAccessObject;
import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.PokemonGuruCardSearchFilter;
import entity.Card;
import entity.User;

public interface InventoryInputBoundary {
    public void displayInventory(User user, PokemonGuruCardSearchFilter filter);
    public void removeCard(User user, Card card); 
}
