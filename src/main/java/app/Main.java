package app;

import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data_access.pokemon.PokemonGuruDataAccessObject;
import entity.Message;
import org.json.JSONObject;
import org.json.JSONWriter;

import app.usecase_factories.*;
import data_access.database.DatabaseAccessInterface;
import data_access.database.PostGreSQLAccessObject;
import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.TestCardDataAccessObject;
import data_access.database.TestDatabaseAccessObject;
import data_access.image.ImageCacheAccessInterface;
import entity.Card;
import entity.Conversation;
import entity.SellListing;
import interface_adapters.app.AppController;
import interface_adapters.app.AppViewModel;
import interface_adapters.app.cardsearch.CardSearchViewModel;
import interface_adapters.app.deckbrowser.DeckBrowserViewModel;
import interface_adapters.app.deckbuilder.DeckBuilderViewModel;
import interface_adapters.app.inventory.InventoryViewModel;
import interface_adapters.app.trade.TradeViewModel;
import interface_adapters.login.LoginViewModel;
import interface_adapters.signup.SignupViewModel;
import view.LoginView;
import view.SignupView;
import view.app.AppView;
import view.app.CardSearchView;
import view.app.DeckBrowserView;
import view.app.DeckBuilderView;
import view.app.InventoryView;
import view.app.TradeView;

public class Main {
    public static void main(String[] args) {
        // Data access init

        PokemonCardDataAccessInterface pokemonCardDAO = new PokemonGuruDataAccessObject();//new TestCardDataAccessObject();
        ImageCacheAccessInterface imageDAO = (ImageCacheAccessInterface) pokemonCardDAO;
        DatabaseAccessInterface db = new TestDatabaseAccessObject(pokemonCardDAO);//new PostGreSQLAccessObject(pokemonCardDAO);

        // Other

        GUIManager guiManager = new GUIManager();

        CardSearchViewModel cardSearchViewModel = new CardSearchViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        AppViewModel appViewModel = new AppViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        TradeViewModel tradeViewModel = new TradeViewModel();
        DeckBuilderViewModel deckBuilderViewModel = new DeckBuilderViewModel();
        DeckBrowserViewModel deckBrowserViewModel = new DeckBrowserViewModel();

        AppView appView = AppUseCaseFactory.create(appViewModel, guiManager);
        TradeView tradeView = TradeUseCaseFactory.create(tradeViewModel, db, imageDAO, appViewModel, inventoryViewModel);
        InventoryView inventoryView = InventoryUseCaseFactory.create(inventoryViewModel, db, imageDAO, appViewModel, tradeViewModel);
        DeckBuilderView deckBuilderView = DeckBuilderUserCaseFactory.create(deckBuilderViewModel, appViewModel, db, pokemonCardDAO, imageDAO);
        DeckBrowserView deckBrowserView = DeckBuilderUserCaseFactory.createBrowser(deckBrowserViewModel, deckBuilderViewModel, db, pokemonCardDAO, imageDAO);
        CardSearchView cardSearchView = CardSearchUseCaseFactory.create(cardSearchViewModel, pokemonCardDAO, imageDAO);
        LoginView loginView = LoginUseCaseFactory.create(loginViewModel, appViewModel, db, guiManager);
        SignupView signupView = SignupUseCaseFactory.create(signupViewModel, loginViewModel, db, guiManager);

        appView.addTab("Card Search", cardSearchView);
        appView.addTab("Deck builder", deckBuilderView);
        appView.addTab("Deck browser", deckBrowserView);
        appView.addTab("My cards", inventoryView);
        appView.addTab("Trade cards", tradeView);

        guiManager.addView("login", loginView);
        guiManager.addView("app", appView);
        guiManager.addView("signup", signupView);

        // Misc testing whatever
    }
}
