package interface_adapters.app.trade;

import java.util.ArrayList;

import entity.SellListing;
import interface_adapters.ViewModel;

public class TradeViewModel extends ViewModel{
    private ArrayList<SellListing> currentListings = new ArrayList<>();
    private SellListing currentListing = null;

    public TradeViewModel() {
        super("trade");
    }

    public void setListings(ArrayList<SellListing> listings) {
        Object oldValue = currentListings;
        currentListings = listings;

        firePropertyChanged("currentListings", oldValue, currentListings);
    }

    public ArrayList<SellListing> getListings() {
        return new ArrayList<>(currentListings);
    }

    public void setCurrentListing(SellListing listing) {
        Object oldValue = currentListing;
        currentListing = listing;

        firePropertyChanged("currentListing", oldValue, currentListing);
    }

    public SellListing getCurrentListing() {
        return currentListing;
    }
}
