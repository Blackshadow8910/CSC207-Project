package usecase.app.inventory;

import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.PokemonGuruCardSearchFilter;

public interface InventoryInputBoundary {
    public void displayInventory(PokemonCardDataAccessInterface inventoryDAO, PokemonGuruCardSearchFilter filter);
}
