package usecase.app.trade;

import entity.*;

public interface TradeInputBoundary {
    public void updateSellListings();
    public void replyToConversation(Conversation conversation, Message message);
    public void resolveSellListing(SellListing listing, User buyer, Card offer);
    public void uploadSellListing(SellListing listing);
}
