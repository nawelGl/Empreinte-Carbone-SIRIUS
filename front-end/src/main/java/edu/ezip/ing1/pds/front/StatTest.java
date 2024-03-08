package edu.ezip.ing1.pds.front;//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//
//public class StatistiqueGraphique extends JFrame {
//
//    public StatistiqueGraphique(String title) {
//        super(title);
//
//        // Création du jeu de données
//        CategoryDataset dataset = createDataset();
//
//        // Création du graphique
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Exemple de graphique en barres",
//                "Catégorie",
//                "Valeur",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//
//        // Ajout du graphique à un panneau Swing
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
//        setContentPane(chartPanel);
//    }
//
//    private CategoryDataset createDataset() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        // Ajout de données fictives
//        dataset.addValue(10, "Série 1", "Catégorie 1");
//        dataset.addValue(15, "Série 1", "Catégorie 2");
//        dataset.addValue(20, "Série 1", "Catégorie 3");
//
//        return dataset;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            StatistiqueGraphique example = new StatistiqueGraphique("Graphique en barres avec JFreeChart");
//            example.setSize(800, 600);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            example.setVisible(true);
//        });
//    }
//}
