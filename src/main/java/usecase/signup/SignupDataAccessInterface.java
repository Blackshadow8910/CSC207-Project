package usecase.signup;

import entity.User;

public interface SignupDataAccessInterface {
    public User getUser(String username, String password);
}
