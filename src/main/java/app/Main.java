package app;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data_access.database.DatabaseAccessInterface;
import data_access.database.PostGreSQLAccessObject;
import data_access.pokemon.PokemonCardDataAccessInterface;
import data_access.pokemon.TestCardDataAccessObject;
import data_access.database.TestDatabaseAccessObject;
import data_access.image.ImageCacheAccessInterface;
import entity.Card;
import interface_adapters.app.AppController;
import interface_adapters.app.AppViewModel;
import interface_adapters.login.LoginViewModel;
import usecase.PokemonTCGApiUseCase;
import view.AppView;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // Data access init

        PokemonCardDataAccessInterface pokemonCardDAO = new TestCardDataAccessObject();
        ImageCacheAccessInterface imageDAO = (ImageCacheAccessInterface) pokemonCardDAO;
        DatabaseAccessInterface db = new TestDatabaseAccessObject();//new PostGreSQLAccessObject();


        // Use case

        PokemonTCGApiUseCase pokemonTCGApiUseCase = new PokemonTCGApiUseCase(pokemonCardDAO);

        // Other

        GUIManager guiManager = new GUIManager();

        LoginViewModel loginViewModel = new LoginViewModel();
        AppViewModel appViewModel = new AppViewModel();

        AppView appView = AppView.create(appViewModel);
        LoginView loginView = LoginView.create(loginViewModel, appViewModel, db, guiManager);
        
        guiManager.addView("login", loginView);
        guiManager.addView("app", appView);

        // Misc testing whatever

        Card c = pokemonTCGApiUseCase.apiAccessObject.searchCards("Horse").get(0);
        System.out.println(c.name);
    }
}
