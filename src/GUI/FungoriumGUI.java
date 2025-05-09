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

public class FungoriumGUI {
    private final Fungorium fungorium;
    private final List<PlayerGUI> jatekosokGUI;

    public FungoriumGUI(int sor, int oszlop, List<PlayerGUI> playerGUIs) {
        this.jatekosokGUI = playerGUIs;
        this.fungorium = new Fungorium(sor, oszlop);
        addPlayers();

        SwingUtilities.invokeLater(() -> {
            Grid[][] map = fungorium.getMap();

            int rows = map.length;
            int cols = map[0].length;

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
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(rows, cols));
            for (Grid[] gridRow : map) {
                for (Grid currentGrid : gridRow) {
                    JButton button = new JButton(getLabel(currentGrid));
                    if (currentGrid instanceof Lava) {
                        button.setBackground(Color.RED);
                    } else if (currentGrid instanceof TektonElem) {
                        button.setBackground(Color.LIGHT_GRAY);
                    } else {
                        button.setBackground(Color.WHITE);
                    }
                    button.setOpaque(true);
                    button.setBorderPainted(true);
                    centerPanel.add(button);
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

    private void addPlayers() {
        jatekosokGUI.stream()
                .map(gui -> gui.jatekos)
                .forEach(fungorium::addJatekos);
    }
}