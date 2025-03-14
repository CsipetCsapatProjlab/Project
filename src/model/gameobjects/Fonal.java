package model.gameobjects;

import interfaces.GameObjectVisitor;
import interfaces.IGombasz;
import logic_classes.FonalGrowLogic;
import logic_classes.GombaTestPlaceLogic;
import model.grid.Grid;

public class Fonal extends GameObject {
    IGombasz gombaszObserver;
    FonalGrowLogic fonalGrowLogic;
    GombaTestPlaceLogic gombaTestPlaceLogic;

    public Fonal(Grid grid, IGombasz gObserver, FonalGrowLogic fgl, GombaTestPlaceLogic gtp) {
        super(grid);
        this.gombaszObserver = gObserver;
        this.fonalGrowLogic = fgl;
        this.gombaTestPlaceLogic = gtp;
    }

    @Override
    public void remove() {
        // TODO
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void fonalNovesztes(Grid destination) {
        // TODO
    }

    public void gombaTestNovesztes() {
        // TODO
    }
}
