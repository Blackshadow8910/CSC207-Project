package view.app;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import interface_adapters.app.cardsearch.CardSearchController;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import usecase.app.cardsearch.CardDisplayData;
import util.GridBagConstraintBuilder;

public class CardSearchView extends JPanel {
    private CardSearchViewModel viewModel;
    private CardSearchController controller;
    
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
    private final JLabel cardImageLabel = new JLabel();
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
    
    private final CardSearchBarView searchPanel = new CardSearchBarView();;
    private final GridBagConstraints searchPanelGBC = new GridBagConstraintBuilder()
        .fill(GridBagConstraints.BOTH)
        .gridy(0)
        .weightx(1)
        .weighty(1)
        .build();
    
    private final CardView resultContainer = new CardView();

    public CardSearchView(CardSearchViewModel viewModel, CardSearchController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        // Setup UI

        infoPanel.setLayout(new GridLayout(3,1));

        infoPanel.setBorder(infoPanelBorder);
        // Creating The Header for the Info Box, just the card's name
        cardNamePanel.add(cardNameLabel);
        // Creating The Card Information
        cardInfoPanel.setLayout(new GridLayout(2,2));

        cardInfoPanel.add(cardTypeLabel);
        cardInfoPanel.add(cardSubtypesLabel);
        cardInfoPanel.add(cardPriceLabel);
        cardInfoPanel.add(cardSetIDLabel);

        // Creating The Card Image
        JPanel cardImagePanel = new JPanel();
        cardImagePanel.add(cardImageLabel);
        cardImagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Adding the Name and Image

        infoPanel.add(cardNamePanel);
        infoPanel.add(cardImagePanel);
        infoPanel.add(cardInfoPanel);


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




            // Add the Card Image

            try {
                URL imageUrl = new URL(evt.selectedCard.imageURL);
                ImageIcon imageIcon = new ImageIcon(imageUrl);
                // Scale the image to fit your requirements
                Image scaledImage = imageIcon.getImage().getScaledInstance(80, 100, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);

                // Assuming cardImageLabel is a JLabel where you want to display the image
                cardImageLabel.setIcon(imageIcon);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
                // Handle the exception (e.g., show an error message to the user)
            }

            // Adjust panel and container sizes as needed
            Dimension panelSize2 = new Dimension(50, 100);
            infoPanel.setPreferredSize(panelSize2);
            resultContainer.setPreferredSize(new Dimension(1000, 100));
        });

        searchPanel.addSearchListener(evt -> {
            controller.performSearch(evt.data);
        });
        
        // Finish setting UI

        mainContainer.add(mainPanel, mainPanelGBC);
        mainContainer.add(searchPanel, searchPanelGBC);
        mainContainer.add(infoPanel, infoPanelGBC);
        infoPanel.setVisible(false);

        setLayout(new BorderLayout());
        add(mainContainer, BorderLayout.CENTER);
    }
}
