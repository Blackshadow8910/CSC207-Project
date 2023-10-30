package data_access.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.User;


public class PostGreSQLAccessObject implements DatabaseAccessInterface {
    private static String dbURL;
    private static String dbUsername;
    private static String dbPassword;
    
    private Connection connection;

    public PostGreSQLAccessObject() {
        try {
            File f = new File("resources/pokemon-tcg-api-key.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            
            dbURL = reader.readLine();
            dbUsername = reader.readLine();
            dbPassword = reader.readLine();
            reader.close();

            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(String query) {
        try {
            Statement s = connection.createStatement();

            return s.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUser(String username) {
        ResultSet results = executeQuery("SELECT * FROM users WHERE username='%s'".formatted(username));
        try {
            if (results.next()) {
                User user = new User(username);
                
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    /**
     * Returns a user if the given credentials match a user.
     * 
     * Currently doesn't result in anything different from the other implementation of getUser, 
     * but it should give higher level access to the account done this way later.
     */
    public User getUser(String username, String password) {
        ResultSet results = executeQuery("SELECT * FROM users WHERE username='%s' AND password='%s'".formatted(username, password));
        try {
            if (results.next()) {
                User user = new User(username);
                
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
