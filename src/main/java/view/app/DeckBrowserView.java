package view.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entity.Deck;
import interface_adapters.app.deckbrowser.DeckBrowserController;
import interface_adapters.app.deckbrowser.DeckBrowserViewModel;
import interface_adapters.app.deckbuilder.DeckBuilderViewModel;
import util.GridBagConstraintBuilder;
import util.ImagePanel;

public class DeckBrowserView extends JPanel {
    private final DeckBrowserViewModel viewModel;
    private final DeckBrowserController controller;
    private final DeckBuilderViewModel deckBuilderViewModel;

    private JPanel gridContainer = new JPanel(new GridBagLayout());

    private final FlowLayout resultContainerLayout = new FlowLayout(FlowLayout.LEFT, 12, 12);
    private final JPanel resultContainer = new JPanel(resultContainerLayout);
    private final JScrollPane resultScrollPane = new JScrollPane(resultContainer);
    private final Dimension resultEntrySize = new Dimension(114, 160 + 20);
    
    private final GridBagConstraints  resultContainerGBC = new GridBagConstraintBuilder()
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

    public DeckBrowserView(DeckBrowserViewModel viewModel, DeckBrowserController controller, DeckBuilderViewModel deckBuilderViewModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.deckBuilderViewModel = deckBuilderViewModel;

        // Setup
        setLayout(new BorderLayout());
        searchBarPanel.setLabelVisible(false);
        searchBarPanel.advancedEnabled(false);

        resultScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resultScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resultScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        resultScrollPane.getViewport().setScrollMode(2);

        add(resultScrollPane, BorderLayout.CENTER);

        gridContainer.add(resultScrollPane, resultContainerGBC);
        gridContainer.add(infoPanel, infoPanelGBC);
        gridContainer.add(searchBarPanel, searchPanelGBC);

        // Bind

        viewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("currentDecks")) {
                displayDecks(viewModel.getDecks());
            }
        });

        searchBarPanel.addSearchListener(evt -> {
            infoPanel.clearData();
            controller.displayDecks(evt.data.filter.name);
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                infoPanel.setPreferredSize(new Dimension((int)Math.round(getWidth() * 0.25), getHeight()));
                infoPanel.setMinimumSize(infoPanel.getPreferredSize());
                infoPanel.setMaximumSize(infoPanel.getPreferredSize());
                searchBarPanel.setPreferredSize(new Dimension(0, 40));
                resultScrollPane.setPreferredSize(new Dimension(0, 0));
                revalidate();
            }
        });


        add(gridContainer, BorderLayout.CENTER);
    }

    private void displayDecks(ArrayList<Deck> decks) {
        resultContainer.removeAll();

        int number = 0;
        for (Deck deck : decks) {
            JPanel panel = createDeckEntry(deck);

            resultContainer.add(panel);
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    infoPanel.displayData(deck);
                }
            });
            number++;

            panel.repaint();
        }
        int result = number / 5;

        resultContainer.revalidate();
        resultContainer.repaint();
        resultContainer.setPreferredSize(new Dimension(getWidth() - 100, 200 + (result * (resultEntrySize.height + resultContainerLayout.getVgap()))));
    }

    private void editDeck(Deck deck) {
        deckBuilderViewModel.setDeck(deck.clone());
    }

    private JPanel createDeckEntry(Deck deck) {
        JPanel panel = new JPanel(new BorderLayout());

        
        ImagePanel imagePanel = new ImagePanel(viewModel.defaultDeckImage);
        JLabel textLabel = new JLabel(deck.name);
        
        panel.add(imagePanel, BorderLayout.CENTER);
        panel.add(textLabel, BorderLayout.SOUTH);
        
        textLabel.setBackground(Color.BLUE);
        textLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.setPreferredSize(resultEntrySize);
        imagePanel.setPadding(new Insets(25, 0, 25, 0));
        
        // Hover stylings: Subject to change
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                //panel.setBorder(b);
                panel.setBackground(new Color(0, 0, 0, 50));
                resultContainer.repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                //panel.setBorder(null);
                panel.setBackground(null);
                resultContainer.repaint();
            }
        });
        
        return panel;
    }

    private class InfoPanel extends JPanel {
        private Deck currentDeck;

        private JPanel mainContainer = new JPanel();
        private BoxLayout mainContainerLayout = new BoxLayout(mainContainer, BoxLayout.Y_AXIS);

        // private final ImagePanel imagePanel = new ImagePanel(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
        private final JLabel nameLabel = new JLabel();
        private final JLabel authorLabel = new JLabel();

        private final JLabel cardCountLabel = new JLabel();
        private final JLabel validLabel = new JLabel();

        private final JButton editButton = new JButton("Edit");

        public InfoPanel() {
            setLayout(new BorderLayout());
            mainContainer.setLayout(mainContainerLayout);

            // imagePanel.setPreferredSize(new Dimension(114, 160));

            editButton.addActionListener(evt -> {
                assert currentDeck != null;
                editDeck(currentDeck);
            });

            nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            nameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            authorLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            authorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            cardCountLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            validLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);

            // mainContainer.add(imagePanel);
            mainContainer.add(nameLabel);
            mainContainer.add(authorLabel);
            mainContainer.add(cardCountLabel);
            mainContainer.add(validLabel);

            add(editButton, BorderLayout.SOUTH);
            add(mainContainer, BorderLayout.CENTER);

            clearData();
        }

        public void displayData(Deck deck) {
            currentDeck = deck;

            editButton.setEnabled(true);

            nameLabel.setText(deck.name);
            authorLabel.setText("By %s".formatted(deck.author));
            cardCountLabel.setText("Cards: %s/60".formatted(deck.deck.size()));
            validLabel.setText("Is valid: %s".formatted((deck.isValid()) ? "Yes" : "No"));
            // imagePanel.setImage(data.image);
            
            revalidate();
            repaint();
        }

        public void clearData() {
            currentDeck = null;

            editButton.setEnabled(false);

            nameLabel.setText("");
            authorLabel.setText("");
            cardCountLabel.setText("");
            validLabel.setText("");
            // imagePanel.setImage(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));

            revalidate();
            repaint();
        }
    }
}
