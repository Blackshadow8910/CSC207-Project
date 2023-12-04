package usecase.signup;

import data_access.database.UserAlreadyExistsException;
import entity.User;

public interface SignupDataAccessInterface {
    User getUser(String username);

    void registerUser(String username, String password) throws UserAlreadyExistsException;
}
