package interface_adapters.app.deckbuilder;

import java.util.ArrayList;

import data_access.pokemon.ArrayListCardDataAccessObject;
import data_access.pokemon.PokemonGuruCardSearchFilter;
import entity.Card;
import entity.User;
import usecase.app.inventory.InventoryInputBoundary;

public class DeckController {
    public DeckInputBoundary inputBoundary;

    public DeckController(DeckInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void displayInventory(User user, PokemonGuruCardSearchFilter filter) {
        inputBoundary.displayInventory(user, filter);
    }

    public void removeCard(User user, Card card) {
        inputBoundary.removeCard(user, card);
    }
}
