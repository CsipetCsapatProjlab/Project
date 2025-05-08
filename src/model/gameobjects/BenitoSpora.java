package model.gameobjects;

import model.utils.Constants;
import model.grid.Grid;
import model.players.Gombasz;

public class BenitoSpora extends Spora {

    public BenitoSpora(Grid g, Gombasz gombasz) {
        super(g, gombasz);
    }

    public BenitoSpora(Grid grid, Gombasz gombasz, int tapErtek) {
        super(grid, gombasz,tapErtek);
    }

    /**
     * Hatas aktivalasa az ot elfogyaszto rovaron
     * @param rovar Elfogyaszto rovar
     */
    @Override
    public void effektAktival(Rovar rovar) {
        super.effektAktival(rovar);
        rovar.energia-=rovar.getEnergia()* Constants.rnd.nextInt(5) + 2;
    }
}
