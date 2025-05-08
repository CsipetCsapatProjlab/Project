package GUI;

import model.players.Jatekos;

import java.awt.*;

/// Tartalmazza a Játékost, és annak színét
public class PlayerGUI {
    Color colorOfJatekos;
    Jatekos jatekos;

    public PlayerGUI(Jatekos jatekos, Color colorOfJatekos){
        this.jatekos = jatekos;
        this.colorOfJatekos = colorOfJatekos;
    }

    @Override
    public String toString() {
        return jatekos.toString() + " color: " + colorOfJatekos;
    }
}
