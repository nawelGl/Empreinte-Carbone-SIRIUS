package edu.ezip.ing1.pds.front;


import javax.swing.*;
import java.awt.*;

public class DrawChart extends JPanel {

    //private final String title;
    private final String[] labels;
    private final double[] values;
    private final Color[] colors;

    public DrawChart(String[] labels, double[] values, Color[] colors) {
        this.labels = labels;
        this.values = values;
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / values.length;


        // Calculate maximum value
        double maxValue = 0;
        for (double value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        // Draw bars
        for (int i = 0; i < values.length; i++) {
            int x = i * barWidth;
            int barHeight = (int) (values[i] / maxValue * height);
            int y = height - barHeight;

            g.setColor(colors[i]);
            g.fillRect(x, y, barWidth, barHeight);

            g.setColor(Color.WHITE);
            g.drawRect(x, y, barWidth, barHeight);

            // Draw label
            g.drawString(labels[i], x + (barWidth - g.getFontMetrics().stringWidth(labels[i])) / 4, height - 5);
        }
    }

}
