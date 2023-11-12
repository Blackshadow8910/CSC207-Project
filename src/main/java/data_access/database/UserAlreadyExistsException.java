package data_access.database;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String user) {
        super("User %s already exists.".formatted(user));
    }
}
