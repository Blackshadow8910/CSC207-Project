package app.usecase_factories;



import view.CardSearchView;

import javax.swing.*;

import app.GUIManager;
import interface_adapters.app.cardsearch.CardSearchController;
import interface_adapters.app.cardsearch.CardSearchPresenter;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import usecase.app.cardsearch.CardSearchDataAccessInterface;
import usecase.app.cardsearch.CardSearchInputBoundary;
import usecase.app.cardsearch.CardSearchInteractor;
import usecase.app.cardsearch.CardSearchOutputBoundary;

import java.io.IOException;

public class CardSearchUseCaseFactory {

    /** Prevent instantiation. */
    private CardSearchUseCaseFactory() {}

    public static CardSearchView create(CardSearchViewModel cardSearchViewModel,
                                   CardSearchDataAccessInterface dataAccessObject) {

        CardSearchOutputBoundary cardSearchOutputBoundary = new CardSearchPresenter(cardSearchViewModel);
        CardSearchInputBoundary cardSearchInputBoundary = new CardSearchInteractor(cardSearchOutputBoundary);
        CardSearchController cardSearchController = new CardSearchController(cardSearchInputBoundary);

        return new CardSearchView(cardSearchViewModel, cardSearchController);

    }
}
