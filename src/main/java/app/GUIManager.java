package app;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;

import entity.Card;
import usecase.PokemonTCGApiUseCase;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class GUIManager {
    public JFrame frame = new JFrame();
    private CardLayout cardLayout = new CardLayout(); 
    public JPanel views = new JPanel(cardLayout);

    public GUIManager() {
        // Sets the look and feel (theme) of swing
        setLaf(new FlatDarculaLaf());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(960, 540);
        frame.add(views);

        addView("login", new LoginView());

        frame.getContentPane().add(views);
        frame.setVisible(true);
        views.setSize(frame.getSize());

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                views.setSize(frame.getSize());
            }
        });
    }



    private void setLaf(FlatLaf laf) {
        FlatLaf.setup(laf);
    }

    private JPanel getMainJPanel() {
        JPanel panel = new JPanel();
        TextField tf = new TextField();
        panel.add(tf);

        Button btn = new Button("Search");
        panel.add(btn);

        return panel;
    }

    public void addView(String tag, Component c) {
        views.add(c, tag);
    }

    public void showView(String tag) {
        cardLayout.show(views, tag);
    }
 }
