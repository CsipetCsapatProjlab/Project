package model.gameobjects;

import model.utils.Constants;
import model.grid.Grid;
import model.players.Gombasz;

public class GyorsSpora extends Spora {

    public GyorsSpora(Grid g, Gombasz gombasz) {
        super(g, gombasz);
    }

    public GyorsSpora(Grid grid, int tapErtek, Gombasz gombasz) {
        super(grid, gombasz, tapErtek);
    }

    /**
     * Hatas aktivalasa az ot elfogyaszto rovaron
     * @param rovar Elfogyaszto rovar
     */
    @Override
    public void effektAktival(Rovar rovar) {
        super.effektAktival(rovar);
        rovar.energia+= Constants.rnd.nextInt((int) Math.round(rovar.getEnergia()));
    }
}
