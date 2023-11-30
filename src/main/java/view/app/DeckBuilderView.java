package view.app;

import javax.swing.JPanel;

import interface_adapters.app.deckbuilder.DeckBuilderController;
import interface_adapters.app.deckbuilder.DeckBuilderViewModel;

public class DeckBuilderView extends JPanel {
    private DeckBuilderViewModel viewModel;
    private DeckBuilderController controller;

    public DeckBuilderView(DeckBuilderViewModel viewModel, DeckBuilderController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
    }
}
