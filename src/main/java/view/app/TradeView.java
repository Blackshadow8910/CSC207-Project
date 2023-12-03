package view.app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import entity.*;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.inventory.InventoryViewModel;
import interface_adapters.app.trade.TradeController;
import interface_adapters.app.trade.TradeViewModel;
import usecase.app.cardsearch.CardDisplayData;
import util.GridBagConstraintBuilder;
import util.ImagePanel;

public class TradeView extends JPanel {
    private final TradeController controller;
    private final TradeViewModel viewModel;
    private final AppViewModel appViewModel;
    private final InventoryViewModel inventoryViewModel;

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
    private final JButton conversationOfferButton = new JButton("Offer");
    private final JPanel listingDetailPanel = new JPanel(new BorderLayout());
    private final ImagePanel listingImagePanel = new ImagePanel(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
    private final JTextPane listingDetailMainText = new JTextPane();


    

    public TradeView(TradeViewModel viewModel, TradeController controller, AppViewModel appViewModel, InventoryViewModel inventoryViewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.appViewModel = appViewModel;
        this.inventoryViewModel = inventoryViewModel;

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
        conversationTypingContainer.add(conversationOfferButton, GridBagConstraintBuilder.constraint(1, 0));
        conversationTypingContainer.add(conversationSendButton, GridBagConstraintBuilder.constraint(2, 0));

        listingDetailPanel.setPreferredSize(new Dimension(240, 0));
        listingDetailPanel.add(listingDetailMainText);
        listingDetailMainText.setEditable(false);
//        listingDetailPanel.add(listingImagePanel);
//        listingImagePanel.setPreferredSize(new Dimension(114, 160));

        listingPanel.add(listingBackButton, BorderLayout.WEST);
        listingPanel.add(listingDetailPanel, BorderLayout.EAST);
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
            } else if (evt.getPropertyName().equals("currentConversation")) {
                SellListing listing = viewModel.getCurrentListing();
                Conversation conversation = viewModel.getCurrentConversation();
                if (listing != null && conversation != null) {
                    displayListingDetails(listing, conversation);
            
                    showSubtab("Listing");
                }
            } else if (evt.getPropertyName().equals("infoMessage")) {
                String message = (String) evt.getNewValue();
                JOptionPane.showMessageDialog(this, message);
            } else if (evt.getPropertyName().equals("uploadListing")) {
                SellListing listing = (SellListing) evt.getNewValue();

                controller.uploadSellListing(listing);
            }
        });

        appViewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("currentUser")) {
                if (appViewModel.currentUser != null) {
                    updateSellListings();
                    viewModel.setCurrentConversation(null, null);
                    showSubtab("Browse");
                }
            } if (evt.getPropertyName().equals("currentTab")) {
                if (appViewModel.currentTab.equals("Trade cards")) {
                    updateSellListings();
                    viewModel.setCurrentConversation(null, null);
                    showSubtab("Browse");
                }
            }
        });

        listingBackButton.addActionListener(evt -> {
            viewModel.setCurrentConversation(null, null);
            showSubtab("Browse");
        });

        conversationSendButton.addActionListener(evt -> {
            String messageText = conversationTypingArea.getText();
            if (messageText.isEmpty()) {
                return;
            }

            controller.replyToConversation(viewModel.getCurrentConversation(), new Message(
                appViewModel.currentUser, 
                messageText,
                new Date()
            ));

            conversationTypingArea.setText("");
            displayListingDetails(viewModel.getCurrentListing(), viewModel.getCurrentConversation());
        });

        conversationOfferButton.addActionListener(evt -> {
            OfferDialog dialog = new OfferDialog(closeEvt -> {
                String messageText = conversationTypingArea.getText();
                controller.replyToConversation(viewModel.getCurrentConversation(), new Message(
                        appViewModel.currentUser,
                        messageText,
                        new Date(),
                        closeEvt.selectedCard
                ));

                conversationTypingArea.setText("");
                displayListingDetails(viewModel.getCurrentListing(), viewModel.getCurrentConversation());
            });

            dialog.setCards(inventoryViewModel.getCurrentResults());
            dialog.displayDialog(TradeView.this);
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
        browsePanel.removeAll();

        ArrayList<SellListing> ownListings = new ArrayList<>();

        int i = 0;
        for (SellListing listing : listings) {
            if (listing.seller.username.equals(appViewModel.currentUser.username)) {
                ownListings.add(listing);
                continue;
            }

            JComponent c = createSellListingEntry(listing);
            GridBagConstraints gbc = new GridBagConstraintBuilder()
                .gridy(i)
                .build();

            browsePanel.add(c, gbc);

            i++;
        }

        for (SellListing listing : ownListings) {
            JPanel[] entries = createOwnSellListingEntries(listing);
            for (JPanel entry : entries) {
                GridBagConstraints gbc = new GridBagConstraintBuilder()
                        .gridy(i)
                        .build();

                browsePanel.add(entry, gbc);

                i++;
            }
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
            openTradeConversation(listing, appViewModel.currentUser);
        });

        return panel;
    }

    private JPanel[] createOwnSellListingEntries(SellListing listing) {
        JPanel[] jPanels = new JPanel[listing.getConversations().size() + 1];

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(new JLabel("You listed %s for $%s".formatted(listing.card.name, listing.price)), BorderLayout.WEST);
        headerPanel.setBorder(new MatteBorder(0, 0, 3, 0, Color.gray));
        headerPanel.setMaximumSize(new Dimension(800, 80));
        headerPanel.setMinimumSize(headerPanel.getMaximumSize());
        headerPanel.setPreferredSize(headerPanel.getMaximumSize());
        jPanels[0] = headerPanel;

        int i = 1;
        for (Conversation conversation : listing.getConversations()) {
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
            centerContainer.add(new JLabel("Response by: %s".formatted(conversation.getParticipants().get(1).username)), BorderLayout.EAST);
            panel.add(centerContainer, BorderLayout.CENTER);
            panel.add(replyButton, BorderLayout.EAST);

            replyButton.addActionListener(evt -> {
                openTradeConversation(listing, conversation);
            });

            jPanels[i] = panel;
        }
        return jPanels;
    }


    private void openTradeConversation(SellListing listing, Conversation conv) {
        viewModel.setCurrentConversation(listing, conv);
    }

    private void openTradeConversation(SellListing listing, User with) {
        viewModel.setCurrentConversation(listing, listing.openConversation(with));
    }

    private void displayListingDetails(SellListing listing, Conversation conv) {
        conversationPanel.removeAll();

        conversationOfferButton.setVisible(listing.seller != appViewModel.currentUser);

        int i = 0;
        for (Message message : conv.getMessages()) {
            conversationPanel.add(createMessageEntry(message), new GridBagConstraintBuilder()
                            .gridy(i)
                            .weightx(1)
                            .insets(new Insets(8, 8, 8, 8))
                            .build());

            i++;
        }

        listingDetailMainText.setText("""
                %s wants to sell %s for $%s. You can also make a counteroffer.
                
                Card Name %s
                Card ID: %s
                Market value: $%s
                """.formatted(
                        listing.seller.username,
                        listing.card.name,
                        listing.price,
                        listing.card.name,
                        listing.card.id,
                        (listing.card instanceof PokemonCard) ? ((PokemonCard) listing.card).marketPrice : "Unknown"
                ));

        conversationPanel.add(new JPanel(), GridBagConstraintBuilder.constraint(0, i, 0, 1));
    }

    private JComponent createMessageEntry(Message message) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel senderLabel = new JLabel(message.getSender());
        JTextPane contentLabel = new JTextPane();// (message.getContent());

        contentLabel.setText(message.getContent());
        contentLabel.setEditable(false);

        senderLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));

        panel.add(senderLabel, BorderLayout.NORTH);
        panel.add(contentLabel, BorderLayout.CENTER);

        panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));

        if (message.getCardOffer() != null) {
            JPanel offerPanel = new JPanel(new BorderLayout());
            JLabel offerLabel = new JLabel("Offer: %s".formatted(message.getCardOffer().name));
            JButton offerButton = new JButton("Accept");

            offerPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.gray));
            offerButton.setEnabled(!message.getSender().equals(appViewModel.currentUser.username));

            offerPanel.add(offerLabel, BorderLayout.WEST);
            offerPanel.add(offerButton, BorderLayout.CENTER);

            offerButton.addActionListener(evt -> {
                controller.resolveListing(viewModel.getCurrentListing(), message);
                viewModel.setCurrentConversation(null, null);
                updateSellListings();
                showSubtab("Browse");
            });

            panel.add(offerPanel, BorderLayout.SOUTH);
        }

        return panel;
    }

    private class OfferDialog extends JDialog {
        private Consumer<CloseEvent> callback;

        private final JPanel boxContainer = new JPanel();
        private final BoxLayout boxLayout = new BoxLayout(boxContainer, BoxLayout.Y_AXIS);

        private final JPanel spacer = new JPanel();

        private final CardView cardView = new CardView();

        public OfferDialog() {
            super(new JFrame(), "Offer Trade", true);

            boxContainer.setLayout(boxLayout);
            boxContainer.setBorder(new EmptyBorder(new Insets(12, 12, 12, 12)));

            spacer.setPreferredSize(new Dimension(614, 0));
            cardView.setPreferredSize(new Dimension(614, 450));

            boxContainer.add(cardView);

            cardView.addSelectListener(evt -> {
                fireCallback(new CloseEvent(
                        this,
                        evt.selectedCard
                ));
                dispose();
            });

            add(boxContainer);

            pack();
        }

        public OfferDialog(Consumer<CloseEvent> callback) {
            this();
            this.callback = callback;
        }

        public void displayDialog(JComponent origin) {
            setLocationRelativeTo(origin);
            setVisible(true);
        }

        public OfferDialog setCards(List<CardDisplayData> cards) {
            cardView.displayResults(cards);
            return this;
        }

        public static class CloseEvent extends EventObject {
            public final Card selectedCard;
            public CloseEvent(Object source, Card selectedCard) {
                super(source);
                this.selectedCard = selectedCard;
            }
        }

        private void fireCallback(CloseEvent evt) {
            if (callback != null) {
                callback.accept(evt);
            }
        }
    }
}
