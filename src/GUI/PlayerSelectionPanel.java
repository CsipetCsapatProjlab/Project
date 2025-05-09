package GUI;

import javax.swing.*;
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
            validPlayers.add(panel.createPlayerGUI());
        }
        return validPlayers;
    }
}
