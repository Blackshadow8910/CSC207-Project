package interface_adapters.app.trade;

import entity.Message;
import entity.SellListing;
import usecase.app.trade.TradeInputBoundary;

public class TradeController {
    private final TradeInputBoundary inputBoundary;

    public TradeController(TradeInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void updateSellListings() {
        inputBoundary.updateSellListings();
    }

    public void replyToSellListing(SellListing listing, Message message) {
        inputBoundary.replyToSellListing(listing, message);
    }
}
