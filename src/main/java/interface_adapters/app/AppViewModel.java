package interface_adapters.app;

import entity.User;
import interface_adapters.ViewModel;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

public class AppViewModel extends ViewModel {
    public String currentTab;
    public User currentUser;

    private final ArrayList<LoginListener> loginListeners = new ArrayList<>();

    public AppViewModel() {
        super("app");

        addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("currentUser")) {
                if (currentUser != null) {
                    fireLoginListeners(new LoginEvent(this, currentUser));
                }
            }
        });
    }

    public void setTab(String tab) {
        String oldValue = currentTab;
        currentTab = tab;
        
        firePropertyChanged("currentTab", oldValue, currentTab);
    }

    public void setCurrentUser(User user) {
        User oldUser = currentUser;
        currentUser = user;

        firePropertyChanged("currentUser", oldUser, currentUser);
    }

    

    public interface LoginListener extends EventListener {
        void onLogin(LoginEvent evt);
    }

    public class LoginEvent extends EventObject {
        public final User user;

        public LoginEvent(Object source, User user) {
            super(source);

            this.user = user;
        }
    }

    public void addLoginListener(LoginListener listener) {
        loginListeners.add(listener);
    }

    private void fireLoginListeners(LoginEvent evt) {
        for (LoginListener loginListener : loginListeners) {
            loginListener.onLogin(evt);
        }
    }
}
