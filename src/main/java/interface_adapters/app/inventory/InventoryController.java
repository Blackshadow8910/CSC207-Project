package interface_adapters.app.inventory;

import java.util.ArrayList;

import data_access.pokemon.ArrayListCardDataAccessObject;
import data_access.pokemon.PokemonGuruCardSearchFilter;
import entity.User;
import usecase.app.inventory.InventoryInputBoundary;

public class InventoryController {
    public InventoryInputBoundary inputBoundary;

    public InventoryController(InventoryInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void displayInventory(User user, PokemonGuruCardSearchFilter filter) {
        inputBoundary.displayInventory(new ArrayListCardDataAccessObject(new ArrayList<>(user.getOwnedCards())), filter);
    }
}
