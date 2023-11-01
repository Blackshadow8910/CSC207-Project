package interface_adapters.login;

import javax.swing.JComponent;

import interface_adapters.ViewModel;
import view.LoginView;

public class LoginViewModel extends ViewModel{
    private String feedbackMessage;
    private String username;
    private String password;
    
    public LoginViewModel() {
        super("login");
    }
    
    public String getFeedbackMessage() {
        return feedbackMessage;
    }
    
    public void setFeedbackMessage(String message) {
        feedbackMessage = message;
        
        firePropertyChanged("feedbackMessage", null, feedbackMessage);
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        String oldValue = this.password;
        this.password = password;
        
        firePropertyChanged("password", oldValue, password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        String oldValue = this.username;
        this.username = username;

        firePropertyChanged("username", oldValue, username);
    }
}
