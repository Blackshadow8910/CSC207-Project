package usecase.app.trade;

import java.util.ArrayList;

import entity.SellListing;

public interface TradeOutputBoundary {
    public void presentSellListings(ArrayList<SellListing> listings);
    public void presentInfoMessage(String message);
}
