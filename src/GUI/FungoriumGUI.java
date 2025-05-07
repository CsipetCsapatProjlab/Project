package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Fungorium;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.players.Jatekos;

public class FungoriumGUI {
    Fungorium model;
    FungoriumController controller;
    Map<Jatekos,Color> jatekosSzin;
    PalyaPanel palyaPanel;
    JatekosValasztoPanel jatekosValasztoPanel;
    // Csak nem lesz több mint 12 játékos, nem?
    private static Map<Jatekos,Color> generateColors(Map<Jatekos,Color> current, List<Jatekos> jatekosok){
         Color[] DISTINCT_COLORS = {
                Color.RED,
                Color.BLUE,
                Color.GREEN,
                Color.ORANGE,
                Color.MAGENTA,
                Color.CYAN,
                Color.PINK,
                Color.YELLOW,
                new Color(0, 128, 0), // Dark green
                new Color(128, 0, 128), // Purple
                new Color(0, 128, 128), // Teal
                new Color(128, 128, 0)  // Olive
        };
         if(current==null) {current = new HashMap<>();}
         for(Jatekos j:jatekosok){
             if(!current.containsKey(j)){
                 for (Color distinctColor : DISTINCT_COLORS) {
                     if (!current.containsValue(distinctColor)) {
                         current.put(j, distinctColor);
                     }
                 }
             }
         }
         return current;
    }

    public FungoriumGUI(Fungorium f2) {
        model = f2;
        FungoriumController controller = new FungoriumController(model);
        jatekosSzin = generateColors(null,model.getMotor().getJatekosok());
        palyaPanel = new PalyaPanel(model,controller::onGridClicked,jatekosSzin);
        jatekosValasztoPanel = new JatekosValasztoPanel(model);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fungorium Grid");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());


            //frame.add(palyaPanel, BorderLayout.CENTER);
            frame.add(jatekosValasztoPanel, BorderLayout.CENTER);

            frame.pack();
            frame.setVisible(true);

            /*
            char[][] test = getTestField(model);
            Grid[][] map = getMapField(model);

            int rows = test.length;
            int cols = test[0].length;

            JFrame frame = new JFrame("Fungorium Grid");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // --------- BAL PANEL: lépés lehetőségek ---------
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            Jatekos current = model.getCurrentPlayer();

            JLabel leftTitle = new JLabel("Lehetséges lépések:");
            leftPanel.add(leftTitle);

            if (current instanceof model.players.Rovarasz) {
                leftPanel.add(new JLabel("Lépés"));
                leftPanel.add(new JLabel("Evés"));
                leftPanel.add(new JLabel("Vágás"));
            } else if (current instanceof model.players.Gombasz) {
                leftPanel.add(new JLabel("Fonál növesztés"));
                leftPanel.add(new JLabel("Spóra lövés"));
                leftPanel.add(new JLabel("Gombatest növesztés"));
                leftPanel.add(new JLabel("Rovar evés"));
            } else {
                leftPanel.add(new JLabel("(Ismeretlen játékostípus)"));
            }

            // --------- JOBB PANEL: játékosok és pontszámaik ---------
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            JLabel rightTitle = new JLabel("    Játékosok:  ");
            rightPanel.add(rightTitle);
            for (Jatekos j : model.getPlayers()) {
                JPanel playerRow = new JPanel(new BorderLayout());
                JLabel nameLabel = new JLabel("   " + j.getNev());
                JLabel scoreLabel = new JLabel(String.valueOf(j.getPoints() + "   "), SwingConstants.RIGHT);

                nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
                scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                playerRow.add(nameLabel, BorderLayout.WEST);
                playerRow.add(scoreLabel, BorderLayout.EAST);

                rightPanel.add(playerRow);
            }
            // --------- KÖZÉPSŐ GRID: pálya gombokkal ---------
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(rows, cols));
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    JButton button = new JButton(getLabel(map[i][j]));
                    char cell = test[i][j];
                    if (cell == '#') {
                        button.setBackground(Color.RED);
                    } else if (cell >= '1' && cell <= '4') {
                        button.setBackground(Color.LIGHT_GRAY);
                    } else {
                        button.setBackground(Color.WHITE);
                    }
                    button.setOpaque(true);
                    button.setBorderPainted(false);
                    centerPanel.add(button);
                }
            }

            frame.add(leftPanel, BorderLayout.WEST);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(rightPanel, BorderLayout.EAST);

            frame.pack();
            frame.setVisible(true)*/
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

    private static char[][] getTestField(Fungorium f) {
        try {
            Field field = Fungorium.class.getDeclaredField("test");
            field.setAccessible(true);
            return (char[][]) field.get(f);
        } catch (Exception e) {
            e.printStackTrace();
            return new char[0][0];
        }
    }

    private static Grid[][] getMapField(Fungorium f) {
        try {
            Field field = Fungorium.class.getDeclaredField("map");
            field.setAccessible(true);
            return (Grid[][]) field.get(f);
        } catch (Exception e) {
            e.printStackTrace();
            return new Grid[0][0];
        }
    }
}