package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import model.Fungorium;
import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.exceptions.IncompatibleGameObjectException;
import model.exceptions.InvalidMoveException;
import model.gameobjects.GameObject;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;
import model.players.Jatekos;
import model.utils.ColorUtils;

public class FungoriumGUI extends JFrame {
    private final Fungorium fungorium;
    private List<PlayerGUI> jatekosokGUI;
    private int korokszama;
    private int jelenlegikorokszama = 1;
    private String jatekNev;

    private JButton[][] viewGrid;
    private DefaultListModel<Move> possibleMoves;
    private JList<Move> possibleMovesList;
    private Lepes actualisLepes = new Lepes();
    JLabel current;

    private int rows;
    private int cols;

    public class GridButton extends JButton {
        private int gridx;
        private int gridy;

        public GridButton(int x, int y) {
            gridx = x;
            gridy = y;
            this.addActionListener(this::onButtonPressed);
        }

        public void onButtonPressed(ActionEvent event) {
            if (!actualisLepes.isMoveSet && jelenlegikorokszama != 1) {
                JOptionPane.showMessageDialog(null, "Először válassz ki egy lépést a bal oldali listából.");
                return;
            }

            actualisLepes.addLepes(gridx, gridy);
            highlightSelection();

            if (actualisLepes.isDone()) {
                performMoveIfPossible();
            }
        }
        private void highlightSelection() {
            if (actualisLepes.startx == gridx && actualisLepes.starty == gridy) {
                this.setBackground(Color.YELLOW);
            } else if (actualisLepes.endx == gridx && actualisLepes.endy == gridy) {
                this.setBackground(Color.GREEN);
            }
        }
    }

    private class Lepes{
        private int startx=-1;
        private int starty=-1;
        private int endx=-1;
        private int endy=-1;
        private Move move;
        boolean isMoveSet=false;

        public void addLepes(int x, int y){
            if(x<0 || y<0 || x>=rows || y>=cols) throw new IllegalArgumentException("Hibás index");
            if(!isInBounds(startx,starty)){
                startx = x;
                starty = y;
            }
            else{
                if(!isInBounds(endx,endy)){
                    endx = x;
                    endy = y;
                }
            }
        }
        public boolean isInBounds(int x, int y){
            return x>=0 && x<rows && y>=0 && y<cols;
        }
        public void addMove(Move move){
            this.move = move;
            isMoveSet=true;
        }
        public boolean isDone(){
            if(jelenlegikorokszama == 1){
                return isInBounds(startx,starty) && isInBounds(endx,endy);
            }
            return isInBounds(startx,starty) && isInBounds(endx,endy) && isMoveSet;
        }
    }

    private void performMoveIfPossible(){
        if(actualisLepes.isDone()){
            int x1 = actualisLepes.startx;
            int x2 = actualisLepes.endx;
            int y1 = actualisLepes.starty;
            int y2 = actualisLepes.endy;
            Move move = actualisLepes.move;
            actualisLepes = new Lepes();

            try {
                if (jelenlegikorokszama == 1) {
                    fungorium.makeMove(x1, y1, x2, y2, Move.Kezdo_lepes);
                    fungorium.getMotor().kovetkezoJatekos();

                    int kovetkezoJatekos = fungorium.getMotor().getCurrentPlayerNumber();
                    System.out.println(kovetkezoJatekos);
                    if (kovetkezoJatekos == 0) {
                        jelenlegikorokszama++;
                        fungorium.ujKor();
                    }

                }else{
                    fungorium.makeMove(x1, y1, x2, y2, move);
                }
            } catch (IncompatibleGameObjectException | InvalidMoveException | FailedMoveException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

            viewFrissit();
        }
    }

    private void viewFrissit(){
        palyaFrissit();
        moveListaFrissit();
        jobbPanelFrissit();
        current.setText(fungorium.getMotor().getCurrentPlayer().toString());
    }

    private void moveListaFrissit(){
        possibleMoves.removeAllElements();
        possibleMoves.addAll(Arrays.stream(fungorium.getCurrentPlayer().getMoveTypes()).toList());
    }


    private void palyaFrissit(){
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Grid currentGrid=fungorium.getGrid(y,x);
                List<GameObject> objs = currentGrid.getGameObject();
                StringBuilder label = new StringBuilder();

                label.append("<html>");
                for (GameObject obj : objs) {
                    Jatekos gameObjectOwner=obj.getObserver();
                    var color = jatekosokGUI.stream().filter(e->gameObjectOwner==e.jatekos).findFirst();
                    Color jatekosColor=color.isPresent()?color.get().colorOfJatekos:Color.BLACK;
                    label.append(wrapInColor(obj.toStringShort(), jatekosColor));
                }
                label.append("</html>");

                viewGrid[y][x].setText(label.toString());
                if (currentGrid instanceof Lava) {
                    viewGrid[y][x].setBackground(Color.RED);
                } else if (currentGrid instanceof TektonElem) {
                    viewGrid[y][x].setBackground(Color.LIGHT_GRAY);
                } else {
                    viewGrid[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void jobbPanelFrissit() {
        SwingUtilities.invokeLater(() -> {
            Jatekos currentPlayer = fungorium.getMotor().getCurrentPlayer();
            for (PlayerGUI playerGUI : jatekosokGUI) {
                playerGUI.frissit();
                if (playerGUI.getJatekos().equals(currentPlayer)) {
                    playerGUI.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); // piros keret
                } else {
                    playerGUI.setBorder(null); // eltávolítja a keretet a többiektől
                }
            }
        });
    }

    public void MoveListSelectionChanged(ListSelectionEvent evt) {
        int selectedIx = evt.getFirstIndex();
        if(selectedIx>=0 && selectedIx<possibleMoves.getSize()){
            actualisLepes.addMove(possibleMoves.get(selectedIx));
            performMoveIfPossible();
        }
    }

    public JPanel initBottomPanel(){
        var bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JPanel leftButtonsPanel = new JPanel();
            leftButtonsPanel.setLayout(new BoxLayout(leftButtonsPanel, BoxLayout.X_AXIS));
            leftButtonsPanel.add(initSaveButton());
            leftButtonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            leftButtonsPanel.add(initSkipButton());
            bottomPanel.add(leftButtonsPanel, BorderLayout.WEST);
        return bottomPanel;
    }

    public JPanel initLeftPanel(){
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel currentLabel = new JLabel("Név: Típus; Pontszám");
        currentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(currentLabel);
        
        current = new JLabel(fungorium.getMotor().getCurrentPlayer().toString());
        current.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(current);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel leftTitle = new JLabel("Lehetséges lépések:");
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(leftTitle);

        possibleMoves = new DefaultListModel<>();
        possibleMovesList = new JList<>(possibleMoves);
        leftPanel.add(getCurrentPlayerGUI());
        leftPanel.add(possibleMovesList);

        possibleMovesList.addListSelectionListener(this::MoveListSelectionChanged);

        return leftPanel;
    }

    public JPanel initRightPanel(){
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 20));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel rightTitle = new JLabel("Játékosok:");
        rightPanel.add(rightTitle);

        rightPanel.add(Box.createVerticalStrut(5));
        for (PlayerGUI playerGUI : jatekosokGUI) {
            rightPanel.add(playerGUI);
            rightPanel.add(Box.createVerticalStrut(10));
        }
        if (!jatekosokGUI.isEmpty()) {
            rightPanel.remove(rightPanel.getComponentCount() - 1);
        }
        rightPanel.add(Box.createVerticalGlue());

        rightPanel.add(initBottomPanel());

        return rightPanel;
    }

    public JPanel initCenterPanel(){
        viewGrid = new JButton[rows][cols];
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(rows, cols));
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                viewGrid[y][x] = new GridButton(y,x);
                viewGrid[y][x].setOpaque(true);
                viewGrid[y][x].setBorderPainted(true);
                centerPanel.add(viewGrid[y][x]);
            }
        }
        centerPanel.setMinimumSize(new Dimension(800,600));

        return centerPanel;
    }

    public FungoriumGUI(int sor, int oszlop, int korokszama, String nev, List<PlayerGUI> playerGUIs) {
        super("Fungorium");
        this.korokszama = korokszama;
        this.jatekNev = nev;
        this.jatekosokGUI = playerGUIs;
        this.fungorium = new Fungorium(sor, oszlop);
        this.rows = sor;
        this.cols = oszlop;
        addPlayers();

        initFrame();
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        SwingUtilities.invokeLater(() -> {
            // --------- BAL PANEL: lépés lehetőségek ---------
            JPanel leftPanel = initLeftPanel();

            // --------- JOBB PANEL: játékosok és pontszámaik ---------
            JPanel rightPanel = initRightPanel();

            // --------- KÖZÉPSŐ GRID: pálya gombokkal ---------
            JPanel centerPanel = initCenterPanel();

            add(leftPanel, BorderLayout.WEST);
            add(centerPanel, BorderLayout.CENTER);
            add(rightPanel, BorderLayout.EAST);

            pack();
            setVisible(true);

            viewFrissit();
        });
    }

    private JButton initSaveButton() {
        JButton saveButton = new JButton("Mentés");
        saveButton.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog(null, "Add meg a fájlnevet a mentéshez:");
            if (filename != null && !filename.trim().isEmpty()) {
                try {
                    JOptionPane.showMessageDialog(null, "Játék elmentve: " + filename.trim());
                    this.mentes(filename.trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hiba történt a mentés során: " + ex.getMessage());
                }
            }
        });
        return saveButton;
    }
    private JButton initSkipButton(){
        JButton skipButton = new JButton("SKIP");
        skipButton.addActionListener(e -> {
            fungorium.getMotor().kovetkezoJatekos();
            int kovetkezoJatekos = fungorium.getMotor().getCurrentPlayerNumber();

            if (kovetkezoJatekos == 0) {
                jelenlegikorokszama++;
                try {
                    this.mentes(jatekNev);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                fungorium.ujKor();
            }

            if (jelenlegikorokszama > korokszama) {
                JOptionPane.showMessageDialog(this, "Játék vége! A játékosok győztek!");
            }

            viewFrissit();
        });
        return skipButton;
    }

    public FungoriumGUI(String betolString) {
        this.fungorium = new Fungorium(betolString);
        Path path = Paths.get(betolString);
        this.jatekNev = path.getFileName().toString(); 
        System.out.println(jatekNev);

        this.jatekosokGUI = fungorium.getPlayerslist().stream()
                .map(PlayerGUI::new)
                .toList();
        betultszin(betolString + "/szinek.txt");
        betultkorok(betolString + "/korok.txt");

        var shape = fungorium.getShape();
        rows = shape[0];
        cols = shape[1];

        initFrame();
    }

    private String wrapInColor(String label, Color color) {
        // A font méretet dinamikusan számoljuk: kisebb pályán nagyobb szöveg
        int baseSize = 20;
        int scaleFactor = Math.max(rows, cols); // nagyobb dimenzió határozza meg
        int size = Math.max(8, baseSize - (scaleFactor / 10)); // alsó limit 8

        return String.format("<font size='%d' color='%s'>%s</font>", size, ColorUtils.colorToHex(color), label);
    }

    private void addPlayers() {
        jatekosokGUI.stream()
                .map(gui -> gui.jatekos)
                .forEach(fungorium::addJatekos);
    }

    public void mentes(String filename) throws IOException {
        // Meghatározza a projekt gyökerét a forrásmappa (src) alapján
        Path currentDir = Paths.get("").toAbsolutePath(); // ez a working dir, pl. Project/src
        Path projectRoot = currentDir;

        // Ha a working dir véletlenül a src, akkor visszalépünk egyet a Project gyökérbe
        if (currentDir.getFileName().toString().equals("src")) {
            projectRoot = currentDir.getParent();
        }

        // Létrehozza a teljes mentési útvonalat: Project/Mentesek/filename
        Path saveDir = projectRoot.resolve("Mentesek").resolve(filename);
        Files.createDirectories(saveDir); // biztosítja, hogy létezik

        // Hívja a fungorium mentést a megfelelő útvonallal
        fungorium.mentes(saveDir.toString());

        System.out.println("korokszama = " + korokszama + ", jelenlegikorokszama = " + jelenlegikorokszama);
        //jelenlegi kor száma és az összes körök száma
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveDir.resolve("korok.txt").toFile()))) {
            writer.printf("%d;%d%n", korokszama, jelenlegikorokszama);
        }
        // Színek mentése
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveDir.resolve("szinek.txt").toFile()))) {
            for (PlayerGUI j : jatekosokGUI) {
                Color c = j.getszin(); // Feltételezve, hogy van ilyen getter
                writer.printf("%d;%d;%d%n", c.getRed(), c.getGreen(), c.getBlue());
            }
        }
    }

    public PlayerGUI getCurrentPlayerGUI() {
        var currentPlayer = fungorium.getMotor().getCurrentPlayer();
        return jatekosokGUI.stream()
                .filter(jatekosGUI -> jatekosGUI.getJatekos() == currentPlayer)
                .findAny()
                .get();
    }

    public void betultszin(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < jatekosokGUI.size()) {
                String[] parts = line.split(";");
                int r = Integer.parseInt(parts[0]);
                int g = Integer.parseInt(parts[1]);
                int b = Integer.parseInt(parts[2]);
                Color color = new Color(r, g, b);
                jatekosokGUI.get(index).setszin(color);
                index++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Hiba a fájl betöltésekor: " + e.getMessage(), e);
        }
    }

    public void betultkorok(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < jatekosokGUI.size()) {
                String[] parts = line.split(";");
                korokszama = Integer.parseInt(parts[0]);
                jelenlegikorokszama = Integer.parseInt(parts[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Hiba a fájl betöltésekor: " + e.getMessage(), e);
        }
    }

}