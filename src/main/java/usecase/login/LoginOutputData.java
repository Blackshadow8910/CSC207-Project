package usecase.login;

import entity.User;

public class LoginOutputData {
    public final User user;

    public LoginOutputData(User user) {
        this.user = user;
    }
}
