package entity;

import java.util.ArrayList;
import java.util.List;

public class SellListing {
    public final String id;
    public final String cardID;
    public final String sellerID;
    public final float price;
    /** A list of the conversations the seller has with interested users; 
     * the 0th participant should always be the seller.
     * the 1st participant should be the user inquiring about the listing.
     * */
    private final ArrayList<Conversation> conversations = new ArrayList<>();

    public SellListing(String id, String cardID, String sellerID, float price) {
        this.id = id;
        this.cardID = cardID;
        this.sellerID = sellerID;
        this.price = price;
    }

    public SellListing(String id, Card card, User seller, float price) {
        this(id, card.id, seller.username, price);
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public Conversation openConversation(String inquirerId) {
        Conversation c = getConversationWith(inquirerId);

        if (c != null) {
            return c;
        }

        c = new Conversation();
        c.addParticipant(sellerID);
        c.addParticipant(inquirerId);
        conversations.add(c);
        
        return c;
    }

    public Conversation getConversationWith(String inquirerId) {
        for (Conversation c : conversations) {
            if (c.getParticipants().get(1).equals(inquirerId)) {
                return c;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getCardID() {
        return cardID;
    }

    public String getSellerID() {
        return sellerID;
    }
}
