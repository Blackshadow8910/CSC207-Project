package usecase.database;

import entity.SellListing;

public interface MarketplaceDataAccessInterface {
    public SellListing getSellListing(String id);
    public void uploadSellListing(SellListing sellListing);
    public void closeSellListing(String id);
    public void replyToSellListing(String sellListingId, String userID, String contents);
}
