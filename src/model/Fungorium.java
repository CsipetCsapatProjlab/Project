package model;

import java.util.List;

import model.enums.Move;
import model.grid.Grid;
import model.players.Jatekos;

public class Fungorium {
    JatekMotor motor;
    Grid[][] map;
    List<Tekton> tektons;

    /**
     * Visszaadja a jatekmotort
     * @return A jatekmotor
     */
    public JatekMotor getMotor() {
        return motor;
    }
    
    /**
     * Letrehozza a bolygot es a jatekhoz egy jatekmotort
     */
    public Fungorium(){
        motor = new JatekMotor();
    }

    public void addJatekos(Jatekos jatekos){
        motor.jatekosHozzaAd(jatekos);
    }

    /**
     * Uj kort indit
     */
    public void ujKor() {
        // TODO
    }

    /**
     * Átadja a megfelelő gridet a játékmotornak
     */
    public void makeMove(int startGridX, int startGridY, int endGridX, int endGridY, Move move) {
        motor.kovetkezoLepes(map[startGridY][startGridX], map[endGridY][endGridX], move);
    }

    /**
     *
     * @param gridX a grid x koordinátája
     * @param gridY a grid y koordinátája
     * @return megfelelő  grid
     */
    public Grid getGrid(int gridX, int gridY){
        return map[gridY][gridX];
    }

    /**
     * Beallitja a bolygo felszinet, a palyat
     * @param g Mezok tombjenek tombje, amibol a palya all
     */
    public void setMap(Grid[][] g){map = g;}

    /**
     * Beallitja a tektonokat
     * @param t
     */
    public void setTektons(List<Tekton> t){tektons = t;}
}
