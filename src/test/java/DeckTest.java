import entity.Card;
import entity.Deck;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void testAddCard() {
        Deck deck = new Deck("Test Deck", "123");

        Card card = new Card("Card1", "001", "imageURL1");
        deck.addCard(card);

        assertTrue(deck.getCards().contains(card));
    }

    @Test
    public void testRemoveCard() {
        Deck deck = new Deck("Test Deck", "123");

        Card card = new Card("Card1", "001", "imageURL1");
        deck.addCard(card);

        Card removedCard = deck.removeCard("001");

        assertEquals(card, removedCard);
        assertFalse(deck.getCards().contains(card));
    }

    @Test
    public void testDeckLength() {
        Deck deck = new Deck("Test Deck", "123");

        assertEquals(0, deck.deckLength());

        Card card1 = new Card("Card1", "001", "imageURL1");
        Card card2 = new Card("Card2", "002", "imageURL2");

        deck.addCard(card1);
        deck.addCard(card2);

        assertEquals(2, deck.deckLength());
    }

    @Test
    public void testGetProblems() {
        Deck deck = new Deck("Test Deck", "123");

        // Add more than 60 cards to create a problem
        for (int i = 0; i < 70; i++) {
            Card card = new Card("Card" + i, "00" + i, "imageURL" + i);
            deck.addCard(card);
        }

        Collection<String> problems = deck.getProblems();

        assertTrue(problems.contains("Deck is too big: 70 > 60"));
    }

    @Test
    public void testIsValid() {
        Deck validDeck = new Deck("Valid Deck", "456");
        for (int i = 0; i < 60; i++) {
            validDeck.addCard(new Card("Card" + i, "00" + i, "imageURL" + i));
        }

        assertTrue(validDeck.isValid());

        Deck invalidDeck = new Deck("Invalid Deck", "789");
        invalidDeck.addCard(new Card("Card100", "100", "imageURL100"));
        invalidDeck.addCard(new Card("Card101", "101", "imageURL101"));

        assertFalse(invalidDeck.isValid());
    }
}
