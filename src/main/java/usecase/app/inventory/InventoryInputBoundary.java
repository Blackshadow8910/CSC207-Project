package usecase.app.inventory;

import entity.PokemonGuruCardSearchFilter;
import entity.Card;
import entity.User;

public interface InventoryInputBoundary {
    public void displayInventory(User user, PokemonGuruCardSearchFilter filter);
    public void removeCard(User user, Card card); 
}
