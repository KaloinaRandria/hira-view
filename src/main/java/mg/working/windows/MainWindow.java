package mg.working.windows;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setTitle("Famoronana Fafana");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Panel central avec label et champ de texte côte à côte
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // marges

        JLabel jLabel = new JLabel("Contenu");
        jLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(jLabel, gbc);

        JTextArea jTextArea = new JTextArea(10, 50);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(scrollPane, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        // Panel bas avec bouton à droite
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton jButton = new JButton("Generer");
        JPanel buttonRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonRightPanel.add(jButton);
        bottomPanel.add(buttonRightPanel, BorderLayout.SOUTH);

        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}
