package entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class Deck {
    public ArrayList<Card> deck;
    public final String name;
    public final String id;
    public final String author;
    public Deck(String name, String id, String author){
        this.name = name;
        this.id = id;
        this.deck = new ArrayList<Card>();
        this.author = author;
    }

    public Deck(String name, String id) {
        this(name, id, "Unknown");
    }

    public void addCard(Card card){
        this.deck.add(card);
    }
    public ArrayList<Card> getCards()
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

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Deck clone() {
        Deck result = new Deck(name, id, author);

        for (Card card : deck) {
            result.addCard(card);
        }

        return result;
    }

    public Collection<String> getProblems() {
        ArrayList<String> problems = new ArrayList<>();

        if (deckLength() < 60) {
            problems.add("Deck is too small: %s/60".formatted(deckLength()));
        } else if (deckLength() > 60) {
            problems.add("Deck is too big: %s > 60".formatted(deckLength()));
        }

        HashMap<Card, Integer> counts = new HashMap<>();
        for (Card card : deck) {
            if (!counts.containsKey(card))
                counts.put(card, 0);

            counts.put(card, counts.get(card) + 1);

            if (counts.get(card) == 5) {
                problems.add("Too many copies of card: %s".formatted(card.name));
            }
        }

        return problems;
    }

    public boolean isValid() {
        return getProblems().size() == 0;
    }
}
