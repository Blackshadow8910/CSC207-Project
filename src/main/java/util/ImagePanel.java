package util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    private Image image;
    private Insets padding = new Insets(0, 0, 0, 0);

    public ImagePanel(Image image) {
        this.image = image;
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    public void setPadding(Insets padding) {
        this.padding = padding;
        repaint();
    } 

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth() - padding.left - padding.right;
        int height = getHeight() - padding.top - padding.bottom;
        g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), padding.left, padding.top, null);
    }
}
