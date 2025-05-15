package GUI;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartMenuGUI extends JFrame {

    private JTextField widthField;
    private JTextField heightField;
    PlayerSelectionPanel  playerSelectionPanel = new PlayerSelectionPanel(5);
    private String betoltes = null;

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
        if(betoltes != null){
            FungoriumGUI GUI = new FungoriumGUI(betoltes);
        }
    }

    private void onSelectFolder() {
        File rootDir = new File(System.getProperty("user.dir"));
        File srcDir = new File(rootDir, "src");
        System.out.println("user.dir: " + System.getProperty("user.dir"));
        System.out.println("srcDir: " + srcDir.getAbsolutePath());

        // Ha nincs benne `src`, akkor próbáld meg a rootot
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            // Talán eleve a src-ben vagyunk
            srcDir = rootDir;
        }

        if (!srcDir.exists() || !srcDir.isDirectory()) {
            JOptionPane.showMessageDialog(this, "'src' mappa nem található!", "Hiba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("Használt mappa: " + srcDir.getAbsolutePath());

        // Kizárt mappanevek és fájlok
        String[] excludedNames = {
            "GUI", "interfaces", "logic_classes", "model", "testing", "test_script", "Main.class", "Main.java"
        };

        File[] files = srcDir.listFiles(File::isDirectory);
        if (files == null) {
            JOptionPane.showMessageDialog(this, "Nem sikerült beolvasni a mappákat!", "Hiba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Elérhető mappák, amiket nem zárunk ki
        Set<String> availableFolders = new HashSet<>();
        for (File f : files) {
            String name = f.getName();
            boolean isExcluded = false;
            for (String excluded : excludedNames) {
                if (excluded.equals(name)) {
                    isExcluded = true;
                    break;
                }
            }
            if (!isExcluded) {
                availableFolders.add(name);
            }
        }

        // Megjelenítjük a felhasználónak az elérhető mappák nevét
        String folderName = JOptionPane.showInputDialog(this,
                "Korábban mentett játékok kérem addja meg a mappa nevét a betöltéshez:\n-" + String.join("\n-", availableFolders) + "\n Meik legyen:");

        if (folderName != null && !folderName.trim().isEmpty()) {
            if (availableFolders.contains(folderName.trim())) {
                betoltes = srcDir.getAbsolutePath() + File.separator + folderName.trim();
                JOptionPane.showMessageDialog(this, "Mappa kiválasztva: " + betoltes);
            } else {
                // Ha a mappa nem található
                JOptionPane.showMessageDialog(this, "A megadott mappa nem található.", "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nem adtál meg mappát!", "Hiba", JOptionPane.ERROR_MESSAGE);
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
