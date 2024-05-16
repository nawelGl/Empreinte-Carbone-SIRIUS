package edu.ezip.ing1.pds.front.UC3;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;



public class DrawBarChart extends JPanel {

    private final String[] labels;
    private final double[] values;
//    private Color[] colors = {Color.rgb(108, 229, 232), Color.rgb(65, 184, 213)};

    public DrawBarChart(String[] labels, double[] values) {
        this.labels = labels;
        this.values = values;


        // JavaFX Panel
        JFXPanel fxPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            initFX(fxPanel);
        });
    }

    private void initFX(JFXPanel fxPanel) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Before/After of our service");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < labels.length; i++) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(labels[i], values[i]);
            series.getData().add(data);
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart);
        fxPanel.setScene(scene);

    }

}
