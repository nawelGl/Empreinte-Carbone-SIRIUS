package edu.ezip.ing1.pds.front.UC3;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawSalesGraph extends JPanel {
    String[] months = {"2023-05", "2023-06", "2023-07", "2023-08", "2023-09", "2023-10", "2023-11", "2023-12", "2024-01", "2024-02", "2024-03", "2024-04"};


    private final ArrayList<Integer> listA;
    private final ArrayList<Integer> listB;
    private final ArrayList<Integer> listC;
    private final ArrayList<Integer> listD;
    private final ArrayList<Integer> listE;

    public DrawSalesGraph(ArrayList<Integer> listA, ArrayList<Integer> listB, ArrayList<Integer> listC, ArrayList<Integer> listD, ArrayList<Integer> listE) {
        this.listA = listA;
        this.listB = listB;
        this.listC = listC;
        this.listD = listD;
        this.listE = listE;

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
        // axis x : months
        String[] months = {"2023-05", "2023-06", "2023-07", "2023-08", "2023-09", "2023-10", "2023-11", "2023-12", "2024-01", "2024-02", "2024-03", "2024-04"};
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(months)));
        xAxis.setLabel("Month");

        // axis y: sales
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales");

        // generate graph
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Product sales by score");

        addSeries(lineChart, "A", listA, Color.DARKGREEN);
        addSeries(lineChart, "B", listB, Color.LIGHTGREEN);
        addSeries(lineChart, "C", listC, Color.YELLOW);
        addSeries(lineChart, "D", listD, Color.ORANGE);
        addSeries(lineChart, "E", listE, Color.RED);

        // add JavaFX Scene in JFXPanel
        Scene scene = new Scene(lineChart, 800, 600);
        fxPanel.setScene(scene);
        //Platform.exit();
    }

    private void addSeries(LineChart<String, Number> lineChart, String name, ArrayList<Integer> data, Color color) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);

        // add data point
        for (int i = 0; i < data.size(); i++) {
            series.getData().add(new XYChart.Data<>(months[i], data.get(i)));
        }
        lineChart.getData().add(series);

        // color decode
        series.getNode().setStyle("-fx-stroke: " + color.toString().replace("0x", "#") + ";");
    }
}
