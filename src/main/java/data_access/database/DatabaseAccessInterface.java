package data_access.database;

import java.sql.ResultSet;

import usecase.database.DeckDataAccessInterface;
import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;

public interface DatabaseAccessInterface extends LoginDataAccessInterface, SignupDataAccessInterface, DeckDataAccessInterface {
}
