package view.app;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import usecase.app.cardsearch.CardSearchInputData;
import util.GridBagConstraintBuilder;

public class CardSearchBarView extends JPanel {
    private final LayoutManager searchPanelLayout = new GridBagLayout();
    private final Border searchPanelBorder = new EmptyBorder(0, 8, 0, 0);
    private final JTextField searchField = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final JButton advancedSearchButton = new JButton("Advanced");

    private final HashSet<SearchListener> searchListeners = new HashSet<>();

    public CardSearchBarView() {
        setBorder(searchPanelBorder);
        setLayout(searchPanelLayout);

        searchButton.setFocusable(false);
        advancedSearchButton.setFocusable(false);

        add(new JLabel("Search keywords: "));
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


    public class SearchEvent extends EventObject {
        public final CardSearchInputData data;

        public SearchEvent(Object source, CardSearchInputData data) {
            super(source);

            this.data = data;
        }
    }

    public interface SearchListener extends EventListener {
        public void onSearch(SearchEvent evt);
    }
}