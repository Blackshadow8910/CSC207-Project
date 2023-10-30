package data_access.database;

import java.sql.ResultSet;

import usecase.login.LoginDataAccessInterface;
import usecase.signup.SignupDataAccessInterface;

public interface DatabaseAccessInterface extends LoginDataAccessInterface, SignupDataAccessInterface {
    //public ResultSet executeQuery(String query);
}
