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


//TODO: resoudre le pb de couleur
public class DrawBarChart extends JPanel {

    private final String[] labels;
    private final double[] values;
//    private Color[] colors = {Color.rgb(108, 229, 232), Color.rgb(65, 184, 213)};

    public DrawBarChart(String[] labels, double[] values) {
        this.labels = labels;
        this.values = values;
//        this.colors = colors;

        // JavaFX Panel 생성
        JFXPanel fxPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
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
            XYChart.Data<String, Number> data = new XYChart.Data<>(labels[i], values[i]);
            series.getData().add(data);


            //-----------------------------------------------

            // initialisation des nodes
            Node node = data.getNode();
            if (node != null) {
                if (i == 0) {
                    // 첫 번째 막대를 하늘색으로 설정
                    node.setStyle("-fx-background-color: #6CE5E8;");
                } else if (i == 1) {
                    // 두 번째 막대를 파란색으로 설정
                    node.setStyle("-fx-background-color: #41B8D5;");
                }
            }
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart);
        fxPanel.setScene(scene);

    }

}
