package edu.ezip.ing1.pds.front.UC2;

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
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Path;
import edu.ezip.ing1.pds.business.dto.PointChemin;
import edu.ezip.ing1.pds.client.InsertPointsRequest;
import edu.ezip.ing1.pds.client.UC2.DeletePath;
import edu.ezip.ing1.pds.front.MethodesFront;
import edu.ezip.ing1.pds.front.RechercheReference;
import edu.ezip.ing1.pds.front.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

public class PathManagement implements ActionListener{

    protected static Logger loggerPathManagement;
    JFrame pathManagementFrame = new JFrame();
    private JPanel mainPanel;
    private JPanel mapPanel;
    private JPanel actionButtonsPanel;
    private BufferedImage backgroundImage;
    private Path path = new Path();
    private Point startPoint = null;
    private Point endPoint = null;
    private JButton addPath;
    private JButton newPath;
    private JButton deletePath;
    private JButton calculatePath = new JButton();
    private JButton validate = new JButton();
    private boolean firstPath = true;
    private JComboBox<Integer> comboBox;
    private JComboBox<Integer> comboBoxToDelete;
    private int aisleNumber;
    private boolean canValidate = false;
    private JButton backMenu = new JButton("Retour au menu");
    private JLabel newImage;
    private int aisleToDelete;
    private JButton validateDeletion;

    public PathManagement(){

        loggerPathManagement = LogManager.getLogger(PathManagement.class);
        pathManagementFrame  = new JFrame();
        pathManagementFrame.setSize(Template.LONGUEUR, Template.LARGEUR);
        pathManagementFrame.setTitle("Configurez vos chemins.");
        pathManagementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pathManagementFrame.setLocationRelativeTo(null);
        pathManagementFrame.setResizable(false);

        //----------panel header--------------
        String headerTitle = "Configurez les emplacements de votre magasin";
        MethodesFront.header(pathManagementFrame, headerTitle, 450);
        //------------------------------------

        // Charger l'image de fond
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(MethodesFront.class.getResource("/mapV1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.decode(Template.COULEUR_PRINCIPALE));

        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(5));
                
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }

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

        backMenu.addActionListener(this);
        mapPanel.setLayout(null);
        mapPanel.setBounds(60, 50,770, 580);

        actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(null);
        actionButtonsPanel.setBackground(Color.decode(Template.COULEUR_SECONDAIRE));
        actionButtonsPanel.setBounds(840, 50,500,580);

        addPath = new JButton();
        addPath.setText("Ajouter un chemin");
        addPath.setBounds(100, 160, 300, 70);
        addPath.setFont(new Font(Template.POLICE, Font.BOLD, 18));
        addPath.addActionListener(this);
        actionButtonsPanel.add(addPath);

        deletePath = new JButton();
        deletePath.addActionListener(this);
        deletePath.setText("Supprimer un chemin");
        deletePath.setBounds(100, 260, 300, 70);
        deletePath.setFont(new Font(Template.POLICE, Font.BOLD, 18));
        deletePath.addActionListener(this);
        actionButtonsPanel.add(deletePath);

        //TODO : à régler ou a enlever
        newImage = new JLabel();
        newPath = new JButton();
        newPath.setText("Charger une map");
        newPath.setBounds(100, 360, 300, 70);
        newPath.setFont(new Font(Template.POLICE, Font.BOLD, 18));
        newPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    newImage.setIcon(imageIcon);
                }
            }
        });
        actionButtonsPanel.add(newPath);

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
        if (e.getSource() == addPath) {
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
                        if (startPoint != null && endPoint != null) {
                            canValidate = true;
                        }
                    }
                    mapPanel.repaint();
                }
            });
            
            calculatePath.setText("Tracer le chemin");
            calculatePath.setBounds(910, 650, 180, 40);
            calculatePath.addActionListener(this);
            validate.setText("Valider");
            validate.setBounds(1110, 650, 180, 40);
            validate.addActionListener(this);
            mainPanel.add(validate);
            backMenu.setBounds(920, 650, 180, 40);
            mainPanel.add(backMenu);
            mainPanel.revalidate();
            mainPanel.repaint();
            mainPanel.revalidate();
            mainPanel.repaint();

            actionButtonsPanel.remove(addPath);
            actionButtonsPanel.remove(newPath);
            actionButtonsPanel.remove(deletePath);
            actionButtonsPanel.repaint();
            actionButtonsPanel.revalidate();
            JLabel aisleLabel = new JLabel("Pour quel rayon voulez vous ajouter un chemin ?");
            aisleLabel.setForeground(Color.WHITE);
            aisleLabel.setBounds(35, 170, 500, 70);
            aisleLabel.setFont(new Font(Template.POLICE, Font.BOLD, 20));
            actionButtonsPanel.add(aisleLabel);
            JLabel explainations = new JLabel();
            explainations.setText("<html>Pour ajouter le point de départ et le point d'arrivée, faites un clic droit. Pour ajouter un point intermediaire, faites un clic gauche. Attention, si vous n'indiquez pas un point de départ et un point d'arrivée, vous devrez recommencer. Pour les escalators : rayon 15.</html>");
            explainations.setBounds(35, 440, 440, 115);
            explainations.setForeground(Color.WHITE);
            explainations.setFont(new Font(Template.POLICE, Font.BOLD, 17));
            actionButtonsPanel.add(explainations);

            Integer[] options = new Integer[15];
            for (int i = 1; i <= 15; i++) {
                options[i - 1] = i;
            }

            comboBox = new JComboBox<>(options);
            comboBox.setBounds(200, 270, 100, 80);
            actionButtonsPanel.add(comboBox);

        } else if (e.getSource() == calculatePath) {
            if (firstPath) {
                firstPath = false;
                calculatePath();
                mapPanel.repaint();
            }
        } else if (e.getSource() == validate) {
            if (canValidate) {

                aisleNumber = (int) comboBox.getSelectedItem();
                String responseBody = "";
                ObjectMapper objectMapper = new ObjectMapper();
                for (int i = 0; i < path.getPoints().size(); i++) {
                    PointChemin pointChemin = new PointChemin();
                    pointChemin.setCoordX(path.getPoints().get(i).x);
                    pointChemin.setCoordY(path.getPoints().get(i).y);
                    pointChemin.setIdRayon(aisleNumber);
                    try {
                        responseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pointChemin);
                        InsertPointsRequest.insertPoints(responseBody);
                    } catch (IOException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                mainPanel.remove(validate);
                backMenu.setBounds(1110, 650, 180, 40);
                mainPanel.add(backMenu);
                mainPanel.revalidate();
                mainPanel.repaint();

                actionButtonsPanel.removeAll();
                actionButtonsPanel.revalidate();
                actionButtonsPanel.repaint();
                JLabel succes = new JLabel("Votre chemin a bien été enregistré !");
                succes.setForeground(Color.WHITE);
                succes.setBounds(85, 270, 500, 70);
                succes.setFont(new Font(Template.POLICE, Font.BOLD, 20));
                actionButtonsPanel.add(succes);

            } else {
                path.getPoints().clear();
                startPoint = null;
                endPoint = null;
                mapPanel.repaint();
            }
        } else if(e.getSource() == deletePath){
            actionButtonsPanel.removeAll();
            actionButtonsPanel.revalidate();
            actionButtonsPanel.repaint();
            JLabel deleteLabel = new JLabel("<html>Quel est le rayon pour lequel vous souhaitez supprimer votre chemin ?</html>");
            deleteLabel.setFont(Template.FONT_ECRITURE2);
            deleteLabel.setForeground(Color.WHITE);
            deleteLabel.setBounds(20, 180, 450, 100);
            actionButtonsPanel.add(deleteLabel);
            Integer[] options = new Integer[14];
            for (int i = 1; i <= 14; i++) {
                options[i - 1] = i;
            }
            comboBoxToDelete = new JComboBox<>(options);
            comboBoxToDelete.setBounds(200, 270, 100, 80);
            actionButtonsPanel.add(comboBoxToDelete);
            validateDeletion = new JButton("Supprimer le chemin");
            validateDeletion.setBounds(160, 370, 175, 40);
            validateDeletion.addActionListener(this);
            actionButtonsPanel.add(validateDeletion);
            //TODO : Requete de suppression d'un chemin :
        }else if (e.getSource() == backMenu){
            PathManagement pathManagement = new PathManagement();
            pathManagementFrame.dispose();
        }
        if(e.getSource() == validateDeletion){
            aisleToDelete = (int) comboBoxToDelete.getSelectedItem();

            try{
                DeletePath.launchDeletePath(aisleToDelete);
                actionButtonsPanel.removeAll();
                actionButtonsPanel.revalidate();
                actionButtonsPanel.repaint();
                JLabel succes = new JLabel("Votre chemin a bien été supprimé !");
                succes.setForeground(Color.WHITE);
                succes.setBounds(85, 270, 500, 70);
                succes.setFont(new Font(Template.POLICE, Font.BOLD, 20));
                actionButtonsPanel.add(succes);
                mainPanel.revalidate();
                mainPanel.repaint();
                backMenu.setBounds(1110, 650, 180, 40);
                mainPanel.add(backMenu);
            }catch(Exception exception){
                loggerPathManagement.error("Erreur lors de la suppression du rayon, veuillez réessayer.");
                actionButtonsPanel.removeAll();
                actionButtonsPanel.revalidate();
                actionButtonsPanel.repaint();
                JLabel fail = new JLabel("<html>Problème lors de la suppression, veuillez réessayer !</html>");
                fail.setForeground(Color.WHITE);
                fail.setBounds(85, 270, 500, 70);
                fail.setFont(new Font(Template.POLICE, Font.BOLD, 20));
                actionButtonsPanel.add(fail);
                mainPanel.revalidate();
                mainPanel.repaint();
                backMenu.setBounds(1110, 650, 180, 40);
                mainPanel.add(backMenu);
            }
        }
    }
}
