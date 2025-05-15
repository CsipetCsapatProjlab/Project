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
            actualisLepes.addLepes(gridx,gridy);
            performMoveIfPossible();
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
            return isInBounds(startx,starty) && isInBounds(endx,endy) && isMoveSet;
        }
    }

    private void performMoveIfPossible(){
        if(actualisLepes.isDone()){
            int x1=actualisLepes.startx;
            int x2=actualisLepes.endx;
            int y1=actualisLepes.starty;
            int y2=actualisLepes.endy;
            Move move=actualisLepes.move;
            actualisLepes=new Lepes();
            try{
                fungorium.makeMove(x1,y1,x2,y2,move);
            } catch (IncompatibleGameObjectException | InvalidMoveException | FailedMoveException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            viewFrissit();
        }
    }

    private void viewFrissit(){
        palyaFrissit();
        moveListaFrissit();
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
                    label.append(wrapInColor(obj.toStringShort(),jatekosColor,20));
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
        current.setText(fungorium.getMotor().getCurrentPlayer().toString() + ":");
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

        JLabel leftTitle = new JLabel("Lehetséges lépések:");
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(leftTitle);

        current = new JLabel(fungorium.getMotor().getCurrentPlayer().toString() + ":");
        current.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(current);

        possibleMoves = new DefaultListModel<Move>();
        possibleMovesList = new JList<>(possibleMoves);
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

    public FungoriumGUI(int sor, int oszlop, List<PlayerGUI> playerGUIs) {
        super("Fungorium");
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
                    fungorium.mentes(filename.trim());
                    JOptionPane.showMessageDialog(null, "Játék elmentve: " + filename.trim());
                    mentes(filename.trim() + "/szinek.txt");
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
                viewFrissit();
            });
        return skipButton;
    }

    public FungoriumGUI(String betolString) {
        this.fungorium = new Fungorium(betolString);
        addPlayers();
        betultszin(betolString + "/szinek.txt");

        var shape = fungorium.getShape();
        rows = shape[0];
        cols = shape[1];

        initFrame();
    }

    private static String wrapInColor(String label, Color color, int size) {
        return String.format("<font size='%s' color='%s'>%s</font>", size,ColorUtils.colorToHex(color),label);
    }

    private void addPlayers() {
        jatekosokGUI.stream()
                .map(gui -> gui.jatekos)
                .forEach(fungorium::addJatekos);
    }

    public void mentes(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (PlayerGUI j : jatekosokGUI) {
                Color c = j.getszin(); // Feltételezve, hogy van ilyen getter
                writer.printf("%d;%d;%d%n", c.getRed(), c.getGreen(), c.getBlue());
            }
        }
    }

    public PlayerGUI getCurrentPlayerGUI(List<PlayerGUI> jatekosokGUI) {
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
}