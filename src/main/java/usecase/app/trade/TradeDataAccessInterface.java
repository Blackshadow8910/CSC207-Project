package usecase.app.trade;

import entity.*;

import java.util.ArrayList;

public interface TradeDataAccessInterface {
    SellListing getSellListing(String id);
    ArrayList<SellListing> getSellListings();
    void uploadSellListing(SellListing sellListing);
    void closeSellListing(SellListing listing, User buyer, Card offer);
    void replyToConversation(Conversation conversation, Message message);
}
