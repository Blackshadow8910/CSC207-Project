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

import usecase.PokemonTCGApiUseCase;

public class Main {
    public static void main(String[] args) {
        // Data access init

        PokemonCardDataAccessInterface pokemonCardDAO = new TestCardDataAccessObject();
        ImageCacheAccessInterface imageDAO = (ImageCacheAccessInterface) pokemonCardDAO;
        DatabaseAccessInterface db = new TestDatabaseAccessObject();//new PostGreSQLAccessObject();


        // Usercase

        PokemonTCGApiUseCase pokemonTCGApiUseCase = new PokemonTCGApiUseCase(pokemonCardDAO);

        // Other

        GUIManager guiManager = new GUIManager();
        //Card c = pokemonTCGApiUseCase.apiInteractor.searchCards("mega lopunny").get(0);
        Card c = pokemonTCGApiUseCase.apiAccessObject.searchCards("Horse").get(0);
        System.out.println(c.name);

        JLabel jLabel = new JLabel(new ImageIcon(imageDAO.getImage(c.imageURL)));
        jLabel.setSize(245, 342);
        ((JPanel)(guiManager.views.getComponent(0))).add(jLabel);
        guiManager.views.repaint();
    }
}
