package GUI;

import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerSelectionPanel extends JPanel {
    List<CustomPanel> panels = new ArrayList<>();
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    public PlayerSelectionPanel(int numPlayers) {
        addPanels(numPlayers);
        refresh();
    }
    private void addPanels(int num) {
        for (int i = 0; i < num; i++) {
            var panel = new CustomPanel();
            panels.add(panel);
            this.add(panel);
        }
    }
    private void refresh() {
        setVisible(false);
        revalidate();
        repaint();
        setVisible(true);
    }
    public void resizeTo(int numPlayers) {
        this.panels.clear();
        this.removeAll();
        addPanels(numPlayers);
        refresh();
    }

    public int getNumPlayers() {
        return panels.size();
    }

    public List<PlayerGUI> getJatekosok() {
        List<PlayerGUI> validPlayers = new ArrayList<>();
        for (var panel : panels) {
            String name = panel.getName();
            String type = panel.getSelection();
            Color color = panel.getColor();

            boolean isValid = name != null && !name.trim().isEmpty() && type != null && color != null;

            if (isValid) {
                Jatekos jatekos = switch (type) {
                    case "Gombász" -> new Gombasz(name);
                    case "Rovarász" -> new Rovarasz(name);
                    default -> throw new RuntimeException();
                };
                validPlayers.add(new PlayerGUI(jatekos, color));
            }
        }
        return validPlayers;
    }
}
