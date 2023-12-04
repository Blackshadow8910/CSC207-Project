package interface_adapters.app.trade;

import entity.Conversation;
import entity.SellListing;
import interface_adapters.ViewModel;

import java.util.ArrayList;

public class TradeViewModel extends ViewModel{
    private ArrayList<SellListing> currentListings = new ArrayList<>();
    private SellListing currentListing = null;
    private Conversation currentConversation = null;

    public TradeViewModel() {
        super("trade");
    }

    public void setListings(ArrayList<SellListing> listings) {
        Object oldValue = currentListings;
        currentListings = listings;

        firePropertyChanged("currentListings", null, currentListings);
    }

    public ArrayList<SellListing> getListings() {
        return new ArrayList<>(currentListings);
    }

    public void setCurrentConversation(SellListing listing, Conversation conv) {
        Object oldValue = currentListing;
        currentListing = listing;
        Object oldConv = currentConversation;
        currentConversation = conv;

        firePropertyChanged("currentListing", oldValue, currentListing);
        firePropertyChanged("currentConversation", oldConv, currentConversation);
    }

    public void setInfoMessage(String message) {
        firePropertyChanged("infoMessage", null, message);
    }

    public void uploadSellListing(SellListing listing) {
        firePropertyChanged("uploadListing", null, listing);
    }

    public SellListing getCurrentListing() {
        return currentListing;
    }
    public Conversation getCurrentConversation() {return currentConversation; }
}
