import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.*;

public class SellListingTest {

    @org.junit.Test
    public void testOpenConversation() {
        User seller = new User("Seller", "Password123");
        User inquirer = new User("Inquirer", "Password123");
        Card card = new Card("Pikachu", "1", "imageURL");
        SellListing sellListing = new SellListing("456", card, seller, 10.0);

        Conversation conversation = sellListing.openConversation(inquirer);

        assertNotNull(conversation);
        assertEquals(2, conversation.getParticipants().size());
        assertTrue(conversation.getParticipants().contains(seller));
        assertTrue(conversation.getParticipants().contains(inquirer));
        assertTrue(sellListing.getConversations().contains(conversation));
    }

    @org.junit.Test
    public void testOpenExistingConversation() {
        User seller = new User("Seller", "Password123");
        User inquirer = new User("Inquirer", "Password123");
        Card card = new Card("Pikachu", "1", "imageURL");
        SellListing sellListing = new SellListing("456", card, seller, 10.0);

        Conversation existingConversation = new Conversation();
        existingConversation.addParticipant(seller);
        existingConversation.addParticipant(inquirer);
        sellListing.getConversations().add(existingConversation);

        Conversation conversation = sellListing.openConversation(inquirer);

        assertNotNull(conversation);
        assertEquals(existingConversation, conversation);
        assertEquals(2, conversation.getParticipants().size());
        assertTrue(conversation.getParticipants().contains(seller));
        assertTrue(conversation.getParticipants().contains(inquirer));
    }

    @org.junit.Test
    public void testGetConversationWith() {
        User seller = new User("Seller", "Password123");
        User inquirer = new User("Inquirer", "Password123");
        Card card = new Card("Pikachu", "1", "imageURL");
        SellListing sellListing = new SellListing("456", card, seller, 10.0);

        Conversation existingConversation = new Conversation();
        existingConversation.addParticipant(seller);
        existingConversation.addParticipant(inquirer);
        sellListing.getConversations().add(existingConversation);

        Conversation conversation = sellListing.getConversationWith(inquirer);
        List<Message> messages = conversation.getMessages();
        List<Message> expected = new ArrayList<>();

        assertNotNull(conversation);
        assertEquals(existingConversation, conversation);
        assertEquals(expected, messages);

        Date date = new Date();
        Message message = new Message(seller, "test", date);

        assertEquals("Seller", message.getSender());
        assertEquals("test", message.getContent());
        assertEquals(date, message.getDate());

        conversation.sendMessage(message);
        assertEquals(1, conversation.getMessages().size());


        assertEquals(2, conversation.getParticipants().size());
        assertTrue(conversation.getParticipants().contains(seller));
        assertTrue(conversation.getParticipants().contains(inquirer));
    }

    @org.junit.Test
    public void testGetConversations() {
        User seller = new User("Seller", "Password123");
        User inquirer = new User("Inquirer", "Password123");
        Card card = new Card("Pikachu", "1", "imageURL");
        SellListing sellListing = new SellListing("456", card, seller, 10.0);

        List<Conversation> conversations = sellListing.getConversations();

        assertNotNull(conversations);
        assertEquals(0, conversations.size());
    }

    @org.junit.Test
    public void testGetId() {
        String id = "456";
        User seller = new User("Seller", "Password123");
        Card card = new Card("Pikachu", "1", "imageURL");
        SellListing sellListing = new SellListing(id, card, seller, 10.0);

        assertEquals(id, sellListing.getId());
    }

    @org.junit.Test
    public void testGetCard() {
        User seller = new User("Seller", "Password123");
        Card card = new Card("Pikachu", "1", "imageURL");
        SellListing sellListing = new SellListing("456", card, seller, 10.0);

        assertEquals(card, sellListing.getCard());
    }

    @org.junit.Test
    public void testGetSeller() {
        User seller = new User("Seller", "Password123");
        Card card = new Card("Pikachu", "1", "imageURL");
        SellListing sellListing = new SellListing("456", card, seller, 10.0);

        assertEquals(seller, sellListing.getSeller());
    }
}