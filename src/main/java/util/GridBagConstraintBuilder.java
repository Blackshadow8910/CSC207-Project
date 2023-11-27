package util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagConstraintBuilder {
    private int gridx = 0;
    private int gridy = 0;
    private int gridwidth = 1;
    private int gridheight = 1;
    private double weightx = 0;
    private int weighty = 0;
    private int anchor = GridBagConstraints.CENTER;
    private Insets insets = new Insets(0, 0, 0, 0);
    private int fill = GridBagConstraints.BOTH;
    private int ipadx = 0;
    private int ipady = 0;

    public GridBagConstraintBuilder() {

    }

    public GridBagConstraintBuilder gridx(int value) {
        gridx = value;
        return this;
    }

    public GridBagConstraintBuilder gridy(int value) {
        gridy = value;
        return this;
    }

    public GridBagConstraintBuilder weightx(double value) {
        weightx = value;
        return this;
    }

    public GridBagConstraintBuilder weighty(int value) {
        weighty = value;
        return this;
    }

    public GridBagConstraintBuilder gridwidth(int value) {
        gridwidth = value;
        return this;
    }

    public GridBagConstraintBuilder gridheight(int value) {
        gridheight = value;
        return this;
    }

    public GridBagConstraintBuilder ipadx(int value) {
        weightx = value;
        return this;
    }

    public GridBagConstraintBuilder ipady(int value) {
        weightx = value;
        return this;
    }

    public GridBagConstraintBuilder anchor(int value) {
        anchor = value;
        return this;
    }

    public GridBagConstraintBuilder fill(int value) {
        fill = value;
        return this;
    }

    public GridBagConstraintBuilder insets(Insets value) {
        insets = value;
        return this;
    }

    public GridBagConstraints build() {
        return new GridBagConstraints(
            gridx, 
            gridy, 
            gridwidth, 
            gridheight, 
            weightx, 
            weighty, 
            anchor, 
            fill, 
            insets, 
            ipadx, 
            ipady);
    }

    public static GridBagConstraints constraint(int gridX, int gridY) {
        return new GridBagConstraintBuilder()
            .gridx(gridX)
            .gridy(gridY)
            .build();
    }

    public static GridBagConstraints constraint(int gridX, int gridY, int weightx, int weighty) {
        return new GridBagConstraintBuilder()
            .gridx(gridX)
            .gridy(gridY)
            .weightx(weightx)
            .weighty(weighty)
            .build();
    }
}
