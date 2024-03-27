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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PathManagement implements ActionListener{

    JFrame pathManagementFrame = new JFrame();
    private JPanel mainPanel;
    private JPanel mapPanel;
    private BufferedImage backgroundImage;
    private ArrayList<Point> points = new ArrayList<>();
    private Point startPoint = null;
    private Point endPoint = null;
    private ArrayList<Point> path = new ArrayList<>();
    private JButton backHomeButton;
    private JButton addPath;
    private JButton modifyPath;
    private JButton deletePath;
    private JButton calculatePath = new JButton();
    private JButton validate = new JButton();

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
                for (Point point : points) {
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
                if (!path.isEmpty()) {
                    g.setColor(Color.RED);
                    for (int i = 0; i < path.size() - 1; i++) {
                        Point p1 = path.get(i);
                        Point p2 = path.get(i + 1);
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        };

        mapPanel.setLayout(null);
        
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point point = e.getPoint();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    points.add(point);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if (startPoint == null) {
                        startPoint = point;
                    } else if (endPoint == null) {
                        endPoint = point;
                        points.add(endPoint);
                    }
                }
                mapPanel.repaint();
            }
        });

        mapPanel.setBounds(60, 50,770, 580);

        //Bouton retour :
        backHomeButton = new JButton("Retour à l'accueil");
        backHomeButton.addActionListener(this);
        backHomeButton.setBounds(610, 650, 180, 40);
        mainPanel.add(backHomeButton);

        //Panel boutons d'actions
        JPanel actionButtonsPanel = new JPanel();
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
        path.clear();
        Point currentPoint = startPoint;
        path.add(currentPoint);
        while (!currentPoint.equals(endPoint)) {
            Point nextPoint = findClosestPoint(currentPoint);
            if (nextPoint == null) {
                break;
            }
            path.add(nextPoint);
            currentPoint = nextPoint;
        }
    }

    private Point findClosestPoint(Point from) {
        double minDistance = Double.MAX_VALUE;
        Point closestPoint = null;
        for (Point point : points) {
            if (!path.contains(point)) {
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
            calculatePath.setText("Tracer le chemin");
            calculatePath.setBounds(910, 650, 180, 40);
            calculatePath.addActionListener(this);
            mainPanel.add(calculatePath);
            validate.setText("Valider");
            validate.setBounds(1110, 650, 180, 40);
            mainPanel.add(validate);
            mainPanel.revalidate();
            mainPanel.repaint();
        } else if (e.getSource() == calculatePath){
            System.out.println("dans tracer chemin");
            calculatePath();
            mapPanel.repaint();
        }
    }
    
}
