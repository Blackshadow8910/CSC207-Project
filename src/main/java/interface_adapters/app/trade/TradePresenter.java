package interface_adapters.app.trade;

import entity.SellListing;
import usecase.app.trade.TradeOutputBoundary;

import java.util.ArrayList;

public class TradePresenter implements TradeOutputBoundary {
    private final TradeViewModel viewModel;
    
    public TradePresenter(TradeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentSellListings(ArrayList<SellListing> listings) {
        viewModel.setListings(listings);
    }
    public void presentInfoMessage(String message) {
        viewModel.setInfoMessage(message);
    }
}
