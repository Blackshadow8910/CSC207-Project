package usecase.signup;

public class SignupInputData {
    private String username;
    private String password;
    public SignupInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
