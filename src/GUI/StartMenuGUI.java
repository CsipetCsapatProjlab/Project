package GUI;

import javax.swing.*;

import model.Fungorium;
import model.gameobjects.Rovar;
import model.players.Gombasz;
import model.players.Rovarasz;

import java.awt.*;
import java.util.ArrayList;

public class StartMenuGUI extends JFrame {

    private JTextField widthField;
    private JTextField heightField;
    private JSpinner rovarSpinner;
    private JSpinner gombaSpinner;
    private JButton startButton;

    private java.util.List<CustomPanel> playerPanels;

    public StartMenuGUI() {
        setTitle("Fungi Mungi 2000");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel(), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // középre helyezi
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("<html><h1>Fungi<br>Mungi<br>2000</h1></html>");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(new JLabel("Pályaméret:"));
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        widthField = new JTextField(3);
        heightField = new JTextField(3);
        sizePanel.add(widthField);
        sizePanel.add(new JLabel("x"));
        sizePanel.add(heightField);
        panel.add(sizePanel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        rovarSpinner = new JSpinner(new SpinnerNumberModel(3, 0, 10, 1));
        gombaSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));

        panel.add(new JLabel("rovarászok"));
        panel.add(rovarSpinner);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JLabel("gombászok"));
        panel.add(gombaSpinner);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Player Panels
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playerPanels = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            CustomPanel p = new CustomPanel();
            playersPanel.add(p);
            playersPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            playerPanels.add(p);
        }

        JScrollPane scrollPane = new JScrollPane(playersPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Start button
        startButton = new JButton("Start");
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(startButton);
        startButton.addActionListener(e -> onStartPressed());

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void onStartPressed() {
        java.util.List<String> validPlayers = new ArrayList<>();
        int sor = Integer.parseInt(heightField.getText().trim());
        int oszlop = Integer.parseInt(heightField.getText().trim());

        Fungorium f = new Fungorium(sor, oszlop);
        for (CustomPanel panel : playerPanels) {
            String name = panel.getName();
            String type = panel.getSelection();
            Color color = panel.getColor();
    
            boolean isValid = name != null && !name.trim().isEmpty() && type != null && color != null;
    
            if (isValid) {
                validPlayers.add(name + " - " + type + " - " + color.toString());
                if(name == "Gombasz"){
                    f.addJatekos(new Gombasz(name));
                }else{
                    f.addJatekos(new Rovarasz(name));
                }
            }
        }
        FungoriumGUI GUI = new FungoriumGUI(f);
    
        if (validPlayers.size() < 2) {
            JOptionPane.showMessageDialog(this,
                "Legalább két érvényes játékost kell megadni!",
                "Hiba",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        System.out.println("Játék indul ezekkel a játékosokkal:");
        for (String info : validPlayers) {
            System.out.println(info);
        }
    
        // Továbblépés például új ablak vagy játék logika elindítása itt
    }
    
}
