package interface_adapters.app.deckbrowser;

import usecase.app.deckbrowser.DeckBrowserInputBoundary;
import usecase.app.deckbrowser.DeckSearchFilter;

public class DeckBrowserController {
    public DeckBrowserInputBoundary inputBoundary;

    public DeckBrowserController(DeckBrowserInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void displayDecks(String query) {
        inputBoundary.displayDecks(new DeckSearchFilter(query));
    }
}
