package model.gameobjects;

import interfaces.GameObjectVisitor;
import interfaces.IGombasz;
import logic_classes.SporaPlaceLogic;
import model.grid.Grid;

public class GombaTest extends GameObject {
    private IGombasz gombaszObserver;
    boolean fejlesztett = false;
    SporaPlaceLogic sporaPlaceLogic;

    public GombaTest(Grid grid, IGombasz gObserver,  SporaPlaceLogic spl) {
        super(grid);
        this.gombaszObserver = gObserver;
        this.sporaPlaceLogic = spl;
    }

    @Override
    public void remove() {
        // TODO
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void setFejlesztett(boolean fejlesztett) {
        this.fejlesztett = fejlesztett;
    }

    public void sporaKilo(Grid destination, Spora spora) {
        // TODO
    }
}
