package data_access.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseAccessObject implements DatabaseAccessInterface {
    private static final String dbURL = "jdbc:postgresql://testy-walker-6038.g8z.cockroachlabs.cloud:26257/defaultdb";
    private static final String dbUsername = "Corknut";
    private static final String dbPassword = "HxHFA35PsK69vR0XQtADag";
    
    private Connection connection;

    public DatabaseAccessObject() {
        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Connected to the PostgreSQL server successfully.");

            connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
