package data_access.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Card;
import entity.Deck;
import entity.User;

public class TestDatabaseAccessObject implements DatabaseAccessInterface {
    public ArrayList<User> users = new ArrayList<>();
    private final HashMap<String, Deck> decks = new HashMap<>();

    public TestDatabaseAccessObject() {
        try {
            registerUser("bob", "123456");
            registerUser("steven", "654321");

            Deck testDeck = new Deck("TestDeck", "testdeck");
            testDeck.addCard(new Card("henry", "s", "s"));
            uploadDeck(testDeck);

        } catch (UserAlreadyExistsException e) {
        }
    }

    @Override
    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username)) {
                if (user.password.equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void registerUser(String username, String password) throws UserAlreadyExistsException {
        if (getUser(username) != null) {
            throw new UserAlreadyExistsException(username);
        }

        users.add(new User(username, password));
    }

    @Override
    public Deck getDeck(String id) {
        if (decks.containsKey(id)) {
            return decks.get(id);
        }
        return null;
    }

    @Override
    /**
     * Will upload a deck with the id as specified by deck; if there is already a deck with the same id, it will be overwritten
     */
    public void uploadDeck(Deck deck) {
        decks.put(deck.id, deck);
    }
}
