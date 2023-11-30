package interface_adapters.app.deckbuilder;

import usecase.app.deckbuilder.DeckBuilderInputBoundary;
import usecase.app.deckbuilder.DeckBuilderInputBoundary;

public class DeckBuilderController {
    public DeckBuilderInputBoundary inputBoundary;

    public DeckBuilderController(DeckBuilderInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }
}
