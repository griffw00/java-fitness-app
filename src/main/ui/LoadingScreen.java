package ui;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame {

    public LoadingScreen() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Loading Screen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout()); // Use GridBagLayout for better component placement
        getContentPane().setBackground(Color.WHITE);

        addImageLabel();
        addWelcomeLabel();
        addStartButton();
        addQuitButton();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void addImageLabel() {
        // Load image from file
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/dumbbell.jpeg"));
        ImageIcon resizedIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.5;
        add(imageLabel, gbc);
    }

    private void addWelcomeLabel() {
        JLabel label = new JLabel("Welcome to JFit!", SwingConstants.CENTER);
        Font customFont = new Font("Arial", Font.PLAIN, 25);
        label.setFont(customFont);
        setForeground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        add(label, gbc);
    }

    private void addStartButton() {
        JButton startButton = new JButton("Start Application");
        setForeground(Color.LIGHT_GRAY);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        Font customFont = new Font("Arial", Font.PLAIN, 14);
        startButton.setFont(customFont);
        startButton.addActionListener(e -> {
            dispose();  // Close the loading screen
            new FitnessAppGUI();  // Open the main application window
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        add(startButton, gbc);
    }

    private void addQuitButton() {
        JButton quitButton = new JButton("Quit Application");
        setForeground(Color.LIGHT_GRAY);
        quitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        Font customFont = new Font("Arial", Font.PLAIN, 14);
        quitButton.setFont(customFont);
        quitButton.addActionListener(e -> System.exit(0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.1;
        add(quitButton, gbc);
    }
}


