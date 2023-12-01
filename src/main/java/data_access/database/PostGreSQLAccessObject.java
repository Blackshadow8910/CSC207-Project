package data_access.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.spec.ECField;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Deck;
import entity.Message;
import entity.SellListing;
import entity.User;
import usecase.app.deckbrowser.DeckSearchFilter;

import org.json.JSONObject;


public class PostGreSQLAccessObject implements DatabaseAccessInterface {
    private final PokemonCardDataAccessInterface pokemonDAO;
    private static String dbURL;
    private static String dbUsername;
    private static String dbPassword;
    
    private Connection connection;

    public PostGreSQLAccessObject(PokemonCardDataAccessInterface pokemonDAO) {
        this.pokemonDAO = pokemonDAO;

        try {
            File f = new File("resources/postgresql-api-key.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            
            dbURL = reader.readLine();
            dbUsername = reader.readLine();
            dbPassword = reader.readLine();
            reader.close();

            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected ResultSet executeQuery(String query) {
        try {
            Statement s = connection.createStatement();

            return s.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUser(String username) {
        ResultSet results = executeQuery("SELECT * FROM users WHERE username='%s'".formatted(username));
        try {
            if (results.next()) {
                User user = new User(username, results.getString("password"));

                Object[] inv = (Object[]) results.getArray("inventory").getArray();
                for (Object cardId : inv) {
                    assert cardId instanceof String;
                    user.addOwnedCard(pokemonDAO.getCard((String) cardId));
                }
                
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    /**
     * Returns a user if the given credentials match a user.
     * 
     * Currently doesn't result in anything different from the other implementation of getUser, 
     * but it should give higher level access to the account done this way later.
     */
    public User getUser(String username, String password) {
        ResultSet results = executeQuery("SELECT * FROM users WHERE username='%s' AND password='%s'".formatted(username, password));
        try {
            if (results.next()) {
                User user = new User(username, results.getString("password"));

                Object[] inv = (Object[]) results.getArray("inventory").getArray();
                for (Object cardId : inv) {
                    assert cardId instanceof String;
                    user.addOwnedCard(pokemonDAO.getCard((String) cardId));
                }
                
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerUser(String username, String password) throws UserAlreadyExistsException {
        if (getUser(username) != null) {
            throw new UserAlreadyExistsException("User");
        }

        //User user = new User(username, password);

        try {
            executeQuery("INSERT INTO users (username, password) VALUES ('%s', '%s');".formatted(username, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Deck getDeck(String id) {
        ResultSet results = executeQuery("SELECT * FROM decks WHERE id='%s'".formatted(id));
        try {
            if (results.next()) {
                Deck deck = new Deck(results.getString("name"), id);
                for (String cardId : (String[]) results.getArray("cards").getArray()) {
                    deck.addCard(pokemonDAO.getCard(cardId));
                }

                return deck;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void uploadDeck(Deck deck) {
        JSONObject cardList = new JSONObject(deck.getCards());

        try {
            executeQuery("INSERT INTO decks (id, name, cards) VALUES ('%s', '%s', %s);".formatted(deck.id, deck.name, cardList.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SellListing getSellListing(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSellListing'");
    }

    @Override
    public void uploadSellListing(SellListing sellListing) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadSellListing'");
    }

    @Override
    public void closeSellListing(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeSellListing'");
    }

    @Override
    public void replyToSellListing(String sellListingId, Message message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replyToSellListing'");
    }

    @Override
    public ArrayList<SellListing> getSellListings() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSellListings'");
    }

    @Override
    public ArrayList<Deck> getDecks(DeckSearchFilter filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDecks'");
    }
}
