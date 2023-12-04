package view.app;

import interface_adapters.app.AppViewModel;
import interface_adapters.app.cardsearch.CardSearchController;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import usecase.app.cardsearch.CardDisplayData;
import util.GridBagConstraintBuilder;
import util.ImagePanel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CardSearchView extends JPanel {
    private final CardSearchViewModel viewModel;
    private final CardSearchController controller;
    private final AppViewModel appViewModel;
    
    private final GridBagLayout mainGridBagLayout = new GridBagLayout();
    private final JPanel mainContainer = new JPanel(mainGridBagLayout);

    private final JPanel infoPanel = new JPanel();
    private final JPanel namePanel = new JPanel();
    private final JPanel descriptionPanel = new JPanel();
    private final JLabel cardNameLabel = new JLabel();
    private final JLabel cardTypeLabel = new JLabel();
    private final JLabel cardSetIDLabel = new JLabel();
    private final JPanel cardNamePanel = new JPanel();
    private final JPanel cardInfoPanel = new JPanel();
    private final JLabel cardPriceLabel = new JLabel();
    private final JLabel cardSubtypesLabel = new JLabel();
    private final ImagePanel cardImagePanel = new ImagePanel(new BufferedImage(112, 160, BufferedImage.TYPE_4BYTE_ABGR));
    private final MatteBorder infoPanelBorder = new MatteBorder(0, 1, 0, 0, Color.GRAY);
    private final GridBagConstraints infoPanelGBC = new GridBagConstraintBuilder()
        .fill(GridBagConstraints.BOTH)
        .gridx(1)
        .gridheight(2)
        .weightx(0.5)
        .weighty(1)
        .build();

    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final GridBagConstraints mainPanelGBC = new GridBagConstraintBuilder()
        .fill(GridBagConstraints.BOTH)
        .gridx(0).gridy(1)
        .weighty(11)
        .build();
    
    private final CardSearchBarView searchPanel = new CardSearchBarView();
    private final GridBagConstraints searchPanelGBC = new GridBagConstraintBuilder()
        .fill(GridBagConstraints.BOTH)
        .gridy(0)
        .weightx(1)
        .weighty(1)
        .build();
    
    private final CardView resultContainer = new CardView();

    public CardSearchView(CardSearchViewModel viewModel, CardSearchController controller, AppViewModel appViewModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.appViewModel = appViewModel;

        // Setup UI

        infoPanel.setLayout(new BorderLayout());

        infoPanel.setBorder(infoPanelBorder);
        // Creating The Header for the Info Box, just the card's name
        cardNamePanel.add(cardNameLabel);
        // Creating The Card Information
        cardInfoPanel.setLayout(new GridLayout(2,2));

        cardInfoPanel.add(cardTypeLabel);
        cardInfoPanel.add(cardSubtypesLabel);
        cardInfoPanel.add(cardPriceLabel);
        cardInfoPanel.add(cardSetIDLabel);

        // Adding the Name and Image

        JPanel cardImageContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        cardImageContainer.add(cardImagePanel);
        cardImagePanel.setPreferredSize(new Dimension((int) (240 * 0.8), (int) (340 * 0.8)));

        JButton addInventoryButton = new JButton("Add to inventory");
        addInventoryButton.addActionListener(evt -> {
            if (viewModel.getCurrentCard()!= null)
                appViewModel.currentUser.addOwnedCard(viewModel.getCurrentCard());
        });

        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.add(cardInfoPanel, BorderLayout.CENTER);
        bottomContainer.add(addInventoryButton, BorderLayout.SOUTH);

        infoPanel.add(cardNamePanel, BorderLayout.NORTH);
        infoPanel.add(cardImageContainer, BorderLayout.CENTER);
        infoPanel.add(bottomContainer, BorderLayout.SOUTH);

        mainPanel.add(resultContainer, BorderLayout.CENTER);

        // Bind Behaviours

        viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("displayedResults")) {
                    ArrayList<CardDisplayData> results = (ArrayList<CardDisplayData>) evt.getNewValue();

                    System.out.printf("Displaying %s results from search.%n", results.size());
                    resultContainer.displayResults(results);
                }
            }
        });

        resultContainer.addSelectListener(evt -> {
            infoPanel.setVisible(true);
            cardNameLabel.setText(evt.selectedCard.name);
            cardTypeLabel.setText(evt.selectedCard.name);
            try {
                Field typesField = evt.selectedCard.getClass().getField("types");
                List<String> typesValue = (List<String>) typesField.get(evt.selectedCard);
                cardTypeLabel.setText("Types : " + typesValue.toString().replaceAll("[\\[\\]]", " "));
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
            try {
                Field typesField = evt.selectedCard.getClass().getField("subtypes");
                List<String> typesValue = (List<String>) typesField.get(evt.selectedCard);

                cardSubtypesLabel.setText("Subtypes : " + typesValue.toString().replaceAll("[\\[\\]]", " "));
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
            try {
                Field typesField = evt.selectedCard.getClass().getField("marketPrice");
                double typesValue = (double) typesField.get(evt.selectedCard);
                double rounded_value = (double) Math.round(typesValue * 100) /100;
                cardPriceLabel.setText("Market Price : $" + rounded_value);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {

            }
            try {
                Field typesField = evt.selectedCard.getClass().getField("setID");
                String typesValue = (String) typesField.get(evt.selectedCard);
                cardSetIDLabel.setText("SetID : " + typesValue);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }

            viewModel.setCurrentCard(evt.selectedCard);
            addInventoryButton.setEnabled(true);


            // Add the Card Image

            cardImagePanel.setImage(evt.data.image);

            // Adjust panel and container sizes as needed
            Dimension panelSize2 = new Dimension(50, 100);
            infoPanel.setPreferredSize(panelSize2);
            resultContainer.setPreferredSize(new Dimension(1000, 100));
        });

        searchPanel.addSearchListener(evt -> {
            controller.performSearch(evt.data);
        });
        addInventoryButton.setEnabled(false);
        
        // Finish setting UI

        mainContainer.add(mainPanel, mainPanelGBC);
        mainContainer.add(searchPanel, searchPanelGBC);
        mainContainer.add(infoPanel, infoPanelGBC);
        //infoPanel.setVisible(false);

        setLayout(new BorderLayout());
        add(mainContainer, BorderLayout.CENTER);
    }
}
