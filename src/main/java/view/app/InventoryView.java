package view.app;

import entity.PokemonCard;
import entity.SellListing;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.inventory.InventoryController;
import interface_adapters.app.inventory.InventoryViewModel;
import interface_adapters.app.trade.TradeViewModel;
import usecase.app.cardsearch.CardDisplayData;
import util.GridBagConstraintBuilder;
import util.ImagePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.PokemonGuruCardSearchFilter;
import entity.Card;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.UUID;
import java.util.function.Consumer;

public class InventoryView extends JPanel {
    private InventoryViewModel viewModel;
    private InventoryController controller;
    private final AppViewModel appViewModel;
    private final TradeViewModel tradeViewModel;

    private JPanel gridContainer = new JPanel(new GridBagLayout());

    private final CardView cardPanel = new CardView();
    private final GridBagConstraints  cardPanelGBC = new GridBagConstraintBuilder()
            .gridy(1)
            .weightx(1)
            .weighty(1)
            .build();
    
    private final InfoPanel infoPanel = new InfoPanel();
    private final GridBagConstraints infoPanelGBC = new GridBagConstraintBuilder()
            .gridx(1)
            .gridheight(2)
            .weightx(0)
            .build();

    private final CardSearchBarView searchBarPanel = new CardSearchBarView();
    private final GridBagConstraints searchPanelGBC = new GridBagConstraintBuilder()
            .build();

    public InventoryView(InventoryViewModel viewModel, InventoryController controller, AppViewModel appViewModel, TradeViewModel tradeViewModel) {
        setLayout(new BorderLayout());
        this.viewModel = viewModel;
        this.controller = controller;
        this.appViewModel = appViewModel;
        this.tradeViewModel = tradeViewModel;

        // Setup

        searchBarPanel.setLabelVisible(false);

        gridContainer.add(cardPanel, cardPanelGBC);
        gridContainer.add(infoPanel, infoPanelGBC);
        gridContainer.add(searchBarPanel, searchPanelGBC);

        // Listeners

        viewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("currentResults")) {
                cardPanel.displayResults(viewModel.getCurrentResults());
            }
        });

        infoPanel.addRemoveListener(evt -> {
            controller.removeCard(viewModel.getCurrentUser(), evt.card);
            refreshCardDisplay();
        });

        cardPanel.addSelectListener(evt -> {
            infoPanel.displayData(evt.data);
        });

        searchBarPanel.addSearchListener(evt -> {
            performSearch(evt.data.filter);
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                infoPanel.setPreferredSize(new Dimension((int)Math.round(getWidth() * 0.25), getHeight()));
                searchBarPanel.setPreferredSize(new Dimension(0, 40));
                revalidate();
            }
        });

        appViewModel.addLoginListener(evt -> {
            viewModel.setCurrentUser(evt.user);
            refreshCardDisplay();
        });

        appViewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("currentTab")) {
                if (appViewModel.currentTab.equals("My cards")) {
                    refreshCardDisplay();
                }
            }
        });

        add(gridContainer, BorderLayout.CENTER);
    }

    private void refreshCardDisplay() {
        PokemonGuruCardSearchFilter filter = searchBarPanel.getFilter();

        performSearch(filter);
    }

    private void performSearch(PokemonGuruCardSearchFilter filter) {
        if (viewModel.getCurrentUser() == null) {
            JOptionPane.showMessageDialog(this, "Not logged in.", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.displayInventory(viewModel.getCurrentUser(), filter);
    }

    public class InfoPanel extends JPanel {
        private Card currentCard;

        private JPanel mainContainer = new JPanel();
        private BoxLayout mainContainerLayout = new BoxLayout(mainContainer, BoxLayout.Y_AXIS);

        private final ImagePanel imagePanel = new ImagePanel(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
        private final JLabel nameLabel = new JLabel();

        private final JPanel buttonContainer = new JPanel(new GridLayout(1, 2));
        private final JButton sellButton = new JButton("Sell");
        private final JButton removeButton = new JButton("Remove");
        private final ArrayList<RemoveListener> removeListeners = new ArrayList<>();

        public InfoPanel() {
            setLayout(new BorderLayout());
            mainContainer.setLayout(mainContainerLayout);

            imagePanel.setPreferredSize(new Dimension(114, 160));

            sellButton.addActionListener(evt ->{
                SellDialog dialog = new SellDialog(price -> {
                    tradeViewModel.uploadSellListing(new SellListing(
                            UUID.randomUUID().toString(),
                            currentCard,
                            appViewModel.currentUser,
                            price
                    ));
                    appViewModel.setTab("Trade cards");
                });

                if (currentCard instanceof PokemonCard)
                    dialog.setPrice(((PokemonCard) currentCard).marketPrice);
                dialog.displayDialog(InventoryView.this);
            });

            removeButton.addActionListener(evt -> {
                if (currentCard == null) {
                    return;
                }

                RemoveEvent emitEvent = new RemoveEvent(currentCard);
                for (RemoveListener removeListener : removeListeners) {
                    removeListener.onRemove(emitEvent);
                }
                clearData();
            });

            mainContainer.add(imagePanel);
            mainContainer.add(nameLabel);

            buttonContainer.add(sellButton);
            buttonContainer.add(removeButton);
            add(buttonContainer, BorderLayout.SOUTH);
            add(mainContainer, BorderLayout.CENTER);

            clearData();
        }

        public void displayData(CardDisplayData data) {
            currentCard = data.card;

            removeButton.setEnabled(true);
            sellButton.setEnabled(true);

            nameLabel.setText(data.card.name);
            imagePanel.setImage(data.image);
            
            revalidate();
            repaint();
        }

        public void clearData() {
            currentCard = null;

            removeButton.setEnabled(false);
            sellButton.setEnabled(false);

            nameLabel.setText("");
            imagePanel.setImage(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));

            revalidate();
            repaint();
        }

        public record RemoveEvent(Card card) {
        }

        public static interface RemoveListener extends EventListener {
            public void onRemove(RemoveEvent evt);
        }

        public void addRemoveListener(RemoveListener listener) {
            removeListeners.add(listener);
        }
    }

    public class SellDialog extends JDialog {
        private Consumer<Double> callback;

        private final JPanel boxContainer = new JPanel();
        private final BoxLayout boxLayout = new BoxLayout(boxContainer, BoxLayout.Y_AXIS);

        private final JPanel spacer = new JPanel();

        private final JPanel priceInputPanel = new JPanel(new BorderLayout());
        private final JLabel priceInputLabel = new JLabel("Name: ");
        private final JSpinner priceInputField = new JSpinner(new SpinnerNumberModel(5.0, 0.0, 10000, 0.25));

        private final JButton saveButton = new JButton("Save");

        public SellDialog() {
            super(new JFrame(), "Save", true);

            boxContainer.setLayout(boxLayout);
            boxContainer.setBorder(new EmptyBorder(new Insets(12, 12, 12, 12)));

            priceInputPanel.add(priceInputLabel, BorderLayout.WEST);
            priceInputPanel.add(priceInputField, BorderLayout.CENTER);
            spacer.setPreferredSize(new Dimension(200, 0));

            boxContainer.add(spacer);
            boxContainer.add(priceInputPanel);
            boxContainer.add(wrapInBorderContainer(saveButton));

            saveButton.addActionListener(evt -> {
                fireCallback((Double) priceInputField.getValue());
                SellDialog.this.dispose();
            });

            add(boxContainer);

            pack();
        }

        private JPanel wrapInBorderContainer(JComponent c) {
            JPanel panel = new JPanel(new BorderLayout());

            panel.add(c, BorderLayout.CENTER);

            return panel;
        }

        public SellDialog(Consumer<Double> callback) {
            this();
            this.callback = callback;
        }

        public void displayDialog(JComponent origin) {
            setLocationRelativeTo(origin);
            setVisible(true);
        }

        public void setPrice(Double name) {
            priceInputField.setValue(name);
        }
        private void fireCallback(double price) {
            if (callback != null) {
                callback.accept(price);
            }
        }
    }
}
