package entity;

import java.util.ArrayList;
import java.util.List;

public class SellListing {
    public final String id;
    public final Card card;
    public final User seller;
    public final double price;
    /** A list of the conversations the seller has with interested users; 
     * the 0th participant should always be the seller.
     * the 1st participant should be the user inquiring about the listing.
     * */
    private final ArrayList<Conversation> conversations = new ArrayList<>();

    public SellListing(String id, Card card, User seller, double price) {
        this.id = id;
        this.card = card;
        this.seller = seller;
        this.price = price;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public Conversation openConversation(User inquirer) {
        Conversation c = getConversationWith(inquirer);

        if (c != null) {
            return c;
        }

        c = new Conversation();
        c.addParticipant(seller);
        c.addParticipant(inquirer);
        conversations.add(c);
        
        return c;
    }

    public Conversation getConversationWith(User inquirer) {
        for (Conversation c : conversations) {
            if (c.getParticipants().get(1).equals(inquirer)) {
                return c;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public Card getCard() {
        return card;
    }

    public User getSeller() {
        return seller;
    }
}
