package view.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import app.GUIManager;
import entity.Card;
import entity.User;
import interface_adapters.app.AppController;
import interface_adapters.app.AppPresenter;
import interface_adapters.app.AppViewModel;
import usecase.app.AppInputBoundary;
import usecase.app.AppInteractor;
import usecase.app.AppOutputBoundary;
import util.GridBagConstraintBuilder;

/**
 * Where the bulk of the app will be; this will contain the ui for features after you have logged in
 */
public class AppView extends JPanel {
    private AppViewModel appViewModel;
    private AppController appController;

    private final ArrayList<LoginListener> loginListeners = new ArrayList<>();

    private final BorderLayout borderLayout = new BorderLayout();

    private final CardLayout tabs = new CardLayout();
    private final JPanel contentPanel = new JPanel(tabs);

    private final ArrayList<JButton> tabButtons = new ArrayList<>();

    private final ArrayList<JComponent> tabComponents = new ArrayList<>();

    private final JPanel buttonPanel = new JPanel();
    private final LayoutManager buttonPanelLayout = new GridBagLayout();
    private final GridBagConstraintBuilder tabButtonGBCBuilder = new GridBagConstraintBuilder()
            .weightx(1)
            .weighty(1);

    private final GridLayout headerPanelLayout = new GridLayout(1, 2);
    private final JPanel headerPanel = new JPanel(headerPanelLayout);
    private final JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));

    private final Font headerFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
    private final JLabel tabLabel = new JLabel("Card Viewer");
    private final JLabel userLabel = new JLabel("User");
    private final JLabel userIconLabel = new JLabel(new ImageIcon());

    public AppView(AppViewModel appViewModel, AppController appController) {
        this.appViewModel = appViewModel;
        this.appController = appController;
        
        // Setup buttons

        buttonPanel.setLayout(buttonPanelLayout);
        for (JButton button : tabButtons) {
            buttonPanel.add(button);
        }

        // Setup base UI

        headerPanel.add(headerLeft);
        headerPanel.add(headerRight);
        headerRight.add(userLabel);
        headerRight.add(userIconLabel);
        headerLeft.add(tabLabel);
        headerPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
        tabLabel.setFont(headerFont);
        userLabel.setFont(headerFont);

        // Load Image

        try {
            Dimension d = new Dimension(40, 40);
            Image i = ImageIO.read(new File("resources/img/user-icon.png")).getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
            userIconLabel.setIcon(new ImageIcon(i));
            userIconLabel.setPreferredSize(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Bind behaviours
        
        appViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("currentTab")) {
                    tabs.show(contentPanel, appViewModel.currentTab);
                    tabLabel.setText(appViewModel.currentTab);
                } else if (evt.getPropertyName().equals("currentUser")) {
                    userLabel.setText(appViewModel.currentUser.username);
                    fireLoginListeners(new LoginEvent(appController, appViewModel.currentUser));
                }
            }
        });

        // Finish setting UI

        setLayout(borderLayout);
        buttonPanel.setPreferredSize(new Dimension(0, 80));
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
        add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
    }

    public void showTab(String tab) {
        tabs.show(contentPanel, tab);
        tabLabel.setText(tab);

        // Disable the button for the new tab

        for (JButton button : tabButtons) {
            button.setEnabled(!button.getText().equals(tab));
        }
    }

    public void addTab(String name, JComponent component) {
        JButton button = new JButton(name);

        button.setFocusable(false);
        button.addActionListener(evt -> {
            showTab(button.getText());
        });

        buttonPanel.add(button, tabButtonGBCBuilder.gridx(tabButtons.size()).build());

        tabButtons.add(new JButton(name));
        tabComponents.add(component);
        contentPanel.add(component, name);

        if (tabLabel.getText().equals("")) {
            showTab(name);
        }
    }

    public JComponent getTabComponent(String label) {
        int i = 0;
        for (JButton button : tabButtons) {
            if (button.getText().equals(label)) {
                return tabComponents.get(i);
            }

            i++;
        }
        return null;
    }

    interface LoginListener extends EventListener {
        public void onLogin(LoginEvent evt);
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
