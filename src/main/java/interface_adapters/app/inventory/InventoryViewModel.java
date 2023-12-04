package interface_adapters.app.inventory;

import entity.User;
import interface_adapters.ViewModel;
import usecase.app.cardsearch.CardDisplayData;

import java.util.ArrayList;
import java.util.List;

public class InventoryViewModel extends ViewModel{
    private ArrayList<CardDisplayData> currentResults = new ArrayList<>();
    private User currentUser;

    public InventoryViewModel() {
        super("inventory");
    }

    public List<CardDisplayData> getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(ArrayList<CardDisplayData> results) {
        Object oldValue = currentResults;
        currentResults = results;

        firePropertyChanged("currentResults", oldValue, results);
    }

    public User getCurrentUser() {
        return currentUser;
    }
 
    public void setCurrentUser(User user) {
        Object oldValue = currentUser;
        currentUser = user;

        firePropertyChanged("currentUser", oldValue, currentUser);
    }
}
