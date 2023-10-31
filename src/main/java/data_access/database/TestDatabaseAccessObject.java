package data_access.database;

import java.sql.ResultSet;
import java.util.ArrayList;

import entity.Card;
import entity.User;

public class TestDatabaseAccessObject implements DatabaseAccessInterface {

    public ArrayList<User> users = new ArrayList<>();

    public TestDatabaseAccessObject() {
        try {
            registerUser("bob", "123456");
            registerUser("steven", "654321");
        } catch (UserAlreadyExistsException e) {
        }
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

    public void registerUser(String username, String password) throws UserAlreadyExistsException {
        if (getUser(username) != null) {
            throw new UserAlreadyExistsException(username);
        }

        users.add(new User(username, password));
    }
}
