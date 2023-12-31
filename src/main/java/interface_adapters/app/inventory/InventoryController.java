package interface_adapters.app.inventory;

import entity.PokemonGuruCardSearchFilter;
import entity.Card;
import entity.User;
import usecase.app.inventory.InventoryInputBoundary;

public class InventoryController {
    public InventoryInputBoundary inputBoundary;

    public InventoryController(InventoryInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void displayInventory(User user, PokemonGuruCardSearchFilter filter) {
        inputBoundary.displayInventory(user, filter);
    }

    public void removeCard(User user, Card card) {
        inputBoundary.removeCard(user, card);
    }
}
