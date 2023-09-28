import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import java.awt.*;

public class GUIManager {
    public JFrame frame = new JFrame();
    public JPanel views = new JPanel(new CardLayout());
    public GUIManager() {
        // Sets the look and feel (theme) of swing
        setLaf(new FlatDarculaLaf());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(960, 540);
        frame.add(views);

        views.add(new JPanel());

        frame.getContentPane().add(views);
        frame.setVisible(true);
    }



    private void setLaf(FlatLaf laf) {
        FlatLaf.setup(laf);
    }
 }
