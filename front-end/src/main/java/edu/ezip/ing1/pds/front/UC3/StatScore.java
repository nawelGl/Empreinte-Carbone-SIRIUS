package edu.ezip.ing1.pds.front.UC3;



import edu.ezip.ing1.pds.business.dto.VenteScore;
import edu.ezip.ing1.pds.business.dto.VenteScores;
import edu.ezip.ing1.pds.client.UC3.SelectVenteByScore;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.Template;
import javafx.application.Platform;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class StatScore extends JFrame implements ActionListener {

    JFrame scoreFrame;
    static VenteScores VentesA;
    static VenteScores VentesB;
    static VenteScores VentesC;
    static VenteScores VentesD;
    static VenteScores VentesE;
    JButton exportBtt;
    static ArrayList<Integer> listA;
    static ArrayList<Integer> listB;
    static ArrayList<Integer> listC;
    static ArrayList<Integer> listD;
    static ArrayList<Integer> listE;
    static VenteScores venteScores;


    public StatScore() {

        try {
            Request request = new Request();
            request.setRequestContent("A");
            VentesA= SelectVenteByScore.launchSelectVenteByScore(request);
            request.setRequestContent("B");
            VentesB= SelectVenteByScore.launchSelectVenteByScore(request);
            request.setRequestContent("C");
            VentesC= SelectVenteByScore.launchSelectVenteByScore(request);
            request.setRequestContent("D");
            VentesD= SelectVenteByScore.launchSelectVenteByScore(request);
            request.setRequestContent("E");
            VentesE= SelectVenteByScore.launchSelectVenteByScore(request);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<String> dateList = new ArrayList<>(Arrays.asList("2023-05", "2023-06","2023-07","2023-08",
                "2023-09","2023-10","2023-11","2023-12","2024-01","2024-02","2024-03","2024-04"));



        listA = generateSalesList(VentesA, dateList);
        listB = generateSalesList(VentesB, dateList);
        listC = generateSalesList(VentesC, dateList);
        listD = generateSalesList(VentesD, dateList);
        listE = generateSalesList(VentesE, dateList);




        // installation de frame
        scoreFrame = new JFrame("Statisque - Stat par score");
        scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scoreFrame.setResizable(false);
        scoreFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        scoreFrame.setLocationRelativeTo(null);


        //-------------------panel header-------------
        MethodesFront.header(scoreFrame,"Ventes par score des produits",525);


        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));


        //chartPanel setting
        DrawSalesGraph chartPanel = new DrawSalesGraph(listA,listB,listC,listD,listE);
        chartPanel.setBackground(Color.white);
        chartPanel.setBounds(350,50,800,600);

        //-------------------ajout de bouton export
        exportBtt=new JButton("Exporter les data");
        exportBtt.setBackground(Color.white);
        exportBtt.addActionListener( this);
        exportBtt.setBounds(1250,30,140,30);

        //------------back btt

        ImageIcon backIcon= new ImageIcon(Objects.requireNonNull(MethodesFront.class.getResource("/back.png")));
        JButton backButton=new JButton(backIcon);


        backButton.setBounds(1320,600,60,60);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawSalesGraph drawSalesGraph = new DrawSalesGraph(listA, listB, listC, listD, listE);
                drawSalesGraph.exitApplication();
                AccueilUC3 accueilUC3=new AccueilUC3();
                scoreFrame.dispose();


            }
        });


        // ajout de photos des scores
        Icon iconA = new ImageIcon(getClass().getResource("/icon_A.png"));
        JLabel photoLabelA = new JLabel(iconA);
        photoLabelA.setBounds(250,120,50,50);

        Icon iconB = new ImageIcon(getClass().getResource("/icon_B.png"));

        JLabel photoLabelB = new JLabel(iconB);
        photoLabelB.setBounds(250,210,50,50);

        Icon iconC = new ImageIcon(getClass().getResource("/icon_C.png"));
        JLabel photoLabelC = new JLabel(iconC);
        photoLabelC.setBounds(250,300,50,50);

        Icon iconD = new ImageIcon(getClass().getResource("/icon_D.png"));
        JLabel photoLabelD = new JLabel(iconD);
        photoLabelD.setBounds(250,390,50,50);

        Icon iconE = new ImageIcon(getClass().getResource("/icon_E.png"));
        JLabel photoLabelE = new JLabel(iconE);
        photoLabelE.setBounds(250,480,50,50);




        //--------------add-----------------
        mainPanel.add(chartPanel);

        mainPanel.add(photoLabelA);
        mainPanel.add(photoLabelB);
        mainPanel.add(photoLabelC);
        mainPanel.add(photoLabelD);
        mainPanel.add(photoLabelE);




        mainPanel.add(exportBtt);
        mainPanel.add(backButton);
        scoreFrame.add(mainPanel);

        scoreFrame.setVisible(true);



    }

    private ArrayList<Integer> generateSalesList(VenteScores venteScores, List<String> dateList) {
        ArrayList<Integer> salesList = new ArrayList<>();
        for (String date : dateList) {
            boolean found = false;
            for (VenteScore venteScore : venteScores.getVenteScores()) {
                if (venteScore.getMonth().equals(date)) {
                    salesList.add(venteScore.getSum());
                    found = true;
                    break;
                }
            }
            if (!found) {
                salesList.add(0);
            }
        }
        return salesList;
    }
    protected void exportData(){

        //creation de file selector
        JFileChooser fileChosser = new JFileChooser();

        //préciser type de dossier
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File (*.xls)", "xls");
        fileChosser.setFileFilter(filter);

        //on attend jusqu'a utilisateur puisse choisir confirmer

        int result = fileChosser.showSaveDialog(null);

        if(result== JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChosser.getSelectedFile();
            String filePath=selectedFile.getAbsolutePath();


            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Statistique par produit");


            //add the correct data in file
            Object[][] data = {
                    {"", "2023-05", "2023-06","2023-07","2023-08",
                            "2023-09","2023-10","2023-11","2023-12","2024-01","2024-02","2024-03","2024-04"},
                    {"A", listA.get(0), listA.get(1), listA.get(2), listA.get(3), listA.get(4), listA.get(5), listA.get(6), listA.get(7), listA.get(8), listA.get(9), listA.get(10), listA.get(11)},
                    {"B", listB.get(0), listB.get(1), listB.get(2), listB.get(3), listB.get(4), listB.get(5), listB.get(6), listB.get(7), listB.get(8), listB.get(9), listB.get(10), listB.get(11)},
                    {"C", listC.get(0), listC.get(1), listC.get(2), listC.get(3), listC.get(4), listC.get(5), listC.get(6), listC.get(7), listC.get(8), listC.get(9), listC.get(10), listC.get(11)},
                    {"D", listD.get(0), listD.get(1), listD.get(2), listD.get(3), listD.get(4), listD.get(5), listD.get(6), listD.get(7), listD.get(8), listD.get(9), listD.get(10), listD.get(11)},
                    {"E", listE.get(0), listE.get(1), listE.get(2), listE.get(3), listE.get(4), listE.get(5), listE.get(6), listE.get(7), listE.get(8), listE.get(9), listE.get(10), listE.get(11)}
            };

            int rowNum = 0;
            for (Object[] rowData : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object field : rowData) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }

            //Vérifier si l'extension est bonne
            if(!filePath.toLowerCase().endsWith(".xls")){
                filePath+=".xls";
            }
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                System.out.println("Le fichier excel est bien enregistré.");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if (result == JFileChooser.CANCEL_OPTION) {
            return ;
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exportBtt){

            exportData();
            JOptionPane jOptionPane = new JOptionPane();
            jOptionPane.showMessageDialog(scoreFrame,"votre fichier est bien enregistré","information",JOptionPane.INFORMATION_MESSAGE);

        }
    }


}
