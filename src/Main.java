import model.Fungorium;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Rovarasz;
import model.Fungorium;
import testing.CommandLine;
import testing.Tests;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Fungorium f = new Fungorium();
        Gombasz g = new Gombasz((TektonElem) f.getGrid(0, 0),"g");
        Rovarasz r = new Rovarasz((TektonElem) f.getGrid(0, 1),"g");
        f.addJatekos(g);
        f.addJatekos(r);
        System.out.println(f);
        System.out.println(f.getSzigetSzam());
        f.ujKor();
    }
}