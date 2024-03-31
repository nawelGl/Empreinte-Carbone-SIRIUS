package edu.ezip.ing1.pds.front.UC3;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DrawChart extends JPanel {

    private final String[] labels;
    private final double[] values;
    private final Color[] colors;

    public DrawChart(String[] labels, double[] values, Color[] colors) {
        this.labels = labels;
        this.values = values;
        this.colors = colors;

        // JavaFX Panel 생성
        JFXPanel fxPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            initFX(fxPanel);
        });
    }

    private void initFX(JFXPanel fxPanel) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < labels.length; i++) {
            series.getData().add(new XYChart.Data<>(labels[i], values[i]));
            series.getData().get(i).getNode().setStyle("-fx-bar-fill: #" + Integer.toHexString(colors[i].getRGB()).substring(2));
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart);
        fxPanel.setScene(scene);
    }
}
