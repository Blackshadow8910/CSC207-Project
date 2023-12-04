package usecase.login;

import entity.User;

public interface LoginDataAccessInterface {
    User getUser(String username, String password);
}
