package view.app;

import interface_adapters.app.inventory.InventoryController;
import interface_adapters.app.inventory.InventoryViewModel;
import usecase.app.cardsearch.CardDisplayData;
import util.GridBagConstraintBuilder;
import util.ImagePanel;

import javax.swing.*;

import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.PokemonGuruCardSearchFilter;
import entity.Card;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventListener;

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
    
    private final InfoPanel infoPanel = new InfoPanel();
    private final GridBagConstraints infoPanelGBC = new GridBagConstraintBuilder()
            .gridx(1)
            .gridheight(2)
            .weightx(0)
            .build();

    private final CardSearchBarView searchBarPanel = new CardSearchBarView();
    private final GridBagConstraints searchPanelGBC = new GridBagConstraintBuilder()
            .build();

    public InventoryView(InventoryViewModel viewModel, InventoryController controller, AppView appView) {
        setLayout(new BorderLayout());
        this.viewModel = viewModel;
        this.controller = controller;

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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                infoPanel.setPreferredSize(new Dimension((int)Math.round(getWidth() * 0.25), getHeight()));
                searchBarPanel.setPreferredSize(new Dimension(0, 40));
                revalidate();
            }
        });

        appView.addLoginListener(evt -> {
            viewModel.setCurrentUser(evt.user);
            refreshCardDisplay();
        });

        add(gridContainer, BorderLayout.CENTER);
    }

    private void refreshCardDisplay() {
        PokemonGuruCardSearchFilter filter = searchBarPanel.getFilter();

        if (viewModel.getCurrentUser() == null) {
            JOptionPane.showMessageDialog(this, "Not logged in.", "", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.displayInventory(viewModel.getCurrentUser(), filter);
    }

    public static class InfoPanel extends JPanel {
        private Card currentCard;

        private JPanel mainContainer = new JPanel();
        private BoxLayout mainContainerLayout = new BoxLayout(mainContainer, BoxLayout.Y_AXIS);

        private final ImagePanel imagePanel = new ImagePanel(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
        private final JLabel nameLabel = new JLabel();

        private final JButton removeButton = new JButton("Remove");
        private final ArrayList<RemoveListener> removeListeners = new ArrayList<>();

        public InfoPanel() {
            setLayout(new BorderLayout());
            mainContainer.setLayout(mainContainerLayout);

            imagePanel.setPreferredSize(new Dimension(114, 160));

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

            add(removeButton, BorderLayout.SOUTH);
            add(mainContainer, BorderLayout.CENTER);

            clearData();
        }

        public void displayData(CardDisplayData data) {
            currentCard = data.card;

            removeButton.setEnabled(true);

            nameLabel.setText(data.card.name);
            imagePanel.setImage(data.image);
            
            revalidate();
            repaint();
        }

        public void clearData() {
            currentCard = null;

            removeButton.setEnabled(false);

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
}
