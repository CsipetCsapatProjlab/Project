package model;

import java.util.List;
import model.grid.Grid;

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

    /**
     * Uj kort indit
     */
    public void ujKor() {
        // TODO
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
