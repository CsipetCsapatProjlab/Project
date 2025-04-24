package model;

import java.util.ArrayList;
import java.util.List;
import model.enums.Move;
import model.grid.Grid;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

public class JatekMotor {
    private List<Jatekos> jatekosok;
    int currentPlayer = 0;

    public JatekMotor(){
        jatekosok = new ArrayList<>();
    }

    public void jatekosHozzaAd(Jatekos jatekos){
        jatekosok.add(jatekos);
    }

    /**
     * Visszaadja ki a jelenlegi jatekos
     * @return
     */
    public Jatekos jelenlegiJatekos() {
        return jatekosok.get(currentPlayer);
    }

    /**
     * Visszaadja ki lesz a kovetkezo jatekos
     */
    public void kovetkezoJatekos(){
        currentPlayer=(currentPlayer+1)%jatekosok.size();
    }

    /**
     * Lep a felhasznalo babujaval
     * @param kezdo Honnan
     * @param cel   Hova
     * @param move  Milyen modon
     */
    public void kovetkezoLepes(Grid kezdo, Grid cel, Move move) {
        try {
            jatekosok.get(currentPlayer).lepes(kezdo, cel, move);
        }catch (Exception e) {}
        currentPlayer++;
    }

    /**
     * Jatekallas mentese
     */
    public void mentes(){
        
    }

    /**
     * Jatekallas betoltese
     */
    public void betoltes(){
        
    }

    /**
     * Elinditja a jatekot az elso jatekossal
     */
    public void start(){
        currentPlayer = 0;
        //kovetkezoJatekos(null, null, null);
    }

    /**
     * Kiosztja a szerepeket a felhasznaloknak es meghatarozza a kezdohelyuket
     */
    public void jatekosValasztas(Grid kezdoPoz, String jatekosNev, String jatekostype){
           try{
            switch (jatekostype){
                case "Rovarasz":
                    Rovarasz j = new Rovarasz((TektonElem) kezdoPoz, jatekosNev);
                    jatekosok.add(j);
                    break;
                case "Gombasz":
                    Gombasz jg = new Gombasz((TektonElem) kezdoPoz, jatekosNev);
                    jatekosok.add(jg);
                    break;
                default:
                    throw new IllegalArgumentException("Rossz jatekostipus!");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Jatekos[] getJatekosok(){
        return jatekosok.toArray(new Jatekos[0]);
    }
}
