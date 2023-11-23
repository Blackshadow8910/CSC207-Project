package usecase.app.inventory;

import data_access.pokemon.PokemonCardDataAccessInterface;

public interface InventoryInputBoundary {
    public void displayInventory(PokemonCardDataAccessInterface inventoryDAO);
}
