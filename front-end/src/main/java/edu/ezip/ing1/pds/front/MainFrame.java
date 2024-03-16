
package edu.ezip.ing1.pds.front;
import ch.qos.logback.classic.net.JMSQueueAppender;
import edu.ezip.ing1.pds.backend.CoreBackendServer;
import edu.ezip.ing1.pds.backend.RequestHandler;
import edu.ezip.ing1.pds.client.SelectAllProductsClientRequest;
import edu.ezip.ing1.pds.client.SelectEmplacementById;
import edu.ezip.ing1.pds.client.SelectProductByReference;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import edu.ezip.ing1.pds.business.dto.Emplacement;
import edu.ezip.ing1.pds.business.server.XMartCityService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static java.lang.String.valueOf;

public class MainFrame extends JFrame implements ActionListener {

    //Frame demandée
    private int longueur;
    private int largeur;
    private String titre;
    private JButton button;
    private JLabel label;

    public static MainFrame instance = null;
    private JTextArea textArea;

    //Contructeur par défaut :
    private MainFrame(){
        longueur = 800;
        largeur = 600;
        titre = "Index";

        initialize();
    }

    //Constructeur paramétré avec titre uniquement :
    private MainFrame(String titre){
        this.titre = titre;
        longueur = 900;
        largeur = 800;

        initialize();
    }

    //Constructeur paramétré avec toutes les valeurs :
    private MainFrame(String titre, int longueur, int largeur){
        this.titre = titre;
        this.longueur = longueur;
        this.largeur = largeur;

        initialize();
    }

    //Méthode getInstance pour ne pouvoir faire qu'une instance au plus.
    //Si deja une insatnce la recupere sinon en crée une
    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame("Home");
        }
        return instance;
    }

    //Méthode pour initialiser la JFrame :
    public void initialize(){
        setTitle(titre);
        setSize(longueur, largeur);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label=new JLabel("Liste des élèves :");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBorder(new EmptyBorder(15, 20, 0, 0));
        
        button = new JButton();
        button.addActionListener(this);
        button.setText("Afficher les élèves");
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        panel.add(label, BorderLayout.NORTH);
        panel.add(button, BorderLayout.SOUTH);

        textArea = new JTextArea();
        textArea.setBounds(140, 70, 600, 600);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel panelMid = new JPanel();
        panelMid.add(scrollPane);
        panelMid.setLayout(null);
        panelMid.setBackground(Color.white);
        panelMid.add(textArea);
        panelMid.add(scrollPane);
        panel.add(panelMid, BorderLayout.CENTER);
        setVisible(true);
    }

    //TODO : eventuellement changer la taille du bouton car prend toute la longueur de la frame.

    public static void main(String[] args) {
        MainFrame frame =  MainFrame.getInstance();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            try {
                //textArea.append(SelectAllProductsClientRequest.launchSelectAllProducts());
                //textArea.append(SelectProductByReference.launchSelectProductByReference());
                Request request = new Request();
                request.setRequestContent("3");
                Emplacement emplacement = new Emplacement();
                emplacement = SelectEmplacementById.launchSelectEmplacementById(request);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}