package usecase.login;

import entity.User;

public interface LoginDataAccessInterface {
    public User getUser(String username, String password);
}
