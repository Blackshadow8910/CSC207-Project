package usecase.app.trade;

import entity.*;

import java.util.ArrayList;

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
    public void replyToConversation(Conversation conversation, Message message) {
        dataAccessObject.replyToConversation(conversation, message);
    }

    @Override
    public void resolveSellListing(SellListing listing, User buyer, Card offer) {
        try {
            dataAccessObject.closeSellListing(listing, buyer, offer);
            presenter.presentInfoMessage("Succcessfully closed.");
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Card not in inventory")) {
                presenter.presentInfoMessage("The other trader does not have the card they are offering.");
            } else if (e.getMessage().equals("Sell listing nonexistent")) {
                presenter.presentInfoMessage("Sell listing no longer exists.");
            }else if (e.getMessage().equals("Card not in seller inventory")) {
                presenter.presentInfoMessage("You do not have the card you are offering.");
            }
        }
    }

    @Override
    public void uploadSellListing(SellListing listing) {
        dataAccessObject.uploadSellListing(listing);
    }
}
