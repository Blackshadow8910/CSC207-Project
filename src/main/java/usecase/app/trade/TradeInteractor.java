package usecase.app.trade;

import java.util.ArrayList;

import entity.Message;
import entity.SellListing;

public class TradeInteractor implements TradeInputBoundary {
    private final TradeOutputBoundary presenter;
    private final TradeDataAccessInterface dataAccessObject;

    public TradeInteractor(TradeOutputBoundary presenter, TradeDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    public void updateSellListings() {
        ArrayList<SellListing> listings = dataAccessObject.getSellListings();

        presenter.presentSellListings(listings);
    }

    @Override
    public void replyToSellListing(SellListing listing, Message message) {
        dataAccessObject.replyToSellListing(listing.id, message);
    } 
}
