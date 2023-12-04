import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Deck;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usecase.app.deckbrowser.DeckBrowserDataAccessInterface;
import usecase.app.deckbrowser.DeckBrowserInteractor;
import usecase.app.deckbrowser.DeckBrowserOutputBoundary;
import usecase.app.deckbrowser.DeckSearchFilter;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class DeckBrowserInteractorTest {

    @Mock
    private DeckBrowserOutputBoundary mockPresenter;

    @Mock
    private DeckBrowserDataAccessInterface mockDao;

    @Mock
    private ImageDataAccessInterface mockImageDao;

    private DeckBrowserInteractor deckBrowserInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        deckBrowserInteractor = new DeckBrowserInteractor(mockPresenter, mockDao, mock(PokemonCardDataAccessInterface.class), mockImageDao);
    }

    @Test
    public void testDisplayDecks() {
        // Arrange
        DeckSearchFilter filter = new DeckSearchFilter();
        ArrayList<Deck> mockDecks = createMockDeckList();
        when(mockDao.getDecks(filter)).thenReturn(mockDecks);
        when(mockImageDao.getImage("img/deck-icon.png")).thenReturn(any());

        // Act
        deckBrowserInteractor.displayDecks(filter);

        // Assert
        verify(mockPresenter).setDefaultDeckImage(any());
        verify(mockPresenter).presentDecks(mockDecks);
    }

    @Test
    public void testDisplayDecksDefault() {
        // Arrange
        ArrayList<Deck> mockDecks = createMockDeckList();
        when(mockDao.getDecks(any(DeckSearchFilter.class))).thenReturn(mockDecks);
        when(mockImageDao.getImage("img/deck-icon.png")).thenReturn(any());

        // Act
        deckBrowserInteractor.displayDecks();

        // Assert
        verify(mockPresenter).setDefaultDeckImage(any());
        verify(mockPresenter).presentDecks(mockDecks);
    }

    private ArrayList<Deck> createMockDeckList() {
        // Create a list of mock decks for testing
        ArrayList<Deck> mockDecks = new ArrayList<>();
        mockDecks.add(new Deck("Deck1", "1", "Tom"));
        mockDecks.add(new Deck("Deck2", "2", "Jerry"));
        // Add more mock decks as needed
        return mockDecks;
    }

    // Assuming there's an Image class in your code
    private static class Image {
        private final String imageData;

        public Image(String imageData) {
            this.imageData = imageData;
        }

        // Implement methods of the Image class as needed
    }
}