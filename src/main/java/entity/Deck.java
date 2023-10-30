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
        return deck;
    }
    public Card removeCard(String card_name)
    {
        Card dummy_card = null;
        for (int i = 0; i < deck.size(); i++)
        {
            if (Objects.equals(deck.get(i).name, card_name))
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
}
