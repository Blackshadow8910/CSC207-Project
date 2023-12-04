import entity.PokemonGuruCardSearchFilter;
import org.junit.Before;
import org.junit.Test;
import usecase.app.cardsearch.CardSearchInputData;
import view.app.CardSearchBarView;

import static org.junit.Assert.*;

public class CardSearchBarViewTest {

    private CardSearchBarView searchBarView;
    private MockSearchListener mockSearchListener;

    @Before
    public void setUp() {
        searchBarView = new CardSearchBarView();
        mockSearchListener = new MockSearchListener();
        searchBarView.addSearchListener(mockSearchListener);
    }

    @Test
    public void testSearchButtonActionPerformed() {
        searchBarView.getSearchButton().doClick();
        assertTrue(mockSearchListener.isSearchEventFired());
    }

    @Test
    public void testAdvancedSearchButtonActionPerformed() {
        searchBarView.advancedSearchButton.doClick();
        assertTrue(mockSearchListener.isSearchEventFired());
    }

    @Test
    public void testSearchEvent() {
        searchBarView.fireSearchEvent(new CardSearchBarView.SearchEvent(searchBarView, new CardSearchInputData("Test")));
        assertTrue(mockSearchListener.isSearchEventFired());
    }

    @Test
    public void testGetFilter() {
        searchBarView.searchField.setText("Test");
        PokemonGuruCardSearchFilter filter = searchBarView.getFilter();

        assertEquals("supertype:Pokémon name:\"Test\"", filter.getQuery());
    }

    // Add more test cases based on your specific requirements

    private static class MockSearchListener implements CardSearchBarView.SearchListener {
        private boolean searchEventFired = false;

        @Override
        public void onSearch(CardSearchBarView.SearchEvent evt) {
            searchEventFired = true;
        }

        public boolean isSearchEventFired() {
            return searchEventFired;
        }
    }
}