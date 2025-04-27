package model.gameobjects;

import model.utils.Constants;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Gombasz;
import model.utils.Constants;

public class GyorsSpora extends Spora {

    public GyorsSpora(Grid g, Gombasz gombasz) {
        super(g, gombasz);
    }

    /**
     * Letrehozza a sporat
     *
     * @param grid     Melyik mezore
     * @param tapErtek Milyen taperteke van
     * @param gombasz  Ki birtokolja
     * @param hatas    Mi a hatasa
     */
    public GyorsSpora(Grid grid, int tapErtek, Gombasz gombasz, Hatas hatas) {
        super(grid, tapErtek, gombasz, hatas);
    }

    /**
     * Hatas aktivalasa az ot elfogyaszto rovaron
     *
     * @param rovar Elfogyaszto rovar
     */
    @Override
    public void effektAktival(Rovar rovar) {
        super.effektAktival(rovar);
        rovar.energia+= Constants.rnd.nextInt((int) Math.round(rovar.getEnergia()));
    }
}
