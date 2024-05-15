package edu.ezip.ing1.pds.front.UC3;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawSalesGraph extends JPanel {

    private final ArrayList listA;
    private final ArrayList listB;
    private final ArrayList listC;
    private final ArrayList listD;
    private final ArrayList listE;

    public DrawSalesGraph(ArrayList listA, ArrayList listB, ArrayList listC, ArrayList listD, ArrayList listE ) {
        this.listA=listA;
        this.listB=listB;
        this.listC=listC;
        this.listD=listD;
        this.listE=listE;

        initUI();
    }

    private void initUI() {
        // JavaFX를 위한 JFXPanel 생성
        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);

        // JavaFX 작업을 Swing 이벤트 디스패치 스레드 외부에서 실행
        Platform.runLater(() -> {
            initFX(fxPanel);
        });
    }

    private void initFX(JFXPanel fxPanel) {
        // axis X : months
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Month");

        // axis Y: sales
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales");

        // graphs
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Product sales by score");

        addSeries(lineChart, "A", listA);
        addSeries(lineChart, "B", listB);
        addSeries(lineChart, "C", listC);
        addSeries(lineChart, "D", listD);
        addSeries(lineChart, "E", listE);

        // add JavaFX Scene in JFXPanel
        Scene scene = new Scene(lineChart, 800, 600);
        fxPanel.setScene(scene);
    }


    private void addSeries(LineChart<Number, Number> lineChart, String name, ArrayList<Integer> data) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);

        for (int i = 0; i < data.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, data.get(i)));
        }
        lineChart.getData().add(series);
    }
}
