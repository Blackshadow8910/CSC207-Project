package usecase.app.inventory;

import entity.Card;
import entity.PokemonGuruCardSearchFilter;
import entity.User;

public interface InventoryInputBoundary {
    void displayInventory(User user, PokemonGuruCardSearchFilter filter);
    void removeCard(User user, Card card);
}
