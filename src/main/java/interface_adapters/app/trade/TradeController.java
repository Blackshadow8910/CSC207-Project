package interface_adapters.app.trade;

import entity.*;
import usecase.app.trade.TradeInputBoundary;

public class TradeController {
    private final TradeInputBoundary inputBoundary;

    public TradeController(TradeInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void updateSellListings() {
        inputBoundary.updateSellListings();
    }

    public void replyToConversation(Conversation conversation, Message message) {
        inputBoundary.replyToConversation(conversation, message);
    }

    public void resolveListing(SellListing listing, Message message) {
        Card returnOffer = message.getCardOffer();
        User buyer = null;
        for (Conversation conversation : listing.getConversations()) {
            if (conversation.getParticipants().get(1).username.equals(message.getSender())) {
                buyer = conversation.getParticipants().get(1);
                break;
            }
        }

        if (buyer == null) {
            throw new RuntimeException("Buyer doesn't have open conversation");
        }

        inputBoundary.resolveSellListing(listing, buyer, returnOffer);
    }

    public void uploadSellListing(SellListing listing) {
        inputBoundary.uploadSellListing(listing);
    }
}
