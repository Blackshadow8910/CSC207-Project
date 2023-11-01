package interface_adapters.signup;

import interface_adapters.ViewModel;

public class SignupViewModel extends ViewModel{
    private String feedbackMessage;
    private String username;
    private String password;
    
    public SignupViewModel() {
        //super("login");
    }
    
    public String getFeedbackMessage() {
        return feedbackMessage;
    }
    
    public void setFeedbackMessage(String message) {
        feedbackMessage = message;
        
        //firePropertyChanged("feedbackMessage", null, feedbackMessage);
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        String oldValue = this.password;
        this.password = password;
        
        //firePropertyChanged("password", oldValue, password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        String oldValue = this.username;
        this.username = username;

        //firePropertyChanged("username", oldValue, username);
    }
}
