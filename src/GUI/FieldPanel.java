package GUI;

import model.gameobjects.*;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

import javax.swing.*;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class FieldPanel extends JPanel {
    Optional<Grid> selected1 = Optional.empty();
    Optional<Grid> selected2 = Optional.empty();

    public FieldPanel(Grid[][] map) {
        super(new GridLayout(map.length, map[0].length));

        for (Grid[] gridRow : map) {
            for (Grid currentGrid : gridRow) {
                var button = new JToggleButton(getLabel(currentGrid));

                Color color;
                if (currentGrid instanceof Lava) {
                    color = Color.RED;
                } else if (currentGrid instanceof TektonElem) {
                    color = Color.LIGHT_GRAY;
                } else {
                    throw new RuntimeException("Unsupported grid type");
                }

                button.setBackground(color);
                button.setBorderPainted(true);
                button.setUI(new MetalToggleButtonUI() {
                    @Override
                    protected Color getSelectColor() {
                        return color.darker();
                    }
                });
                button.addActionListener(e -> {
                    addSelected(currentGrid);
                });

                add(button);
            }
        }
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

    private void addSelected(Grid grid) {
        if (selected1.isEmpty()) selected1 = Optional.of(grid);
        else if (selected2.isEmpty()) selected2 = Optional.of(grid);
    }
}
