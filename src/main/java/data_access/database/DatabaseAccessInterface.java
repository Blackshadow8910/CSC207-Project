package data_access.database;

import java.sql.ResultSet;

import usecase.app.inventory.InventoryDataAccessInterface;
import usecase.database.DeckDataAccessInterface;
import usecase.database.MarketplaceDataAccessInterface;
import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;

public interface DatabaseAccessInterface extends LoginDataAccessInterface, 
                                                 SignupDataAccessInterface, 
                                                 DeckDataAccessInterface, 
                                                 InventoryDataAccessInterface, 
                                                 MarketplaceDataAccessInterface {
}
