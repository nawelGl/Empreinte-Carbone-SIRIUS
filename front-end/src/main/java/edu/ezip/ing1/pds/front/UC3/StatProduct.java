package edu.ezip.ing1.pds.front.UC3;

import edu.ezip.ing1.pds.business.dto.Vente;
import edu.ezip.ing1.pds.client.UC3.SelectBeforeVenteByReference;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RoundedPanel;
import edu.ezip.ing1.pds.front.Template;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;
import static java.lang.String.valueOf;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;

public class StatProduct implements ActionListener {

    JFrame statUC3;
    String[] labels = {"Avant", "Après"};
    String photoName;

    JButton exportBtt;

    private int reference = RechercheReferenceUC3.venteBefore.getReference();
    private String score = RechercheReferenceUC3.venteBefore.getScore();
    private Vente vente;
    private  int textSize=20;
    private int salesBefore;
    private int salesAfter;



    public StatProduct(int salesBefore, int salesAfter ){
        this.salesBefore=salesBefore;
        this.salesAfter=salesAfter;

        // recherche de information vente pour dessiner les charts et afficher

        try{
            Request request = new Request();
            request.setRequestContent(valueOf(reference));
            vente = SelectBeforeVenteByReference.launchSelectVenteByReference(request);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        statUC3 = new JFrame("Statistiques");
        statUC3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statUC3.setResizable(false);
        statUC3.setSize(Template.LONGUEUR,Template.LARGEUR);
        statUC3.setLocationRelativeTo(null);

        //------------------panel header---------------------------
        MethodesFront.header(statUC3,"Vos statistiques par produit",525);

        //mainPanel(principal)
        JPanel mainPanel = new JPanel(null);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        //infoPanel
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBackground(Color.white);
        infoPanel.setBounds(150,80,500,550);

        //titlePanel
        RoundedPanel infoTitlePanel = new RoundedPanel(30,30);
        JLabel infoTitle = new JLabel("Information de votre produit "+vente.getReference());
        infoTitle.setFont(new Font("Avenir",Font.BOLD,17));
        infoTitle.setForeground(Color.white);

        //ajoute de EmptyBorder pour le placement de title
        EmptyBorder emptyBorder1 = new EmptyBorder(11, 0, 0, 0);
        infoTitlePanel.setBorder(emptyBorder1);

        infoTitlePanel.add(infoTitle);
        infoTitlePanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        infoTitlePanel.setBounds(0,0,500,60);

        // recuperer l'image de produit par reference
        photoName = "/"+Integer.toString(reference)+".png";

        // controle de dimension de l'image

        ImageIcon imageIcon = new ImageIcon(getClass().getResource(photoName));
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(200, 205, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(newImage);

        JLabel photoLabel = new JLabel();
        photoLabel.setIcon(scaledImageIcon);

        // photoLabel
        photoLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(photoName))));



        // textLabel
        JLabel textLabel = new JLabel("Le score de votre produit est ");


        // scoreLabel
        JLabel scoreLabel = new JLabel();
        // Switch case pour recuperer l'icone correspondqnt

        switch (score) {
            case "A":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_A.png"))));
                break;
            case "B":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_B.png"))));
                break;
            case "C":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_C.png"))));
                break;
            case "D":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_D.png"))));
                break;
            case "E":
                scoreLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon_E.png"))));
                break;
        }

        photoLabel.setBounds(150,100,200,205);
        textLabel.setBounds(30,400,200,20);
        scoreLabel.setBounds(350,380,50,50);

        //-------ajout de labels----------
        infoPanel.add(infoTitlePanel);



        infoPanel.add(photoLabel);
        infoPanel.add(textLabel);
        infoPanel.add(scoreLabel);




        //chartPanel
        JPanel chartPanel = new JPanel(null);


        // --ajout de title panel
        RoundedPanel chartTitlePanel = new RoundedPanel(30,30);
        JLabel charTitle = new JLabel("Statistiques des ventes");

        charTitle.setFont(new Font("Avenir",Font.BOLD,17));
        charTitle.setForeground(Color.white);

        //ajoute de EmptyBorder pour le placement de title

        EmptyBorder emptyBorder2 = new EmptyBorder(11, 0, 0, 0);
        chartTitlePanel.setBorder(emptyBorder2);

        chartTitlePanel.add(charTitle);
        chartTitlePanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        chartTitlePanel.setBounds(0,0,500,60);


        double[] values = {salesBefore, salesAfter};
        DrawBarChart barChart = new DrawBarChart(labels, values);
        barChart.setBounds(0,63,500,490);
        chartPanel.add(chartTitlePanel);
        chartPanel.add(barChart);

        chartPanel.setBounds(780,80,500,550);


        //-------------------ajout de bouton export
        exportBtt=new JButton("Exporter les data");
        exportBtt.setBackground(Color.white);
        exportBtt.addActionListener( this);
        exportBtt.setBounds(1250,30,140,30);

        //----------------------------------------------
        mainPanel.add(infoPanel);
        mainPanel.add(chartPanel);
        mainPanel.add(exportBtt);
        statUC3.getContentPane().add(mainPanel);

        //-----------------------------------------------
        statUC3.setVisible(true);


    }
    private void exportData(){

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

            Object[][] data = {
                    {"Produit", "Vente avant installation", "Vente après installation"},
                    {reference, salesBefore, salesAfter}
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
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exportBtt){
            exportData();
            JOptionPane jOptionPane = new JOptionPane();
            jOptionPane.showMessageDialog(statUC3,"votre fichier est bien enregistré","information",JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
