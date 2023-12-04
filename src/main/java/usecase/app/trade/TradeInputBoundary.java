package usecase.app.trade;

import entity.*;

public interface TradeInputBoundary {
    void updateSellListings();
    void replyToConversation(Conversation conversation, Message message);
    void resolveSellListing(SellListing listing, User buyer, Card offer);
    void uploadSellListing(SellListing listing);
}
