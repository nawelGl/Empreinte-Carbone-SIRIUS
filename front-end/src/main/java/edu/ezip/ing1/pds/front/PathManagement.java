package edu.ezip.ing1.pds.front;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Path;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.client.InsertPointsRequest;

public class PathManagement implements ActionListener{

    JFrame pathManagementFrame = new JFrame();
    private JPanel mainPanel;
    private JPanel mapPanel;
    private JPanel actionButtonsPanel;
    private BufferedImage backgroundImage;
    private Path path = new Path();
    private Point startPoint = null;
    private Point endPoint = null;
    private JButton backHomeButton;
    private JButton addPath;
    private JButton modifyPath;
    private JButton deletePath;
    private JButton calculatePath = new JButton();
    private JButton validate = new JButton();
    private boolean firstPath = true;
    private JComboBox<Integer> comboBox;
    private int numeroRayon;

    public PathManagement(){

        pathManagementFrame  = new JFrame();
        pathManagementFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        pathManagementFrame.setTitle("Configurez vos chemins.");
        pathManagementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pathManagementFrame.setLocationRelativeTo(null);
        pathManagementFrame.setResizable(false);

        //----------panel header--------------
        String titreHeader = "Configurez les emplacements de votre magasin";
        Methodes.header(pathManagementFrame, titreHeader, 450);
        //------------------------------------

        // Charger l'image de fond
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(Methodes.class.getResource("/mapV1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        // Créer un JPanel pour superposer l'image de fond et le panneau pour les points
        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                // Épaisseur de la ligne (chemin)
                g2d.setStroke(new BasicStroke(5));

                // Dessiner l'image de fond
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
                // Dessiner les points et le chemin
                if(firstPath){
                    for (Point point : path.getPoints()) {
                        g.setColor(Color.BLACK);
                        g.fillOval(point.x - 5, point.y - 5, 10, 10);
                    }
                    if (startPoint != null) {
                        g.setColor(Color.GREEN);
                        g.fillOval(startPoint.x - 5, startPoint.y - 5, 10, 10);
                    }
                    if (endPoint != null) {
                        g.setColor(Color.RED);
                        g.fillOval(endPoint.x - 5, endPoint.y - 5, 10, 10);
                    }
                }
                if (!path.getPoints().isEmpty()) {
                    g.setColor(Color.RED);
                    for (int i = 0; i < path.getPoints().size() - 1; i++) {
                        Point p1 = path.getPoints().get(i);
                        Point p2 = path.getPoints().get(i + 1);
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        };

        mapPanel.setLayout(null);

        mapPanel.setBounds(60, 50,770, 580);

        //Bouton retour :
        backHomeButton = new JButton("Retour à l'accueil");
        backHomeButton.addActionListener(this);
        backHomeButton.setBounds(610, 650, 180, 40);
        mainPanel.add(backHomeButton);

        //Panel boutons d'actions
        actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(null);
        actionButtonsPanel.setBackground(Color.decode(Template.COUELUR_SECONDAIRE));
        actionButtonsPanel.setBounds(840, 50,500,580);

        //Boutons d'acions :
        addPath = new JButton();
        addPath.setText("Ajouter un chemin");
        addPath.setBounds(100, 160, 300, 70);
        addPath.setFont(new Font(Template.POLICE, Font.BOLD, 18));
        addPath.addActionListener(this);
        actionButtonsPanel.add(addPath);

        modifyPath = new JButton();
        modifyPath.setText("Modifier un chemin");
        modifyPath.setBounds(100, 260, 300, 70);
        modifyPath.setFont(new Font(Template.POLICE, Font.BOLD, 18));
        modifyPath.addActionListener(this);
        actionButtonsPanel.add(modifyPath);

        deletePath = new JButton();
        deletePath.setText("Supprimer un chemin");
        deletePath.setBounds(100, 360, 300, 70);
        deletePath.setFont(new Font(Template.POLICE, Font.BOLD, 18));
        deletePath.addActionListener(this);
        actionButtonsPanel.add(deletePath);

        mainPanel.add(mapPanel);
        mainPanel.add(actionButtonsPanel);
        pathManagementFrame.getContentPane().add(mainPanel);
        pathManagementFrame.setVisible(true);
    }

    private void calculatePath() {
        if (startPoint == null || endPoint == null) {
            return;
        }
        path.getPoints().clear();
        Point currentPoint = startPoint;
        path.getPoints().add(currentPoint);
        while (!currentPoint.equals(endPoint)) {
            Point nextPoint = findClosestPoint(currentPoint);
            if (nextPoint == null) {
                break;
            }
            path.getPoints().add(nextPoint);
            currentPoint = nextPoint;
        }
    }

    private Point findClosestPoint(Point from) {
        double minDistance = Double.MAX_VALUE;
        Point closestPoint = null;
        for (Point point : path.getPoints()) {
            if (!path.getPoints().contains(point)) {
                double distance = point.distance(from);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = point;
                }
            }
        }
        return closestPoint;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backHomeButton){
            pathManagementFrame.dispose();
            EcranAcceuil ecranAcceuil = new EcranAcceuil();
        } else if(e.getSource() == addPath){
            mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point point = e.getPoint();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    path.getPoints().add(point);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if (startPoint == null) {
                        startPoint = point;
                        path.getPoints().add(startPoint);
                    } else if (endPoint == null) {
                        endPoint = point;
                        path.getPoints().add(endPoint);
                    }
                }
                mapPanel.repaint();
            }
        });
            calculatePath.setText("Tracer le chemin");
            calculatePath.setBounds(910, 650, 180, 40);
            calculatePath.addActionListener(this);
            mainPanel.add(calculatePath);
            validate.setText("Valider");
            validate.setBounds(1110, 650, 180, 40);
            validate.addActionListener(this);
            mainPanel.add(validate);
            mainPanel.revalidate();
            mainPanel.repaint();

            //Modifier la configuration du panel actions :
            actionButtonsPanel.remove(addPath);
            actionButtonsPanel.remove(modifyPath);
            actionButtonsPanel.remove(deletePath);
            actionButtonsPanel.repaint();
            actionButtonsPanel.revalidate();
            JLabel labelRayon = new JLabel("Pour quel rayon voulez vous ajouter un chemin ?");
            labelRayon.setForeground(Color.WHITE);
            labelRayon.setBounds(35, 170, 500, 70);
            labelRayon.setFont(new Font(Template.POLICE, Font.BOLD, 20));
            actionButtonsPanel.add(labelRayon);

            Integer[] options = new Integer[14];
            for(int i = 1; i <= 14; i++){
                options[i-1] = i;
            }
            
            comboBox = new JComboBox<>(options);
            comboBox.setBounds(200, 270, 100, 80);
            actionButtonsPanel.add(comboBox);

        } else if (e.getSource() == calculatePath){
            if(firstPath){
                firstPath = false;
                calculatePath();
                mapPanel.repaint();
            }
        } else if(e.getSource() == validate){
            //TODO : créer une requete qui contient les infos necessaires aux inserts, càd les points du path à insérer en base
            //Rappel : requestBody = String et non Arraylist !!
            numeroRayon = (int)comboBox.getSelectedItem();
            //Création de la requete :
            Request request = new Request();
            //Création d'un String qui va etre set dans la requete en tant que requestBody pour devenir responseBody :
            String responseBody = "";
            //Création d'un object mapper pour mettre les données de l'arraylist dans le String crée ci dessus :
            ObjectMapper objectMapper = new ObjectMapper();
            //on met ce qu'il faut dans response body grace à l'object mapper :

            //Pour chaque point de l'arraylist, on fait un insert
            //car dans la methode d'inser : on utilise un obejt de type pointChemin et non une arraylist
            for (int i = 0; i < path.getPoints().size(); i++){
                try {
                    responseBody = objectMapper.writeValueAsString(path.getPoints().get(i));
                    System.out.println("RESPONSEBODY : " + responseBody.toString());
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }

                request.setRequestContent(responseBody);

                //Appel à la méthode d'insert en passant en paramètres la requete :
                try {
                    InsertPointsRequest.insertPoints(request);
                } catch (IOException ex) {
                    System.out.println("Erreur d'insertion des points : " + ex.getMessage());
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    System.out.println("Erreur d'insertion des points : " + ex.getMessage());
                    throw new RuntimeException(ex);
                }

            }

        }
    }
    
}
