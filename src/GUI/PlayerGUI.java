package GUI;

import model.players.Jatekos;

import javax.swing.*;
import java.awt.*;

/// Tartalmazza a Játékost, és annak színét
public class PlayerGUI extends JPanel {
    Color colorOfJatekos;
    Jatekos jatekos;
    JLabel nameLabel;
    JLabel scoreLabel;

    public PlayerGUI(Jatekos jatekos, Color colorOfJatekos) {
        super(new BorderLayout());
        this.jatekos = jatekos;
        this.colorOfJatekos = colorOfJatekos;

        nameLabel = new JLabel(jatekos.getNev(), SwingConstants.LEFT);
        scoreLabel = new JLabel(Integer.toString(jatekos.getPoints()), SwingConstants.RIGHT);

        nameLabel.setForeground(colorOfJatekos);
        scoreLabel.setForeground(colorOfJatekos);

        add(nameLabel, BorderLayout.WEST);
        add(scoreLabel, BorderLayout.EAST);
    }

    public PlayerGUI(Jatekos jatekos) {
        super(new BorderLayout());
        this.jatekos = jatekos;

        nameLabel = new JLabel(jatekos.getNev(), SwingConstants.LEFT);
        scoreLabel = new JLabel(Integer.toString(jatekos.getPoints()), SwingConstants.RIGHT);

        add(nameLabel, BorderLayout.WEST);
        add(scoreLabel, BorderLayout.EAST);
    }

    @Override
    public String toString() {
        return jatekos.toString() + " color: " + colorOfJatekos;
    }

    public Color getszin(){
        return colorOfJatekos;
    }

    public void setszin(Color uj){
        colorOfJatekos = uj;
        nameLabel.setForeground(colorOfJatekos);
        scoreLabel.setForeground(colorOfJatekos);
        this.repaint();
    }
}
