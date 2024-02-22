package edu.ezip.ing1.pds.front;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

class RoundedPanel extends JPanel {
    private int arcWidth = 20;
    private int arcHeight = 20;

    public RoundedPanel(){

    }

    public RoundedPanel(int arcWidth, int arcHeight) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, arcWidth, arcHeight);

        g2d.clip(roundedRectangle);
        super.paintComponent(g2d);

        g2d.dispose();
    }
}