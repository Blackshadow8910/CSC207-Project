package view.app;

import interface_adapters.app.inventory.InventoryController;
import interface_adapters.app.inventory.InventoryViewModel;
import util.GridBagConstraintBuilder;

import javax.swing.*;

import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.PokemonGuruCardSearchFilter;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class InventoryView extends JPanel {
    private InventoryViewModel viewModel;
    private InventoryController controller;

    private JPanel gridContainer = new JPanel(new GridBagLayout());

    private final CardView cardPanel = new CardView();
    private final GridBagConstraints  cardPanelGBC = new GridBagConstraintBuilder()
            .gridy(1)
            .weightx(1)
            .weighty(1)
            .build();
    
    private final JPanel infoPanel = new JPanel();
    private final GridBagConstraints infoPanelGBC = new GridBagConstraintBuilder()
            .gridx(1)
            .gridheight(2)
            .weightx(0)
            .build();

    private final CardSearchBarView searchBarPanel = new CardSearchBarView();
    private final GridBagConstraints searchPanelGBC = new GridBagConstraintBuilder()
            .build();

    public InventoryView(InventoryViewModel viewModel, InventoryController controller) {
        setLayout(new BorderLayout());
        this.viewModel = viewModel;
        this.controller = controller;

        // Setup

        searchBarPanel.setLabelVisible(false);

        gridContainer.add(cardPanel, cardPanelGBC);
        gridContainer.add(infoPanel, infoPanelGBC);
        gridContainer.add(searchBarPanel, searchPanelGBC);

        // Listeners
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                infoPanel.setPreferredSize(new Dimension((int)Math.round(getWidth() * 0.25), getHeight()));
                searchBarPanel.setPreferredSize(new Dimension(0, 40));
                revalidate();
            }
        });

        add(gridContainer, BorderLayout.CENTER);
    }

    private void refreshCardDisplay() {
        PokemonGuruCardSearchFilter filter = searchBarPanel.getFilter();

        controller.
    }
}
