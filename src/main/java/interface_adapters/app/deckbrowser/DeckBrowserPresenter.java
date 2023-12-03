package interface_adapters.app.deckbrowser;

import java.awt.Image;
import java.util.ArrayList;

import entity.Deck;
import usecase.app.deckbrowser.DeckBrowserOutputBoundary;

public class DeckBrowserPresenter implements DeckBrowserOutputBoundary {
    private DeckBrowserViewModel viewModel;
    
    public DeckBrowserPresenter(DeckBrowserViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentDecks(ArrayList<Deck> decks) {
        viewModel.setDecks(decks);
    }

    @Override
    public void setDefaultDeckImage(Image image) {
        viewModel.defaultDeckImage = image;
    }
}
