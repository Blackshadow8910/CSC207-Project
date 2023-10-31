package interface_adapters.app;

import javax.swing.JComponent;

import interface_adapters.ViewModel;
import view.AppView;

public class AppViewModel extends ViewModel {
    public String currentTab;

    public AppViewModel() {
        super("app");
    }

    public void setTab(String tab) {
        String oldValue = currentTab;
        currentTab = tab;
        
        firePropertyChanged("currentTab", oldValue, currentTab);
    }
}
