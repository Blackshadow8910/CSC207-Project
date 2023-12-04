package view.app;

import app.GUIManager;
import interface_adapters.app.AppController;
import interface_adapters.app.AppViewModel;
import util.GridBagConstraintBuilder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Where the bulk of the app will be; this will contain the ui for features after you have logged in
 */
public class AppView extends JPanel {
    private final AppViewModel appViewModel;
    private final AppController appController;
    private final GUIManager guiManager;

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
    private final JLabel tabLabel = new JLabel("");
    private final JLabel userLabel = new JLabel("User");
    private final JLabel userIconLabel = new JLabel(new ImageIcon());
    private final JPopupMenu userMenu = new JPopupMenu();
    private final JMenuItem logoutMenuItem = new JMenuItem("Log out");

    public AppView(AppViewModel appViewModel, AppController appController, GUIManager guiManager) {
        this.appViewModel = appViewModel;
        this.appController = appController;
        this.guiManager = guiManager;
        
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
        userIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tabLabel.setFont(headerFont);
        userLabel.setFont(headerFont);
        userMenu.add(logoutMenuItem);

        // Load Image

        try {
            Dimension d = new Dimension(40, 40);
            Image i = ImageIO.read(new File("resources/img/user-icon.png")).getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
            userIconLabel.setIcon(new ImageIcon(i));
            userIconLabel.setPreferredSize(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bind behaviours

        appViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("currentTab")) {
                    showTab(appViewModel.getCurrentTab());
                    tabLabel.setText(appViewModel.getCurrentTab());
                } else if (evt.getPropertyName().equals("currentUser")) {
                    if (evt.getNewValue() != null) {
                        userLabel.setText(appViewModel.currentUser.username);
                        //appViewModel.fireLoginListeners(new AppViewModel.LoginEvent(appController, appViewModel.currentUser));
                    }
                }
            }
        });

        userIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                userMenu.show(userIconLabel, 0, userIconLabel.getHeight());
            }
        });

        logoutMenuItem.addActionListener(evt -> {
            appViewModel.setCurrentUser(null);
            guiManager.showView("login");
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

        // Update viewmodel

        if (appViewModel.getCurrentTab() == null || !appViewModel.getCurrentTab().equals(tab)) {
            appViewModel.setTab(tab);
        }
    }

    public void addTab(String name, JComponent component) {
        JButton button = new JButton(name);

        button.setFocusable(false);
        button.addActionListener(evt -> {
            showTab(button.getText());
        });

        buttonPanel.add(button, tabButtonGBCBuilder.gridx(tabButtons.size()).build());

        tabButtons.add(button);
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
}
