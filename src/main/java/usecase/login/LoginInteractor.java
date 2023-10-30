package usecase.login;

import java.sql.ResultSet;

import data_access.database.DatabaseAccessInterface;

public class LoginInteractor implements LoginInputBoundary {
    public DatabaseAccessInterface databaseAccessObject;

    public LoginInteractor(DatabaseAccessInterface databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;
    }

    @Override
    public void login(LoginInputData inputData) {
        ResultSet results = databaseAccessObject.executeQuery("");
    }
}
