package edu.ezip.ing1.pds.front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainFrameU2 extends JFrame implements ActionListener {
    private int width = 1000;
    private int height= 700;
    private JButton salesBtt;
    private JButton productBtt;
    private JButton categoryBtt;
    public void createMainFrame() {
        //TODO faire la classe SetFrame qui va instancier tous les cadres de frame
        // inserer la date
        // statistique


        // 프레임 설정
        JFrame frame = new JFrame("Usecase2: Statistique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.getContentPane().setBackground(new Color(0xE7EBE4));

        // 메인 패널 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(0xE7EBE4));

        // 로고 패널 설정
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
        Image resizedLogoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogoImage);
        JLabel logoLabel = new JLabel(resizedLogoIcon);
        logoPanel.add(logoLabel);

        // 헤더 패널 설정
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(Color.white);
       // headerPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        JLabel headerLabel = new JLabel("Vos statistiques");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(logoPanel);
        headerPanel.add(Box.createHorizontalGlue()); // 중앙으로 이동
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(headerLabel);
        headerPanel.add(Box.createHorizontalGlue()); // 오른쪽 여백 추가
        headerPanel.add(Box.createHorizontalGlue()); // 오른쪽 여백 추가
        headerPanel.add(Box.createHorizontalGlue());


        // panel pour 3 choix de statistique
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 3, 30, 30));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Statistique piur les meilleures ventes

        JPanel salesPanel = new JPanel();
        salesPanel.setBackground(Color.WHITE);
        salesPanel.setPreferredSize(new Dimension(100, 100));
        JLabel salesLabel = new JLabel("Vos meilleures ventes");
        salesLabel.setFont(new Font("Arial", Font.BOLD, 18));

        salesBtt = new JButton();
        salesBtt.addActionListener(this);
        salesBtt.add(salesLabel);
        salesPanel.add(salesBtt);



        // panel pour la statistique par produit
        JPanel productPanel = new JPanel();
        productPanel.setBackground(Color.WHITE);
        productPanel.setPreferredSize(new Dimension(100, 100));
        JLabel productLabel = new JLabel("Statistiques par produit");
        productLabel.setFont(new Font("Arial", Font.BOLD, 18));

        productBtt = new JButton();
        productBtt.addActionListener(this);
        productBtt.add(productLabel);
        productPanel.add(productBtt);

        // panel pour la stat par catégorie
        JPanel categoryPanel = new JPanel();
        categoryPanel.setBackground(Color.WHITE);
        categoryPanel.setPreferredSize(new Dimension(100,100));
        JLabel categoryLabel = new JLabel("Statistiques par catégorie");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));

        categoryBtt = new JButton();
        categoryBtt.addActionListener(this);
        categoryBtt.add(categoryLabel);

        categoryPanel.add(categoryBtt);


        contentPanel.setBorder(new EmptyBorder(50, 50, 50, 50));


        // panel pour le bilan total
        JPanel bilanPanel = new JPanel();
        JButton bilanBtt = new JButton();

        bilanPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        bilanBtt.setText("voir votre bilan total");
        bilanPanel.add(bilanBtt);



        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(bilanPanel, BorderLayout.SOUTH);

        contentPanel.add(salesPanel);
        contentPanel.add(productPanel);
        contentPanel.add(categoryPanel);


        frame.add(mainPanel);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == salesBtt) {
            SalesFrame salesFrame = new SalesFrame();
            this.setVisible(false);
            salesFrame.createSalesFrame();
        }
        if(e.getSource() == productBtt) {
            ProductFrame productFrame =new ProductFrame();
            productFrame.createProductFrame();

        }
        if(e.getSource() == categoryBtt) {

        }
    }
}
