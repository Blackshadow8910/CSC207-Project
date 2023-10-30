package data_access.database;

import java.sql.ResultSet;
import java.util.ArrayList;

import entity.Card;
import entity.User;

public class TestDatabaseAccessObject implements DatabaseAccessInterface {

    public ArrayList<User> users = new ArrayList<>();

    public TestDatabaseAccessObject() {
        users.add(new User("bob", "123456"));
        users.add(new User("steven", "654321"));
    }

    @Override
    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username)) {
                if (user.password.equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    } 
}
