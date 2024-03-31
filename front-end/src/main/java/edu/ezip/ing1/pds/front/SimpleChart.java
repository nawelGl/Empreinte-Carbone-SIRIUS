package edu.ezip.ing1.pds.front;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class SimpleChart {

    private BarChart<String, Number> barChart;

    public SimpleChart() {
        // X축과 Y축 생성
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Category");
        yAxis.setLabel("Value");

        // 막대 그래프 생성
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Sample Bar Chart");

        // 데이터 추가
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");
        series.getData().add(new XYChart.Data<>("A", 20));
        series.getData().add(new XYChart.Data<>("B", 30));
        series.getData().add(new XYChart.Data<>("C", 10));
        series.getData().add(new XYChart.Data<>("D", 40));

        // 막대 그래프에 데이터 추가
        barChart.getData().add(series);
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }
}



