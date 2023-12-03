package data_access.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Card;
import entity.Conversation;
import entity.Deck;
import entity.Message;
import entity.SellListing;
import entity.User;
import usecase.app.deckbrowser.DeckSearchFilter;

public class TestDatabaseAccessObject implements DatabaseAccessInterface {
    private final PokemonCardDataAccessInterface pokemonDAO;

    public ArrayList<User> users = new ArrayList<>();
    private final HashMap<String, Deck> decks = new HashMap<>();
    private final HashMap<String, SellListing> sellListings = new HashMap<>();

    public TestDatabaseAccessObject(PokemonCardDataAccessInterface pokemonDAO) {
        this.pokemonDAO = pokemonDAO;

        try {
            registerUser("bob", "123456");
            registerUser("steven", "654321");

            Deck testDeck = new Deck("TestDeck", "TestDeck");
            if (pokemonDAO.getCard("g1-1")!= null) {
                testDeck.addCard(pokemonDAO.getCard("g1-1"));
                getUser("bob").addOwnedCard(pokemonDAO.getCard("g1-1"));
            }
            uploadDeck(testDeck);

            getUser("bob").addOwnedCards(pokemonDAO.searchCards("Horse"));


            if (pokemonDAO.getCard("g1-1")!= null) {
                sellListings.put("sell1", new SellListing("sell1", pokemonDAO.getCard("g1-1"), getUser("steven"), 12.99));
                sellListings.put("sell2", new SellListing("sell2", pokemonDAO.getCard("g1-1"), getUser("steven"), 13.99));
                sellListings.put("sell3", new SellListing("sell3", pokemonDAO.getCard("g1-1"), getUser("steven"), 14.99));
                sellListings.put("sell4", new SellListing("sell4", pokemonDAO.getCard("g1-1"), getUser("steven"), 15.99));
            }
        } catch (UserAlreadyExistsException e) {
        }
    }

    @Override
    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.username.equalsIgnoreCase(username)) {
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
            if (user.username.equalsIgnoreCase(username)) {
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
    public ArrayList<Deck> getDecks(DeckSearchFilter filter) {
        return new ArrayList<>(decks.values());
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

    @Override
    public SellListing getSellListing(String id) {
        if (sellListings.containsKey(id)) {
            return sellListings.get(id);
        }
        return null;
    }

    @Override
    public void uploadSellListing(SellListing sellListing) {
        sellListings.put(sellListing.getId(), sellListing);
    }

    @Override
    public void closeSellListing(SellListing listing, User buyer, Card offer) {
        if (sellListings.containsKey(listing.id)) {
            if (!listing.seller.getOwnedCards().contains(listing.getCard()))
                throw new RuntimeException("Card not in seller inventory");

            if (offer == null || buyer.getOwnedCards().contains(offer)) {
                sellListings.remove(listing.id);
                if (offer != null) {
                    moveCard(offer, buyer, listing.seller);
                    moveCard(listing.getCard(), listing.seller, buyer);
                }
            } else {
                throw new RuntimeException("Card not in inventory");
            }
        } else {
            throw new RuntimeException("Sell listing nonexistent");
        }
    }

    private void moveCard(Card card, User from, User to) {
        if (from.getOwnedCards().contains(card)) {
            from.removeOwnedCard(card);
            to.addOwnedCard(card);
        }
    }

    @Override
    public void replyToConversation(Conversation conversation, Message message) {
        //SellListing sellListing =  getSellListing(sellListingId);

//        if (sellListing == null) {
//            throw new RuntimeException("No such sell listing.");
//        }
//
//        Conversation c = sellListing.openConversation(getUser(message.getSender()));
        conversation.sendMessage(message);
    }

    @Override
    public ArrayList<SellListing> getSellListings() {
        return new ArrayList<>(sellListings.values());
    }
}
