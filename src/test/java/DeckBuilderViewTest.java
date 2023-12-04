import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import entity.Card;
import entity.Deck;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.deckbuilder.DeckBuilderController;
import interface_adapters.app.deckbuilder.DeckBuilderViewModel;
import usecase.app.cardsearch.CardDisplayData;
import view.app.CardView;
import view.app.DeckBuilderView;

public class DeckBuilderViewTest {

    private DeckBuilderViewModel viewModel;
    private DeckBuilderController controller;
    private AppViewModel appViewModel;
    private DeckBuilderView deckBuilderView;

    @Before
    public void setUp() {
        viewModel = new DeckBuilderViewModel();
        controller = mock(DeckBuilderController.class);
        appViewModel = mock(AppViewModel.class);
        deckBuilderView = new DeckBuilderView(viewModel, controller, appViewModel);
        when(appViewModel.getCurrentTab()).thenReturn("Deck browser");
    }

    @Test
    public void testShowSubCardView() {
        deckBuilderView.showSubCardView("Add");

        assertEquals("Add", viewModel.getSubCardView());
        deckBuilderView.showSubCardView("Remove");
        assertEquals("Remove", viewModel.getSubCardView());
    }

    @Test
    public void testDisplayDeckData() {
        Deck deck = new Deck("Test Deck", "1", "John Doe");
        when(appViewModel.getCurrentTab()).thenReturn("Deck browser");
        deckBuilderView.displayDeckData(deck);
        Card card = new Card("Card1", "1", "imageURL1");
        viewModel.addToDeck(card);

        assertNotNull(deckBuilderView.getActiveSubCardView());
        assertNotNull(deckBuilderView.getActiveSubCardView().getResultContainer());
    }

    @Test
    public void testAddSearchBarPanel() {
        // Assuming search listener is properly attached
        viewModel.setSubCardView("Add");
        deckBuilderView.getSearchBarPanel().getSearchButton().doClick();
        verify(controller).search(any());

    }

    @Test
    public void testSearchBarPanel(){
        viewModel.setSubCardView("Remove");
        String name = deckBuilderView.getSearchBarPanel().getSearchButton().getText();
        assertEquals(name, "Search");
    }

    @Test
    public void testAddCardView() {
        CardDisplayData cardDisplayData = new CardDisplayData(new Card("TestCard", "1", "imageURL"), null);
        deckBuilderView.getAddCardView().fireSelectListeners(new CardView.SelectEvent(this, cardDisplayData));
        assertNotNull(viewModel.getDeck());
    }

    @Test
    public void testRemoveCardView() {
        CardDisplayData cardDisplayData = new CardDisplayData(new Card("TestCard", "1", "imageURL"), null);
        deckBuilderView.getRemoveCardView().fireSelectListeners(new CardView.SelectEvent(this, cardDisplayData));
        verify(controller).search(any());
        assertNotNull(viewModel.getDeck());
    }

    @Test
    public void testSave() {
        CardDisplayData cardDisplayData = new CardDisplayData(new Card("TestCard", "1", "imageURL"), null);
        Deck deck = new Deck("Test Deck", "1", "John Doe");
        viewModel.setDeck(deck);
        deckBuilderView.getRemoveCardView().fireSelectListeners(new CardView.SelectEvent(this, cardDisplayData));
        deckBuilderView.getInfoPanel().getSaveButton().doClick();
        assertNotNull(viewModel.getDeck());
    }
}