package view.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.Card;
import entity.Deck;
import entity.PokemonCard;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.deckbuilder.DeckBuilderController;
import interface_adapters.app.deckbuilder.DeckBuilderViewModel;
import usecase.app.cardsearch.CardDisplayData;
import usecase.app.deckbuilder.DeckBuilderInputData;
import util.GridBagConstraintBuilder;
import util.ImagePanel;

public class DeckBuilderView extends JPanel {
    private final DeckBuilderViewModel viewModel;
    public final DeckBuilderController controller;
    private final AppViewModel appViewModel;

    private JPanel gridContainer = new JPanel(new GridBagLayout());

    private final CardLayout cardPanelLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardPanelLayout);
    private final GridBagConstraints  cardPanelGBC = new GridBagConstraintBuilder()
            .gridx(1)
            .gridy(1)
            .weightx(1)
            .weighty(1)
            .build();

    private final CardView addCardView = new CardView();
    private final CardView removeCardView = new CardView();
    
    private final InfoPanel infoPanel = new InfoPanel();
    private final GridBagConstraints infoPanelGBC = new GridBagConstraintBuilder()
            .gridx(2)
            .gridheight(2)
            .weightx(0)
            .build();

    private final CardSearchBarView searchBarPanel = new CardSearchBarView();
    private final GridBagConstraints searchPanelGBC = new GridBagConstraintBuilder()
            .gridx(1)
            .build();

    private final JButton[] subtabButtons = {
        new JButton("Add"),
        new JButton("Remove")
    };

    private final JPanel subtabButtonContainer = new JPanel(new GridBagLayout());
    private final GridBagConstraints subtabButtonContainerGBC = new GridBagConstraintBuilder()
        .gridx(0)
        .gridheight(2)
        .fill(GridBagConstraints.BOTH)
        .build();


    public DeckBuilderView(DeckBuilderViewModel viewModel, DeckBuilderController controller, AppViewModel appViewModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.appViewModel = appViewModel;

        setLayout(new BorderLayout());

        // UI setup

        int i = 0;
        for (JButton button : subtabButtons) {
            GridBagConstraints gbc = new GridBagConstraintBuilder()
                .gridy(i)
                .weighty(1)
                .build();

            button.addActionListener(evt -> {
                showSubCardView(button.getText());
            });
            button.setFocusable(false);

            subtabButtonContainer.add(button, gbc);

            i++;
        }

        searchBarPanel.setLabelVisible(false);

        gridContainer.add(subtabButtonContainer, subtabButtonContainerGBC);
        gridContainer.add(cardPanel, cardPanelGBC);
        gridContainer.add(infoPanel, infoPanelGBC);
        gridContainer.add(searchBarPanel, searchPanelGBC);

        cardPanel.add(addCardView, "Add");
        cardPanel.add(removeCardView, "Remove");

        // Bind

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidateSizes();
            }
        });

        viewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("currentSubCardView")) {
                showSubCardView(viewModel.getSubCardView());

                if (viewModel.getSubCardView().equals("Remove"))
                    refreshRemoveCardView();
            } else if (evt.getPropertyName().equals("results")) {
                getActiveSubCardView().displayResults((ArrayList<CardDisplayData>) evt.getNewValue());
            } else if (evt.getPropertyName().equals("deck")) {
                displayDeckData(viewModel.getDeck());
                if (appViewModel.currentTab.equals("Deck browser")) {
                    appViewModel.setTab("Deck builder");
                }
            }
        });

        searchBarPanel.addSearchListener(evt -> {
            if (viewModel.getSubCardView().equals("Add")) {
                controller.search(new DeckBuilderInputData(evt.data.filter, DeckBuilderInputData.ADD, viewModel.getDeck()));
            } else {
                controller.search(new DeckBuilderInputData(evt.data.filter, DeckBuilderInputData.REMOVE, viewModel.getDeck()));
            }
        });

        addCardView.addSelectListener(evt -> {
            viewModel.addToDeck(evt.selectedCard);
        });
        
        removeCardView.addSelectListener(evt -> { 
            viewModel.removeFromDeck(evt.selectedCard);
            refreshRemoveCardView();
        });
        
        // Finalize
        
        showSubCardView("Add");
        add(gridContainer, BorderLayout.CENTER);
    }
    
    private void refreshRemoveCardView() {
        controller.search(new DeckBuilderInputData(searchBarPanel.getFilter(), DeckBuilderInputData.REMOVE, viewModel.getDeck()));
    }

    public void showSubCardView(String id) {
        cardPanelLayout.show(cardPanel, id);
        if (!viewModel.getSubCardView().equals(id))
            viewModel.setSubCardView(id);

        for (JButton button : subtabButtons) {
            button.setEnabled(!button.getText().equals(id));
        }
        
        revalidateSizes();
    }

    public CardView getActiveSubCardView() {
        if (viewModel.getSubCardView().equals("Add")) {
            return addCardView;
        } else {
            return removeCardView;
        }
    }

    public void displayDeckData(Deck deck) {
        infoPanel.displayData(deck);
    }

    private void revalidateSizes() {
        infoPanel.setPreferredSize(new Dimension((int)Math.round(getWidth() * 0.25), getHeight()));
        infoPanel.setSize(infoPanel.getPreferredSize());
        searchBarPanel.setPreferredSize(new Dimension(0, 40));
        cardPanel.setPreferredSize(new Dimension(0, 0));
        revalidate();
    }

    private JPanel wrapInBorderContainer(JComponent c) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(c, BorderLayout.CENTER);

        return panel;
    }

    public CardSearchBarView getSearchBarPanel() {
        return  searchBarPanel;
    }

    public CardView getAddCardView() {
        return addCardView;
    }

    public CardView getRemoveCardView() {
        return removeCardView;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public class InfoPanel extends JPanel {
        private Deck currentDeck;

        private JPanel mainContainer = new JPanel();
        private BoxLayout mainContainerLayout = new BoxLayout(mainContainer, BoxLayout.Y_AXIS);

        // private final ImagePanel imagePanel = new ImagePanel(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
        private final JLabel nameLabel = new JLabel();
        private final JTextPane mainTextPane = new JTextPane();

        private final JButton saveButton = new JButton("Save");
        private final ArrayList<RemoveListener> removeListeners = new ArrayList<>();

        public InfoPanel() {
            setLayout(new BorderLayout());
            mainContainer.setLayout(mainContainerLayout);

            // imagePanel.setPreferredSize(new Dimension(114, 160));

            saveButton.addActionListener(evt -> {
                SaveDialog saveDialog = new SaveDialog(closeEvt -> {
                    String deckAuthor = viewModel.getDeck().author;
                    if (deckAuthor.equals("Unknown")) {
                        deckAuthor = appViewModel.currentUser.username;
                    }
                    Deck deck = new Deck(closeEvt.name, closeEvt.name, deckAuthor);

                    for (Card card : viewModel.getDeck().getCards()) {
                        deck.addCard(card);
                    }
                    
                    viewModel.setDeck(deck);
                    controller.saveDeck(deck);
                });
                
                saveDialog.setDeckName(viewModel.getDeck().name);
                saveDialog.displayDialog(DeckBuilderView.this);
            });

            nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            nameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            
            mainTextPane.setEditable(false);

            // mainContainer.add(imagePanel);
            mainContainer.add(nameLabel);
            mainContainer.add(mainTextPane);
            
            add(saveButton, BorderLayout.SOUTH);
            add(mainContainer, BorderLayout.CENTER);

            clearData();
        }

        public void displayData(Deck deck) {
            this.currentDeck = deck;

            saveButton.setEnabled(true);

            nameLabel.setText(deck.name);
            //imagePanel.setImage(deck.image);
            mainTextPane.setText("""
                Cards: %s/60
                Pokemon: %s/6
                Is valid: %s
                Problems: %s
            """.formatted(
                deck.getCards().size(),
                deck.getCards().stream().filter((Card card) -> card instanceof PokemonCard).toList().size(),
                deck.isValid(),
                String.join("\n", deck.getProblems())
            ));
            
            revalidate();
            repaint();
        }

        public void clearData() {
            currentDeck = null;
            nameLabel.setText("No deck selected.");
            // imagePanel.setImage(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
            mainTextPane.setText("""
            """.formatted());

            revalidate();
            repaint();
        }

        public JButton getSaveButton() {
            return saveButton;
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

    public class SaveDialog extends JDialog {
        private Consumer<CloseEvent> callback;

        private final JPanel boxContainer = new JPanel();
        private final BoxLayout boxLayout = new BoxLayout(boxContainer, BoxLayout.Y_AXIS);

        private final JPanel spacer = new JPanel();

        private final JPanel nameInputPanel = new JPanel(new BorderLayout());
        private final JLabel nameInputLabel = new JLabel("Name: ");
        private final JTextField nameInputField = new JTextField();

        private final JButton saveButton = new JButton("Save");

        public SaveDialog() {
            super(new JFrame(), "Save", true);

            boxContainer.setLayout(boxLayout);
            boxContainer.setBorder(new EmptyBorder(new Insets(12, 12, 12, 12)));

            nameInputPanel.add(nameInputLabel, BorderLayout.WEST);
            nameInputPanel.add(nameInputField, BorderLayout.CENTER);
            spacer.setPreferredSize(new Dimension(200, 0));
            
            boxContainer.add(spacer);
            boxContainer.add(nameInputPanel);
            boxContainer.add(wrapInBorderContainer(saveButton));

            saveButton.addActionListener(evt -> {
                fireCallback(new CloseEvent(SaveDialog.this, nameInputField.getText()));
                SaveDialog.this.dispose();
            });

            add(boxContainer);

            pack();
        }

        public SaveDialog(Consumer<CloseEvent> callback) {
            this();
            this.callback = callback;
        }

        public void displayDialog(JComponent origin) {
            setLocationRelativeTo(origin);
            setVisible(true);
        }

        public void setDeckName(String name) {
            nameInputField.setText(name);
        }

        public class CloseEvent extends EventObject {
            public final String name;
            public CloseEvent(Object source, String name) {
                super(source);
                this.name = name;
            }
        }

        private void fireCallback(CloseEvent evt) {
            if (callback != null) {
                callback.accept(evt);
            }
        }
    }
}
