import entity.Card;
import entity.Deck;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    private User testUser;

    @Before
    public void setUp() {
        testUser = new User("testUser", "testPassword");
    }

    @Test
    public void testAddDeck() {
        Deck testDeck = new Deck("TestDeck", "TestDeck");
        testUser.addDeck(testDeck);
        assertTrue(testUser.getDecks().contains(testDeck));
    }

    @Test
    public void testGetDecks() {
        Deck testDeck1 = new Deck("TestDeck1", "TestDeck1");
        Deck testDeck2 = new Deck("TestDeck2", "TestDeck2");
        testUser.addDeck(testDeck1);
        testUser.addDeck(testDeck2);

        assertEquals(2, testUser.getDecks().size());
    }

    @Test
    public void testAddOwnedCard() {
        Card testCard = new Card("TestCard", "s", "s");
        testUser.addOwnedCard(testCard);
        assertTrue(testUser.getOwnedCards().contains(testCard));
    }

    @Test
    public void testAddOwnedCards() {
        Card testCard1 = new Card("TestCard1", "s", "s");
        Card testCard2 = new Card("TestCard2", "s", "s");
        List<Card> cardsToAdd = List.of(testCard1, testCard2);

        testUser.addOwnedCards(cardsToAdd);

        assertEquals(2, testUser.getOwnedCards().size());
    }

    @Test
    public void testRemoveOwnedCardById() {
        Card testCard = new Card("TestCard", "s", "s");
        testUser.addOwnedCard(testCard);

        testUser.removeOwnedCard("s");

        assertFalse(testUser.getOwnedCards().contains(testCard));
    }

    @Test
    public void testRemoveOwnedCardByObject() {
        Card testCard = new Card("TestCard", "s", "s");
        testUser.addOwnedCard(testCard);

        testUser.removeOwnedCard(testCard);

        assertFalse(testUser.getOwnedCards().contains(testCard));
    }

}