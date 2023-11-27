package view.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

import entity.Message;
import entity.SellListing;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.trade.TradeController;
import interface_adapters.app.trade.TradeViewModel;
import util.GridBagConstraintBuilder;

public class TradeView extends JPanel {
    private final TradeController controller;
    private final TradeViewModel viewModel;
    private final AppViewModel appViewModel;

    private final GridBagLayout mainContainerLayout = new GridBagLayout();
    private final JPanel mainContainer = new JPanel(mainContainerLayout);

    private final JPanel subtabButtonContainer = new JPanel(new GridBagLayout());
    private final GridBagConstraints subtabButtonContainerGBC = new GridBagConstraintBuilder()
        .gridx(0)
        .fill(GridBagConstraints.BOTH)
        .build();

    private final CardLayout subtabContainerLayout = new CardLayout();    
    private final JPanel subtabContainer = new JPanel(subtabContainerLayout);
    private final GridBagConstraints subtabContainerGBC = new GridBagConstraintBuilder()
        .gridx(1)
        .weightx(1)
        .weighty(1)
        .build();

    private final JButton[] subtabButtons = {
        new JButton("Browse"),
        new JButton("Listing")
    };

    private final JPanel browsePanel = new JPanel();
    private final GridBagLayout browsePanelLayout = new GridBagLayout();
    private final JScrollPane browseScrollPane = new JScrollPane(browsePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private final JPanel listingPanel = new JPanel(new BorderLayout());
    private final JButton listingBackButton = new JButton("Back");
    private final JPanel conversationPanel = new JPanel(new GridBagLayout());
    private final JScrollPane conversationScrollPane = new JScrollPane(conversationPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private final JPanel conversationTypingContainer = new JPanel(new GridBagLayout()); 
    private final JTextArea conversationTypingArea = new JTextArea();
    private final JButton conversationSendButton = new JButton("Send");
    

    public TradeView(TradeViewModel viewModel, TradeController controller, AppViewModel appViewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.appViewModel = appViewModel;

        setLayout(new BorderLayout());

        int i = 0;
        for (JButton button : subtabButtons) {
            GridBagConstraints gbc = new GridBagConstraintBuilder()
                .gridy(i)
                .weighty(1)
                .build();

            button.addActionListener(evt -> {
                showSubtab(button.getText());
            });
            button.setFocusable(false);

            subtabButtonContainer.add(button, gbc);

            i++;
        }

        browsePanel.setLayout(browsePanelLayout);

        conversationTypingContainer.add(conversationTypingArea, GridBagConstraintBuilder.constraint(0, 0, 1, 0));
        conversationTypingContainer.add(conversationSendButton, GridBagConstraintBuilder.constraint(1, 0));

        listingPanel.add(listingBackButton, BorderLayout.WEST);
        listingPanel.add(conversationScrollPane, BorderLayout.CENTER);
        listingPanel.add(conversationTypingContainer, BorderLayout.SOUTH);

        subtabButtonContainer.setPreferredSize(new Dimension(80, 0));
        subtabContainer.add(browseScrollPane, "Browse");
        subtabContainer.add(listingPanel, "Listing");

        
        // mainContainer.add(subtabButtonContainer, subtabButtonContainerGBC);
        mainContainer.add(subtabContainer, subtabContainerGBC);
        showSubtab("Browse");
        
        // Listeners
        
        viewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("currentListings")) {
                displayListings(viewModel.getListings());
            } else if (evt.getPropertyName().equals("currentListing")) {
                SellListing listing = viewModel.getCurrentListing(); 
                if (listing != null) {
                    displayListingDetails(listing);
            
                    showSubtab("Listing");
                }
            }
        });
        updateSellListings();

        listingBackButton.addActionListener(evt -> {
            viewModel.setCurrentListing(null);
            showSubtab("Browse");
        });

        conversationSendButton.addActionListener(evt -> {
            controller.replyToSellListing(viewModel.getCurrentListing(), new Message(
                appViewModel.currentUser, 
                conversationTypingArea.getText(),
                new Date()
            ));

            conversationTypingArea.setText("");
            displayListingDetails(viewModel.getCurrentListing());
        });
        
        add(mainContainer, BorderLayout.CENTER);
    }


    public JButton getSubtabButton(String label) {
        for (JButton button : subtabButtons) {
            if (button.getText().equals(label)) {
                return button;
            }
        }
        return null;
    }

    public void showSubtab(String label) {
        subtabContainerLayout.show(subtabContainer, label);
    }

    private void updateSellListings() {
        controller.updateSellListings();
    }

    private void displayListings(ArrayList<SellListing> listings) {
        System.out.println("Listings: %s".formatted(listings.size()));
        browsePanel.removeAll();
        int i = 0;
        for (SellListing listing : listings) {
            JComponent c = createSellListingEntry(listing);
            GridBagConstraints gbc = new GridBagConstraintBuilder()
                .gridy(i)
                .build();

            browsePanel.add(c, gbc);

            i++;
        }
        browsePanel.revalidate();
    }

    private JComponent createSellListingEntry(SellListing listing) {
        JPanel panel = new JPanel();
        JButton replyButton = new JButton("Reply");
        JPanel centerContainer = new JPanel(new BorderLayout());


        panel.setLayout(new BorderLayout());

        
        panel.setMaximumSize(new Dimension(800, 80));
        panel.setMinimumSize(panel.getMaximumSize());
        panel.setPreferredSize(panel.getMaximumSize());
        replyButton.setPreferredSize(new Dimension(80, 40));
        replyButton.setFocusable(false);
        
        panel.setBorder(new MatteBorder(1, 0, 0, 0, Color.gray));

        centerContainer.add(new JLabel(listing.card.name), BorderLayout.WEST);
        centerContainer.add(new JLabel("Listed by: %s".formatted(listing.seller.username)), BorderLayout.EAST);
        panel.add(centerContainer, BorderLayout.CENTER);
        panel.add(replyButton, BorderLayout.EAST);

        replyButton.addActionListener(evt -> {
            openTradeConversation(listing);
        });

        return panel;
    }


    private void openTradeConversation(SellListing listing) {
        viewModel.setCurrentListing(listing);
    }

    private void displayListingDetails(SellListing listing) {

    }
}
