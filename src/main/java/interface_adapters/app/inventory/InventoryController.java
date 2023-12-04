package interface_adapters.app.inventory;

import entity.Card;
import entity.PokemonGuruCardSearchFilter;
import entity.User;
import usecase.app.inventory.InventoryInputBoundary;

public class InventoryController {
    public final InventoryInputBoundary inputBoundary;

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
