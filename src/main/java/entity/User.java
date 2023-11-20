package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public String password;
    private final ArrayList<Deck> decks = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public List<Deck> getDecks() {
        return decks;
    }
}
