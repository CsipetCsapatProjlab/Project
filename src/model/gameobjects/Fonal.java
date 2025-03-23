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
        super(grid);
        this.gombasz = gombasz;
        this.fonalGrowLogic = fgl;
        this.gombaTestPlaceLogic = gtp;
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
        Fonal f = new Fonal(destination, gombasz, fonalGrowLogic, gombaTestPlaceLogic);
        destination.hozzaAd(f);
        gombasz.addFonal(f);
        //gombaszObserver.addFonal(this);
    }

    public void gombaTestNovesztes(Grid grid) {
        if(grid.getHatas() == Hatas.NO_GOMBATEST) return;
        GombaTest g = new GombaTest(grid, gombasz);
        grid.hozzaAd(g);
        gombasz.addTest(g);
        //gombaszObserver.addTest(g);
    }
}
