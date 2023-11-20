package view.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import interface_adapters.app.cardsearch.CardSearchController;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import usecase.app.cardsearch.CardSearchInputData;
import usecase.app.cardsearch.CardDisplayData;
import util.GridBagConstraintBuilder;
import util.ImagePanel;
import view.app.CardView.SelectEvent;

public class CardSearchView extends JPanel {
    private CardSearchViewModel viewModel;
    private CardSearchController controller;
    
    private final GridBagLayout mainGridBagLayout = new GridBagLayout();
    private final JPanel mainContainer = new JPanel(mainGridBagLayout);

    private final JPanel infoPanel = new JPanel();
    private final JLabel infoLabel = new JLabel();
    private final MatteBorder infoPanelBorder = new MatteBorder(0, 1, 0, 0, Color.GRAY);
    private final GridBagConstraints infoPanelGBC = new GridBagConstraintBuilder()
        .fill(GridBagConstraints.BOTH)
        .gridx(1)
        .gridheight(2)
        .weightx(1)
        .weighty(1)
        .build();

    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final GridBagConstraints mainPanelGBC = new GridBagConstraintBuilder()
        .fill(GridBagConstraints.BOTH)
        .gridx(0).gridy(1)
        .weighty(11)
        .build();
    
    private final CardSearchBarView searchPanel = new CardSearchBarView();;
    private final GridBagConstraints searchPanelGBC = new GridBagConstraintBuilder()
        .fill(GridBagConstraints.BOTH)
        .gridy(0)
        .weightx(2)
        .weighty(1)
        .build();
    
    private final CardView resultContainer = new CardView();

    public CardSearchView(CardSearchViewModel viewModel, CardSearchController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        // Setup UI

        infoPanel.setBorder(infoPanelBorder);
        infoPanel.add(infoLabel);
        mainPanel.add(resultContainer, BorderLayout.CENTER);

        // Bind Behaviours

        viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("displayedResults")) {
                    ArrayList<CardDisplayData> results = (ArrayList<CardDisplayData>) evt.getNewValue();

                    System.out.println("Displaying %s results from search.".formatted(results.size()));
                    resultContainer.displayResults(results);
                }
            }
        });

        resultContainer.addSelectListener(evt -> {
            infoLabel.setText(evt.selectedCard.name);
        });

        searchPanel.addSearchListener(evt -> {
            controller.performSearch(evt.data);
        });
        
        // Finish setting UI

        mainContainer.add(mainPanel, mainPanelGBC);
        mainContainer.add(infoPanel, infoPanelGBC);
        mainContainer.add(searchPanel, searchPanelGBC);

        setLayout(new BorderLayout());
        add(mainContainer, BorderLayout.CENTER);
    }
}
