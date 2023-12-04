package usecase.app.deckbrowser;

public interface DeckBrowserInputBoundary {
    void displayDecks();
    void displayDecks(DeckSearchFilter filter);
}
