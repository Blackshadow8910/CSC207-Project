package usecase.app.trade;

import java.util.ArrayList;

import entity.Message;
import entity.SellListing;
import entity.User;

public interface TradeDataAccessInterface {
    public SellListing getSellListing(String id);
    public ArrayList<SellListing> getSellListings();
    public void uploadSellListing(SellListing sellListing);
    public void closeSellListing(String id);
    public void replyToSellListing(String sellListingId, Message message);
}
