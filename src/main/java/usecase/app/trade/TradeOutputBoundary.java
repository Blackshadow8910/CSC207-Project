package usecase.app.trade;

import entity.SellListing;

import java.util.ArrayList;

public interface TradeOutputBoundary {
    void presentSellListings(ArrayList<SellListing> listings);
    void presentInfoMessage(String message);
}
