package usecase.signup;

import data_access.database.UserAlreadyExistsException;
import entity.User;

public interface SignupDataAccessInterface {
    public User getUser(String username);

    public void registerUser(String username, String password) throws UserAlreadyExistsException;
}
