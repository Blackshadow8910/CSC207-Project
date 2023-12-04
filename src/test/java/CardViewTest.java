import entity.Card;
import interface_adapters.app.cardsearch.CardSearchPresenter;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import org.junit.Test;
import usecase.app.cardsearch.CardDisplayData;
import usecase.app.cardsearch.CardSearchOutputData;
import view.app.CardView;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CardViewTest {

    @Test
    public void testDisplayResults() {
        CardView cardView = new CardView();
        List<CardDisplayData> results = createDummyCardDisplayData(10);

        cardView.displayResults(results);

        // Check if the displayed results match the expected size
        assertEquals(results.size(), cardView.getResultContainer().getComponentCount());
    }

    @Test
    public void testSelectListeners() {
        CardView cardView = new CardView();
        List<CardDisplayData> results = createDummyCardDisplayData(5);
        cardView.displayResults(results);

        TestSelectListener selectListener = new TestSelectListener();
        cardView.addSelectListener(selectListener);

        // Simulate a click on the first result entry
        cardView.fireSelectListeners(new CardView.SelectEvent(this, results.get(0)));

        // Check if the select listener was notified
        assertTrue(selectListener.onClickCalled);
        assertEquals(results.get(0).card, selectListener.selectedCard);
    }

    private List<CardDisplayData> createDummyCardDisplayData(int count) {
        List<CardDisplayData> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(new CardDisplayData(new Card("Card" + i, String.valueOf(i),
                    "imageURL1"), new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR)));
        }
        return results;
    }

    static class TestSelectListener implements CardView.SelectListener {
        boolean onClickCalled = false;
        Card selectedCard;

        @Override
        public void onClick(CardView.SelectEvent evt) {
            onClickCalled = true;
            selectedCard = evt.selectedCard;
        }
    }

    @Test
    public void testPresent() {
        // Arrange

        CardSearchOutputData outputData = new CardSearchOutputData();
        outputData.add(new Card("Card1", "id", "ImageURL1"), null);
        CardSearchPresenter presenter = new CardSearchPresenter(new CardSearchViewModel());

        // Act
        presenter.present(outputData);

        // Assert
        assertEquals(1, outputData.getResults().size());
    }
}
