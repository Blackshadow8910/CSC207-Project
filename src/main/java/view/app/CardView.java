package view.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import entity.Card;
import usecase.app.cardsearch.CardDisplayData;
import util.ImagePanel;

public class CardView extends JPanel {
    private final FlowLayout resultContainerLayout = new FlowLayout(FlowLayout.LEFT, 12, 12);
    private final JPanel resultContainer = new JPanel(resultContainerLayout);
    private final JScrollPane resultScrollPane = new JScrollPane(resultContainer);
    
    private final Dimension resultEntrySize = new Dimension(114, 160 + 20);

    private final HashSet<SelectListener> selectListeners = new HashSet<>();

    public CardView() {
        setLayout(new BorderLayout());

        resultScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resultScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(resultScrollPane, BorderLayout.CENTER);
    }

    public void displayResults(ArrayList<CardDisplayData> results) {
        resultContainer.removeAll();
        int number = 0;

        for (CardDisplayData result : results) {
            number++;
            JPanel entry = createResultEntry(result);
            resultContainer.add(entry);

            bindResultEntryListeners(entry, result);
        }
        int result = number / 5;

        resultContainer.revalidate();
        resultContainer.repaint();
        resultContainer.setPreferredSize(new Dimension(100, 200 + (result * 200)));
    }

    public JPanel createResultEntry(CardDisplayData data) {
        JPanel panel = new JPanel(new BorderLayout());

        ImagePanel imagePanel = new ImagePanel(data.image);
        JLabel textLabel = new JLabel(data.card.name);

        panel.add(imagePanel, BorderLayout.CENTER);
        panel.add(textLabel, BorderLayout.SOUTH);

        textLabel.setBackground(Color.BLUE);
        panel.setPreferredSize(resultEntrySize);
        
        // Hover stylings: Subject to change
        
        //panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //Border b = new MatteBorder(1, 1, 1, 1, Color.gray);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                //panel.setBorder(b);
                panel.setBackground(new Color(0, 0, 0, 50));
                resultContainer.repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                //panel.setBorder(null);
                panel.setBackground(null);
                resultContainer.repaint();
            }
        });
        
        return panel;
    }

    public void addSelectListener(SelectListener listener) {
        selectListeners.add(listener);
    }

    private void fireSelectListeners(SelectEvent evt) {
        for (SelectListener l : selectListeners) {
            l.onClick(evt);
        }
    }

    private void bindResultEntryListeners(JComponent entry, CardDisplayData data) {
        entry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                fireSelectListeners(new SelectEvent(this, data.card));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    interface SelectListener extends EventListener {
        public void onClick(SelectEvent evt);
    }

    public class SelectEvent extends EventObject {
        public final Card selectedCard;

        public SelectEvent(Object source, Card selectedCard) {
            super(source);

            this.selectedCard = selectedCard;
        }
    }
}
