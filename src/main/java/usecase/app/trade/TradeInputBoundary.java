package usecase.app.trade;

import entity.Message;
import entity.SellListing;

public interface TradeInputBoundary {
    public void updateSellListings();
    public void replyToSellListing(SellListing listing, Message message);
}
