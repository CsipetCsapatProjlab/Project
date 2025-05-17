package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class StartMenuGUI extends JFrame {

    private JTextField widthField;
    private JTextField heightField;
    PlayerSelectionPanel  playerSelectionPanel = new PlayerSelectionPanel(5);
    private String betoltes = null;
    private JSpinner korokSpinner;
    private JTextField jatekNevField;

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
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(new JLabel("A játék neve:"));
        jatekNevField = new JTextField(15);
        panel.add(jatekNevField);

        panel.add(new JLabel("Körök száma:"));
        korokSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 1000, 1)); // alapértelmezett 10, min 1, max 1000
        panel.add(korokSpinner);

        panel.add(new JLabel("Pályaméret:"));
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        widthField = new JTextField(3);
        heightField = new JTextField(3);
        sizePanel.add(widthField);
        sizePanel.add(new JLabel("x"));
        sizePanel.add(heightField);
        panel.add(sizePanel);

        JSpinner jatekosSpinner = new JSpinner(new SpinnerNumberModel(playerSelectionPanel.getNumPlayers(), 2, 100, 1));
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
        // Meghatározza a projekt gyökerét
        Path currentDir = Paths.get("").toAbsolutePath();
        Path projectRoot = currentDir;

        // Ha a working dir "src", visszalépünk a Project gyökérbe
        if (currentDir.getFileName().toString().equals("src")) {
            projectRoot = currentDir.getParent();
        }

        // Mentesek mappa elérési útvonala
        Path mentesekDir = projectRoot.resolve("Mentesek");
        File srcDir = mentesekDir.toFile();

        if (!srcDir.exists() || !srcDir.isDirectory()) {
            JOptionPane.showMessageDialog(this, "'Mentesek' mappa nem található!", "Hiba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("Használt mappa: " + srcDir.getAbsolutePath());

        File[] files = srcDir.listFiles(File::isDirectory);
        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(this, "Nincsenek elérhető mentések!", "Hiba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Set<String> availableFolders = new HashSet<>();
        for (File f : files) {
            availableFolders.add(f.getName());
        }

        String folderName = JOptionPane.showInputDialog(this,
            "Korábban mentett játékok – add meg a mappa nevét a betöltéshez:\n-" + String.join("\n-", availableFolders) + "\nMelyik legyen:");

        if (folderName != null && !folderName.trim().isEmpty()) {
            folderName = folderName.trim();
            if (availableFolders.contains(folderName)) {
                betoltes = srcDir.getAbsolutePath() + File.separator + folderName;
                JOptionPane.showMessageDialog(this, "Mappa kiválasztva: " + betoltes);
            } else {
                JOptionPane.showMessageDialog(this, "A megadott mappa nem található.", "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nem adtál meg mappát!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onStartPressed() {
        int korokSzama = (int) korokSpinner.getValue();
        String jatekNev = jatekNevField.getText().trim();
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
            FungoriumGUI GUI = new FungoriumGUI(sor, oszlop, korokSzama, jatekNev, jatekosokGui);
        }
    
        System.out.println("Játék indul ezekkel a játékosokkal:");
        for (var jatekosGui : jatekosokGui) {
            System.out.println(jatekosGui);
        }


        // Továbblépés például új ablak vagy játék logika elindítása itt

        this.setVisible(false);
    }
    
}
