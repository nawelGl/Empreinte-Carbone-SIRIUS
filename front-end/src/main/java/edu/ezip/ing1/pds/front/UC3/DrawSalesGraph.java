package edu.ezip.ing1.pds.front.UC3;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DrawSalesGraph extends JPanel {

    public DrawSalesGraph() {
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
        // X축 (월)
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Month");

        // Y축 (판매량)
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Sales");

        // 그래프
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Product Sales Trend");

        // 가상의 상품 판매량 데이터 생성 (예제에서는 generateData() 메서드를 사용)

        // 그래프 시리즈 생성 및 데이터 추가
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Product Sales Trend");
        // 데이터 추가 코드는 여기에 작성

        lineChart.getData().add(series);

        // JavaFX Scene을 JFXPanel에 추가
        Scene scene = new Scene(lineChart, 800, 600);
        fxPanel.setScene(scene);
    }

    // 가상의 상품 판매량 데이터 생성 메서드
    private Map<String, Integer[]> generateData() {
        Map<String, Integer[]> data = new HashMap<>();
        // 상품 1부터 5까지
        for (int i = 1; i <= 5; i++) {
            Integer[] sales = new Integer[13]; // 13개월 데이터 생성 (인덱스 0은 무시)
            // 가상의 판매량 데이터 생성 (임의로 설정)
            for (int j = 0; j < 13; j++) {
                sales[j] = (int) (Math.random() * 1000); // 0부터 1000까지의 랜덤한 값
            }
            data.put("Product " + i, sales);
        }
        return data;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Swing with JavaFX Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DrawSalesGraph(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DrawSalesGraph::createAndShowGUI);
    }
}
