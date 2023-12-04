import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Card;
import entity.Deck;
import entity.PokemonGuruCardSearchFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usecase.app.deckbuilder.DeckBuilderDataAccessInterface;
import usecase.app.deckbuilder.DeckBuilderInputData;
import usecase.app.deckbuilder.DeckBuilderInteractor;
import usecase.app.deckbuilder.DeckBuilderOutputBoundary;

import java.awt.Image;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class DeckBuilderInteractorTest {

    @Mock
    private DeckBuilderOutputBoundary mockPresenter;

    @Mock
    private DeckBuilderDataAccessInterface mockDao;

    @Mock
    private PokemonCardDataAccessInterface mockPokemonDao;

    @Mock
    private ImageDataAccessInterface mockImageDao;

    private DeckBuilderInteractor deckBuilderInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        deckBuilderInteractor = new DeckBuilderInteractor(mockPresenter, mockDao, mockPokemonDao, mockImageDao);
    }

    @Test
    public void testSearchForAdd() {
        // Arrange
        PokemonGuruCardSearchFilter filter = new PokemonGuruCardSearchFilter();
        ArrayList<Card> mockCards = createMockCardList();
        when(mockPokemonDao.searchCards(filter)).thenReturn(mockCards);

        for (Card mockCard : mockCards) {
            when(mockImageDao.getImage(mockCard.imageURL)).thenReturn(any());
        }

        // Act
        deckBuilderInteractor.search(new DeckBuilderInputData(filter, DeckBuilderInputData.ADD , null));

        // Assert
        verify(mockPresenter).presentSearchResults(any());
    }

    @Test
    public void testSearchForRemove() {
        // Arrange
        PokemonGuruCardSearchFilter filter = new PokemonGuruCardSearchFilter();
        Deck searchDeck = new Deck("SearchDeck", "1", "Justin");
        ArrayList<Card> mockCards = createMockCardList();
        when(mockDao.getDeck("SearchDeck")).thenReturn(any());

        for (Card mockCard : mockCards) {
            when(mockImageDao.getImage(mockCard.imageURL)).thenReturn(any());
        }

        // Act
        deckBuilderInteractor.search(new DeckBuilderInputData(filter, DeckBuilderInputData.REMOVE, searchDeck));

        // Assert
        verify(mockPresenter).presentSearchResults(any());
    }

    @Test
    public void testSaveDeck() {
        // Arrange
        Deck mockDeck = new Deck("MockDeck", "2", "Arther");

        // Act
        deckBuilderInteractor.saveDeck(mockDeck);

        // Assert
        verify(mockDao).uploadDeck(mockDeck);
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