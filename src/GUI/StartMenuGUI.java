package GUI;

import javax.swing.*;

import java.awt.*;
import java.util.List;

public class StartMenuGUI extends JFrame {

    private JTextField widthField;
    private JTextField heightField;
    PlayerSelectionPanel  playerSelectionPanel = new PlayerSelectionPanel(5);
    private String betoltes;

    public StartMenuGUI() {
        setTitle("Fungi Mungi 2000");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createRightPanel(), BorderLayout.CENTER);
        add(createLeftPanel(), BorderLayout.WEST);
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

        JSpinner jatekosSpinner = new JSpinner(new SpinnerNumberModel(playerSelectionPanel.getNumPlayers(), 0, 100, 1));
        jatekosSpinner.addChangeListener(x -> playerSelectionPanel.resizeTo((int) jatekosSpinner.getValue()));
        panel.add(new  JLabel("Jatekosok Száma:"));
        panel.add(jatekosSpinner);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(playerSelectionPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Gombok létrehozása
        JButton startButton = new JButton("Start");
        JButton loadButton = new JButton("Betöltés");
        JButton folderButton = new JButton("Mappa kiválasztása");

        // Eseménykezelők
        startButton.addActionListener(e -> onStartPressed());
        loadButton.addActionListener(e -> onLoadPressed());
        folderButton.addActionListener(e -> onSelectFolder());

        // Gombok hozzáadása egy alsó panelhez
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(folderButton);
        bottomPanel.add(loadButton);
        bottomPanel.add(startButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void onLoadPressed() {
        FungoriumGUI GUI = new FungoriumGUI(betoltes);
    }

    private void onSelectFolder() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = folderChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedFolder = folderChooser.getSelectedFile().getAbsolutePath();
            betoltes = selectedFolder;
        }
    }

    private void onStartPressed() {
        List<PlayerGUI> jatekosokGui = playerSelectionPanel.getJatekosok();

        if (jatekosokGui.size() < 2) {
            JOptionPane.showMessageDialog(this,
                "Legalább két érvényes játékost kell megadni!",
                "Hiba",
                JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            int sor, oszlop;
            try {
                sor = Integer.parseInt(heightField.getText().trim());
                oszlop = Integer.parseInt(widthField.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "2 egész számot kell megadni pályaméretnek.",
                        "Hibás pályaméret!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            FungoriumGUI GUI = new FungoriumGUI(sor, oszlop, jatekosokGui);
        }
    
        System.out.println("Játék indul ezekkel a játékosokkal:");
        for (var jatekosGui : jatekosokGui) {
            System.out.println(jatekosGui);
        }


        // Továbblépés például új ablak vagy játék logika elindítása itt

        this.setVisible(false);
    }
    
}
