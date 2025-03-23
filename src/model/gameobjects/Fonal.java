package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.FonalGrowLogic;
import logic_classes.GombaTestPlaceLogic;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Gombasz;

public class Fonal extends GameObject {
    Gombasz gombasz;
    FonalGrowLogic fonalGrowLogic;
    GombaTestPlaceLogic gombaTestPlaceLogic;

    public Fonal(Grid grid, Gombasz gombasz, FonalGrowLogic fgl, GombaTestPlaceLogic gtp) {
        super(grid, gombasz);
        this.gombasz = gombasz;
        this.fonalGrowLogic = fgl;
        this.gombaTestPlaceLogic = gtp;

        gombasz.add(this);
    }

    @Override
    public void remove() {
        grid.torol(this);
        grid = null;
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void fonalNovesztes(Grid destination) {
        fonalGrowLogic.noveszt(destination);
    }

    public void gombaTestNovesztes(Grid grid) {
        if(grid.getHatas() == Hatas.NO_GOMBATEST) return;
        GombaTest g = new GombaTest(grid, gombasz);
        grid.hozzaAd(g);
        gombasz.addTest(g);
        //gombaszObserver.addTest(g);
    }
}
