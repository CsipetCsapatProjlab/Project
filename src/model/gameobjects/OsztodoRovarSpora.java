package model.gameobjects;

import model.grid.Grid;
import model.players.Gombasz;

public class OsztodoRovarSpora extends Spora{
    public OsztodoRovarSpora(Grid g, Gombasz gombasz) {
        super(g, gombasz);
    }

    public OsztodoRovarSpora(Grid grid, int tapErtek, Gombasz gombasz) {
        super(grid, gombasz, tapErtek);
    }

    @Override
    public void effektAktival(Rovar rovar) {
        super.effektAktival(rovar); // Minden előtte lévőt is megcsinálja
        Rovar.CloneRovar(rovar); // Leklónoljuk a rovart.
    }


}
