package entity;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;

public class Deck {
    public ArrayList<Card> deck;
    public final String name;
    public final String id;
    public Deck(String name, String id){
        this.name = name;
        this.id = id;
        this.deck = new ArrayList<Card>();

    }
    public void addCard(Card card){
        this.deck.add(card);
    }
    public ArrayList<Card> cardList()
    {
        return new ArrayList<>(deck);
    }
    public Card removeCard(String id)
    {
        Card dummy_card = null;
        for (int i = 0; i < deck.size(); i++)
        {
            if (Objects.equals(deck.get(i).id, id))
            {
                dummy_card = deck.get(i);
                deck.remove(i);
                break;
            }
        }
        return dummy_card;
    }

    private int deckLength()
    {
        return deck.size();
    }

    public boolean deckValid()
    {
        HashMap<String, Integer> cardCounting = new HashMap<>();
        if(deckLength() != 60)
        {
            return false;
        }
        for (int i = 0; i < deckLength(); i++)
        {
            if (cardCounting.get(deck.get(i).name) == 4)
            {
                return false;
            }
            if (!cardCounting.containsKey(deck.get(i).name))
            {
                cardCounting.put(deck.get(i).name, 0);
            }
            cardCounting.put(deck.get(i).name, cardCounting.get(deck.get(i).name) + 1);

        }
        return true;
    }

    /**
     * Returns a string representing the content of the deck. 
     * The string is formatted to be a list of card ids seperated by a semicolon.
     */
    public String getContentString() {
        StringBuilder result = new StringBuilder("");
        for (Card card : deck) {
            if (result.length() > 0) {
                result.append(";");
            }
            result.append(card.id);
        }

        return result.toString();
    }

    /*
     * Constructs a deck from metadata and a string describing its contents.
     */
    // public static Deck fromContentString(String name, String id, String contentString) {
    //     Deck deck = new Deck(name, id);

    //     String[] cardIds = contentString.split(";");
    //     for (String cardId : cardIds) {
    //         deck.addCard(null);
    //     }
    // } can't implement from here, need data access object
}
