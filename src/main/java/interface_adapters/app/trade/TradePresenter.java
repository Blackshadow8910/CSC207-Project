package interface_adapters.app.trade;

import java.util.ArrayList;

import entity.SellListing;
import usecase.app.trade.TradeOutputBoundary;

public class TradePresenter implements TradeOutputBoundary {
    private TradeViewModel viewModel;
    
    public TradePresenter(TradeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentSellListings(ArrayList<SellListing> listings) {
        viewModel.setListings(listings);
    }
}
