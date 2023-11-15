package interface_adapters.app;

import javax.swing.JComponent;

import entity.User;
import interface_adapters.ViewModel;
import view.app.AppView;

public class AppViewModel extends ViewModel {
    public String currentTab;
    public User currentUser;

    public AppViewModel() {
        super("app");
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
}
