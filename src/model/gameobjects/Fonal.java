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

    public Fonal(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        this.gombasz = gombasz;
        this.fonalGrowLogic = new FonalGrowLogic(this);
        this.gombaTestPlaceLogic = new GombaTestPlaceLogic();

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

    public void fonalNovesztes(Grid destination) throws Exception {
        fonalGrowLogic.noveszt(destination);
    }

    public void gombaTestNovesztes(Grid grid) {


    }
}
