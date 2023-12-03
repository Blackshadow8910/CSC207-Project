import data_access.image.ImageDataAccessInterface;
import data_access.pokemon.PokemonCardDataAccessInterface;
import entity.Card;
import interface_adapters.app.cardsearch.CardSearchPresenter;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import usecase.app.cardsearch.CardSearchInputData;
import usecase.app.cardsearch.CardSearchInteractor;
import usecase.app.cardsearch.CardSearchOutputBoundary;
import usecase.app.cardsearch.CardSearchOutputData;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CardSearchInteractorTest {

    @Mock
    private CardSearchOutputBoundary mockPresenter;

    @Mock
    private PokemonCardDataAccessInterface mockDataAccessObject;

    @Mock
    private ImageDataAccessInterface mockImageAccessObject;

    private CardSearchInteractor cardSearchInteractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cardSearchInteractor = new CardSearchInteractor(mockPresenter, mockDataAccessObject, mockImageAccessObject);
    }

    @Test
    public void testPerformSearch() {
        // Arrange
        CardSearchInputData inputData = new CardSearchInputData("pikachu");
        ArrayList<Card> mockResults = createMockCardList();
        when(mockDataAccessObject.searchCards("pikachu")).thenReturn(mockResults);

        // Act

        cardSearchInteractor.performSearch(inputData);

        //for (Card mockCard : mockResults) {
        //    when(mockImageAccessObject.getImage(mockCard.imageURL)).thenReturn(new ConcreteImage("MockedImageData"));
        //}
        // Assert
        verify(mockPresenter).present(any(CardSearchOutputData.class));

    }

    private ArrayList<Card> createMockCardList() {
        // Create a list of mock cards for testing
        ArrayList<Card> mockCards = new ArrayList<>();
        mockCards.add(new Card("Card1", "id", "ImageURL1"));
        mockCards.add(new Card("Card2", "id", "ImageURL2"));
        // Add more mock cards as needed
        return mockCards;
    }

}
