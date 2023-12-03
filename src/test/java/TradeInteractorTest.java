import entity.Card;
import entity.Message;
import entity.SellListing;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usecase.app.trade.TradeDataAccessInterface;
import usecase.app.trade.TradeInteractor;
import usecase.app.trade.TradeOutputBoundary;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

public class TradeInteractorTest {

    @Mock
    private TradeOutputBoundary mockPresenter;

    @Mock
    private TradeDataAccessInterface mockDao;

    private TradeInteractor tradeInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tradeInteractor = new TradeInteractor(mockPresenter, mockDao);
    }

    @Test
    public void testUpdateSellListings() {
        // Arrange
        ArrayList<SellListing> mockSellListings = createMockSellListings();
        when(mockDao.getSellListings()).thenReturn(mockSellListings);

        // Act
        tradeInteractor.updateSellListings();

        // Assert
        verify(mockPresenter).presentSellListings(mockSellListings);
    }

    @Test
    public void testReplyToSellListing() {
        // Arrange SellListing(String id, Card card, User seller, double price)
        User mockUser = new User("MockUser", "123456");
        User mockUser2 = new User("MockUser2", "12345678");
        Card mockCard = new Card("MockCard", "1", "ImageURL1");
        SellListing mockListing = new SellListing("id", mockCard, mockUser, 10.0);
        Message mockMessage = new Message(mockUser2,"Interested in buying", new Date());

        // Act
        tradeInteractor.replyToSellListing(mockListing, mockMessage);

        // Assert
        verify(mockDao).replyToSellListing(eq(mockListing.id), eq(mockMessage));
    }

    private ArrayList<SellListing> createMockSellListings() {
        // Create a list of mock sell listings for testing
        Card mockCard = new Card("MockCard", "1", "ImageURL1");
        Card mockCard2 = new Card("MockCard2", "2", "ImageURL2");
        User mockUser = new User("MockUser", "123456");
        ArrayList<SellListing> mockSellListings = new ArrayList<>();
        mockSellListings.add(new SellListing("id", mockCard, mockUser, 10.0));
        mockSellListings.add(new SellListing("id", mockCard2, mockUser, 12.0));
        // Add more mock sell listings as needed
        return mockSellListings;
    }
}