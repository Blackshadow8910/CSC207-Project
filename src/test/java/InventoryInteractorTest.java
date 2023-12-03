import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.ArrayListCardDataAccessObject;
import data_access.pokemon.PokemonGuruCardSearchFilter;
import entity.Card;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usecase.app.cardsearch.CardDisplayData;
import usecase.app.inventory.InventoryDataAccessInterface;
import usecase.app.inventory.InventoryInteractor;
import usecase.app.inventory.InventoryOutputBoundary;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class InventoryInteractorTest {

    @Mock
    private InventoryOutputBoundary mockPresenter;

    @Mock
    private InventoryDataAccessInterface mockDao;

    @Mock
    private ImageDataAccessInterface mockImageDao;

    private InventoryInteractor inventoryInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        inventoryInteractor = new InventoryInteractor(mockPresenter, mockDao, mockImageDao);
    }

    @Test
    public void testDisplayInventory() {
        // Arrange
        User mockUser = new User("MockUser", "123456");
        ArrayListCardDataAccessObject inventoryDAO = new ArrayListCardDataAccessObject(new ArrayList<>(mockUser.getOwnedCards()));
        PokemonGuruCardSearchFilter filter = new PokemonGuruCardSearchFilter();
        ArrayList<Card> mockCards = inventoryDAO.searchCards(filter);
        ArrayList<CardDisplayData> mockCardsData = new ArrayList<>();


        for (Card mockCard : mockCards) {
            when(mockImageDao.getImage(mockCard.imageURL)).thenReturn(mock(BufferedImage.class));
            mockCardsData.add(new CardDisplayData(mockCard, mock(BufferedImage.class)));
        }

        // Act
        inventoryInteractor.displayInventory(mockUser, filter);

        // Assert
        verify(mockPresenter).presentInventory(mockCardsData);
    }

    @Test
    public void testRemoveCard() {
        // Arrange
        User mockUser = new User("MockUser", "123456");
        Card mockCard = new Card("MockCard", "1", "ImageURL1");
        mockUser.addOwnedCard(mockCard);

        // Act
        inventoryInteractor.removeCard(mockUser, mockCard);

        // Assert
        verify(mockPresenter, never()).presentInventory(any());
        assert !mockUser.getOwnedCards().contains(mockCard);
    }

    private ArrayList<Card> createMockCardList() {
        // Create a list of mock cards for testing
        ArrayList<Card> mockCards = new ArrayList<>();
        mockCards.add(new Card("Card1", "1","ImageURL1"));
        mockCards.add(new Card("Card2", "2", "ImageURL2"));
        // Add more mock cards as needed
        return mockCards;
    }
}