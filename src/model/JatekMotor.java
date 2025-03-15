package model;

import java.util.ArrayList;
import java.util.List;

import model.enums.Move;
import model.grid.Grid;
import model.players.Gombasz;
import model.players.Rovarasz;

public class JatekMotor {
    Fungorium fungorium;
    List<Rovarasz> rovaraszok;
    List<Gombasz>  gombaszok;

    public JatekMotor(List<Rovarasz> r, List<Gombasz> g){
        rovaraszok = r;
        gombaszok = g;
    }
    public void jelenlegiJatekos() {

    }

    public void kovetkezoJatekos(Grid kezdo, Grid cel, Move move) {
        
    }
    public void mentes(){
        System.out.println("A jatek mentese sikeres!");
    }
    public void betoltes(){
        System.out.println("A jatek betoltese sikeres!");
    }
    public void start(){
        rovaraszok = new ArrayList<>();
        gombaszok = new ArrayList<>();
        System.out.println("Uj jatek betoltese sikeres!\nPalya betoltese sikeres!\nTektonok letrehozasa sikeres!\nSporak lehejezese sikeres!");
        valasztas(false);
        valasztas(true);
    }
    public void valasztas(boolean a){
        if(a){
            gombaszok.add(new Gombasz(null, null, null));
            System.out.println("Gombasz kivalasztas sikeres!");
        } else {
            rovaraszok.add(new Rovarasz(null));
            System.out.println("Rovarasz kivalasztas sikeres!");
        }
    }
}
