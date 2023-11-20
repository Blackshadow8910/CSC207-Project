package data_access.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Deck;
import entity.User;


public class PostGreSQLAccessObject implements DatabaseAccessInterface {
    private static String dbURL;
    private static String dbUsername;
    private static String dbPassword;
    
    private Connection connection;

    public PostGreSQLAccessObject() {
        try {
            File f = new File("resources/postgresql-api-key.txt");
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
    
    protected ResultSet executeQuery(String query) {
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
                User user = new User(username, results.getString("password"));
                
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
                User user = new User(username, results.getString("password"));
                
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerUser(String username, String password) throws UserAlreadyExistsException {
        if (getUser(username) != null) {
            throw new UserAlreadyExistsException("User");
        }

        //User user = new User(username, password);

        try {
            executeQuery("INSERT INTO users (username, password) VALUES ('%s', '%s');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Deck getDeck(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeck'");
    }

    @Override
    public void uploadDeck(Deck deck) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadDeck'");
    }
}
