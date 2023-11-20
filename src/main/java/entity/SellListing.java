package entity;

import java.util.ArrayList;
import java.util.List;

public class SellListing {
    public final String cardID;
    public final String sellerID;
    public final float price;
    /** A list of the conversations the seller has with interested users; 
     * the 0th participant should always be the seller.
     * the 1st participant should be the user inquiring about the listing.
     * */
    private final ArrayList<Conversation> conversations = new ArrayList<>();

    public SellListing(String cardID, String sellerID, float price) {
        this.cardID = cardID;
        this.sellerID = sellerID;
        this.price = price;
    }

    public SellListing(Card card, User seller, float price) {
        this(card.id, seller.username, price);
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public Conversation openConversation(String inquirerID) {
        Conversation c = new Conversation();
        c.addParticipant(sellerID);
        c.addParticipant(inquirerID);
        conversations.add(c);
        
        return c;
    }
}
