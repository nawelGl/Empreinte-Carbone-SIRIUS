package edu.ezip.ing1.pds.front;


import javax.swing.*;
import java.awt.*;

public class StatTest extends JPanel {

    private final String title;
    private final String[] labels;
    private final double[] values;
    private final Color[] colors;

    public StatTest(String title, String[] labels, double[] values, Color[] colors) {
        this.title = title;
        this.labels = labels;
        this.values = values;
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth()/2;
        int height = getHeight();
        int barWidth = width / values.length;

        // Draw title
        g.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics titleMetrics = g.getFontMetrics();
        int titleWidth = titleMetrics.stringWidth(title);
        g.drawString(title, (width - titleWidth) / 2, titleMetrics.getAscent());

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

            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, barHeight);

            // Draw label
            g.drawString(labels[i], x + (barWidth - g.getFontMetrics().stringWidth(labels[i])) / 2, height - 5);
        }
    }
//
//    public static void main(String[] args) {
//        String title = "Bar Chart Example";
//        String[] labels = {"Avant", "Après"};
//        double[] values = {100, 200};
//        Color[] colors = {Color.RED, Color.GREEN};
//
//        StatTest barChart = new StatTest(title, labels, values, colors);
//
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 700);
//
//        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setPreferredSize(new Dimension(500,400));
//        mainPanel.add(BorderLayout.CENTER,barChart);
//
//        frame.getContentPane().add(mainPanel);
//        frame.setVisible(true);
//    }
}
