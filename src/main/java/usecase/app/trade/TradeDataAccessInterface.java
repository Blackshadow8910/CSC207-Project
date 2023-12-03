package usecase.app.trade;

import java.util.ArrayList;

import entity.*;

public interface TradeDataAccessInterface {
    public SellListing getSellListing(String id);
    public ArrayList<SellListing> getSellListings();
    public void uploadSellListing(SellListing sellListing);
    public void closeSellListing(SellListing listing, User buyer, Card offer);
    public void replyToConversation(Conversation conversation, Message message);
}
