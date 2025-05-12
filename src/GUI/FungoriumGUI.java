package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;

import model.Fungorium;
import model.enums.Move;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;
import model.players.Jatekos;
import model.utils.ColorUtils;

public class FungoriumGUI {
    private final Fungorium fungorium;
    private List<PlayerGUI> jatekosokGUI;
    private JButton[][] viewGrid;

    private int rows;
    private int cols;

    private class Lepes{
        public int startx=-1,starty=-1,endx=-1,endy=-1;
        public Move move;
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

    private void palyaFrissit(){
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Grid currentGrid=fungorium.getGrid(y,x);
                List<GameObject> objs = currentGrid.getGameObject();
                StringBuilder label = new StringBuilder();

                for (GameObject obj : objs) {
                    Jatekos gameObjectOwner=obj.getObserver();
                    var color = jatekosokGUI.stream().filter(e->gameObjectOwner==e.jatekos).findFirst();
                    Color jatekosColor=color.isPresent()?color.get().colorOfJatekos:Color.BLACK;
                    label.append(wrapInColor(obj.toStringShort(),jatekosColor));
                }

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

    public FungoriumGUI(int sor, int oszlop, List<PlayerGUI> playerGUIs) {
        this.jatekosokGUI = playerGUIs;
        this.fungorium = new Fungorium(sor, oszlop);
        addPlayers();

        SwingUtilities.invokeLater(() -> {
            Grid[][] map = fungorium.getMap();

            rows = map.length;
            cols = map[0].length;


            JFrame frame = new JFrame("Fungorium Grid");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // --------- BAL PANEL: lépés lehetőségek ---------
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            Jatekos current = fungorium.getCurrentPlayer();

            JLabel leftTitle = new JLabel("Lehetséges lépések:");
            leftPanel.add(leftTitle);

            JList<Move> possibleMoves = new JList<>(current.getMoveTypes());
            leftPanel.add(possibleMoves);

            // --------- JOBB PANEL: játékosok és pontszámaik ---------
            JPanel rightPanel = new JPanel();
            rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 20));
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            JLabel rightTitle = new JLabel("Játékosok:");
            rightPanel.add(rightTitle);
            jatekosokGUI.forEach(rightPanel::add);

            // --------- KÖZÉPSŐ GRID: pálya gombokkal ---------
            viewGrid = new JButton[rows][cols];
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(rows, cols));
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    Grid currentGrid=map[y][x];
                    viewGrid[y][x] = new JButton(getLabel(currentGrid));
                    if (currentGrid instanceof Lava) {
                        viewGrid[y][x].setBackground(Color.RED);
                    } else if (currentGrid instanceof TektonElem) {
                        viewGrid[y][x].setBackground(Color.LIGHT_GRAY);
                    } else {
                        viewGrid[y][x].setBackground(Color.WHITE);
                    }
                    viewGrid[y][x].setOpaque(true);
                    viewGrid[y][x].setBorderPainted(true);
                    centerPanel.add(viewGrid[y][x]);
                }
            }

            frame.add(leftPanel, BorderLayout.WEST);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(rightPanel, BorderLayout.EAST);

            frame.pack();
            frame.setVisible(true);
        });
    }

    private static String getLabel(Grid grid) {
        if (grid == null) return "";
        List<GameObject> objs = grid.getGameObject();
        StringBuilder label = new StringBuilder();
        for (GameObject obj : objs) {
            if (obj instanceof Rovar) label.append("R");
            else if (obj instanceof GombaTest) label.append("G");
            else if (obj instanceof Spora) label.append("S");
            else if (obj instanceof Fonal) label.append("F");
        }
        return label.toString();
    }

    private static String wrapInColor(String label, Color color) {
        return String.format("<font color='%s'>%s</font>", ColorUtils.colorToHex(color),label);
    }

    private void addPlayers() {
        jatekosokGUI.stream()
                .map(gui -> gui.jatekos)
                .forEach(fungorium::addJatekos);
    }
}