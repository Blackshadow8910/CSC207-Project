package view.app;

import entity.PokemonGuruCardSearchFilter;
import usecase.app.cardsearch.CardSearchInputData;
import util.GridBagConstraintBuilder;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.function.Consumer;

public class CardSearchBarView extends JPanel {
    private final LayoutManager searchPanelLayout = new GridBagLayout();
    private final Border searchPanelBorder = new EmptyBorder(0, 8, 0, 0);
    private final JLabel searchLabel = new JLabel("Search keywords: ");
    private final JTextField searchField = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final JButton advancedSearchButton = new JButton("Advanced");

    private final HashSet<SearchListener> searchListeners = new HashSet<>();

    public CardSearchBarView() {
        setBorder(searchPanelBorder);
        setLayout(searchPanelLayout);

        searchButton.setFocusable(false);
        advancedSearchButton.setFocusable(false);

        add(searchLabel);
        add(searchField, new GridBagConstraintBuilder().gridx(1).weightx(1).weighty(1).build());
        add(advancedSearchButton, new GridBagConstraintBuilder().gridx(2).build());
        add(searchButton, new GridBagConstraintBuilder().gridx(3).build());

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchField.setFocusable(false);
                    searchField.setFocusable(true);

                    searchButton.doClick();
                }
            }
        });

        searchButton.addActionListener(evt -> {
            //controller.performSearch(searchField.getText());
            fireSearchEvent(new SearchEvent(this, new CardSearchInputData(searchField.getText())));
        });

        advancedSearchButton.addActionListener(evt -> {
            new AdvancedSearchDialog(closeEvt -> {
                fireSearchEvent(new SearchEvent(this, new CardSearchInputData(closeEvt.filter)));
            }).displayDialog(CardSearchBarView.this);
        });
    }

    /**
     * Sets whether this search bar has advanced search settings enabled.
     */
    public CardSearchBarView advancedEnabled(boolean enabled) {
        advancedSearchButton.setVisible(enabled);
        return this;
    }

    public void addSearchListener(SearchListener l) {
        searchListeners.add(l);
    }

    private void fireSearchEvent(SearchEvent evt) {
        for (SearchListener l : searchListeners) {
            l.onSearch(evt);
        }
    }

    public JButton getSearchButton() {
        return searchButton;
    }


    public class SearchEvent extends EventObject {
        public final CardSearchInputData data;

        public SearchEvent(Object source, CardSearchInputData data) {
            super(source);

            this.data = data;
        }
    }

    public interface SearchListener extends EventListener {
        void onSearch(SearchEvent evt);
    }

    public void setLabelVisible(boolean value) {
        searchLabel.setVisible(value);
    }

    public PokemonGuruCardSearchFilter getFilter() {
        return new PokemonGuruCardSearchFilter(searchField.getText());
    }

    private JPanel wrapInBorderContainer(JComponent c) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(c, BorderLayout.CENTER);

        return panel;
    }

    private class AdvancedSearchDialog extends JDialog {
        private Consumer<CloseEvent> callback;

        private final JPanel boxContainer = new JPanel();
        private final BoxLayout boxLayout = new BoxLayout(boxContainer, BoxLayout.Y_AXIS);

        private final JPanel spacer = new JPanel();

        private final JPanel nameInputPanel = new JPanel(new BorderLayout());
        private final JTextField nameInputField = new JTextField();
        private final JTextField setIDField = new JTextField();
        private final JTextField typeField = new JTextField();

        private final JCheckBox basicCheckBox = new JCheckBox("Basic");
        private final JCheckBox exCheckBox = new JCheckBox("EX");
        private final JCheckBox megaCheckBox = new JCheckBox("Mega");
        private final JPanel subtypeBox = new JPanel(new GridLayout(1, 3));

        private final JButton saveButton = new JButton("Save");

        private final JPanel priceBox = new JPanel(new GridLayout(1, 2));
        private final JTextField minPriceField = new JTextField();
        private final JTextField maxPriceField = new JTextField();

        public AdvancedSearchDialog() {
            super(new JFrame(), "Advanced Search", true);

            boxContainer.setLayout(boxLayout);
            boxContainer.setBorder(new EmptyBorder(new Insets(12, 12, 12, 12)));

            spacer.setPreferredSize(new Dimension(400, 0));

            typeField.setToolTipText("""
                A space seperated list of all types to search for. The results will match any of the given types.
            """);

            subtypeBox.add(basicCheckBox);
            subtypeBox.add(exCheckBox);
            subtypeBox.add(megaCheckBox);
            basicCheckBox.setSelected(true);
            exCheckBox.setSelected(true);
            megaCheckBox.setSelected(true);

            boxContainer.add(spacer);
            boxContainer.add(initInputComponent("Name: ", nameInputField));
            boxContainer.add(initInputComponent("Types: ", typeField));
            boxContainer.add(subtypeBox);
            boxContainer.add(priceBox);
            boxContainer.add(initInputComponent("Set ID: ", setIDField));
            boxContainer.add(wrapInBorderContainer(saveButton));

            priceBox.add(initInputComponent("Min price", minPriceField));
            priceBox.add(initInputComponent("Max price", maxPriceField));

            saveButton.addActionListener(evt -> {
                fireCallback(new CloseEvent(
                        AdvancedSearchDialog.this,
                        getFilter()
                ));
                AdvancedSearchDialog.this.dispose();
            });

            add(boxContainer);

            pack();
        }

        public AdvancedSearchDialog(Consumer<CloseEvent> callback) {
            this();
            this.callback = callback;
        }

        public void displayDialog(JComponent origin) {
            setLocationRelativeTo(origin);
            setVisible(true);
        }

        public AdvancedSearchDialog setQuery(String query) {
            nameInputField.setText(query);
            return this;
        }

        private PokemonGuruCardSearchFilter getFilter() {
            PokemonGuruCardSearchFilter filter = new PokemonGuruCardSearchFilter(nameInputField.getText())
                    .addTypes(
                            Arrays.stream(typeField.getText().split(" "))
                                    .filter((String s) -> !s.isEmpty()).toList()
                    );

            if (basicCheckBox.isSelected()) {
                filter.addSubtype("Basic");
            }
            if (exCheckBox.isSelected()) {
                filter.addSubtype("EX");
            }
            if (megaCheckBox.isSelected()) {
                filter.addSubtype("MEGA");
            }

            try {
                if (!minPriceField.getText().isEmpty())
                    filter.setMinPrice(Double.valueOf(minPriceField.getText()));
            } catch (NumberFormatException e) {
            }

            try {
                if (!maxPriceField.getText().isEmpty())
                    filter.setMaxPrice(Double.valueOf(maxPriceField.getText()));
            } catch (NumberFormatException e) {
            }

            filter.setSetID(setIDField.getText());

            return filter;
        }

        private JPanel initInputComponent(String labelText, JComponent c) {
            JPanel containerPanel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(labelText);

            containerPanel.add(label, BorderLayout.WEST);
            containerPanel.add(c, BorderLayout.CENTER);

            return containerPanel;
        }

        public static class CloseEvent extends EventObject {
            public final PokemonGuruCardSearchFilter filter;
            public CloseEvent(Object source, PokemonGuruCardSearchFilter filter) {
                super(source);
                this.filter = filter;
            }
        }

        private void fireCallback(CloseEvent evt) {
            if (callback != null) {
                callback.accept(evt);
            }
        }
    }
}