package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class User {
    public String username;
    public String password;
    private final ArrayList<Deck> decks = new ArrayList<>();
    private final ArrayList<Card> ownedCards = new ArrayList<>();

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

    public List<Card> getOwnedCards() {
        return ownedCards;
    }
    
    public void addOwnedCard(Card card) {
        ownedCards.add(card);
    }

    public void addOwnedCards(Collection<Card> cards) {
        for (Card card : cards) {
            addOwnedCard(card);
        }
    }

    public void removeOwnedCard(String id) {
        int n = 0;
        Card card = null;
        for (Card ownedCard : ownedCards) {
            if (ownedCard.id.equals(id)) {
                card = ownedCard;
                break;
            }
            n++;
        }
        if (card != null) {
            ownedCards.remove(card);
        }
    }

    public void removeOwnedCard(Card card) {
        ownedCards.remove(card);
    }
}
