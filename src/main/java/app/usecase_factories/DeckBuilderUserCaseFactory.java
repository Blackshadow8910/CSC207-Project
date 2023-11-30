package app.usecase_factories;

import view.app.DeckBuilderView;

public class DeckBuilderUserCaseFactory {
    public DeckBuilderView create() {

        return new DeckBuilderView(null, null);
    }
}
