package interface_adapters.app.deckbrowser;

import entity.Deck;
import usecase.app.deckbrowser.DeckBrowserOutputBoundary;

import java.awt.*;
import java.util.ArrayList;

public class DeckBrowserPresenter implements DeckBrowserOutputBoundary {
    private final DeckBrowserViewModel viewModel;
    
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
