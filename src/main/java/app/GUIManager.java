package app;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;

import entity.Card;
import usecase.PokemonTCGApiUseCase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIManager {
    public JFrame frame = new JFrame();
    public JPanel views = new JPanel(new CardLayout());
    public GUIManager() {
        // Sets the look and feel (theme) of swing
        setLaf(new FlatDarculaLaf());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(960, 540);
        frame.add(views);

        views.add(getMainJPanel());

        frame.getContentPane().add(views);
        frame.setVisible(true);
    }



    private void setLaf(FlatLaf laf) {
        FlatLaf.setup(laf);
    }

    private JPanel getMainJPanel() {
        PokemonTCGApiUseCase uc = new PokemonTCGApiUseCase();
        JPanel panel = new JPanel();
        TextField tf = new TextField();
        panel.add(tf);

        Button btn = new Button("Search");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Card> cards = uc.apiInteractor.searchCards(tf.getText());
                for (Card card : cards) {
                    System.out.println(card.name);
                }
            }});
        panel.add(btn);

        return panel;
    }
 }
