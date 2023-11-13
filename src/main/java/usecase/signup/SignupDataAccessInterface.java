package usecase.signup;

import entity.User;

public interface SignupDataAccessInterface {
    public User getUser(String username);

    public void registerUser(String username, String password);
}
