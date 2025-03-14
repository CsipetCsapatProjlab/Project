package model.gameobjects;

import interfaces.GameObjectVisitor;
import interfaces.IGombasz;
import model.enums.Hatas;
import model.grid.Grid;

public class Spora extends GameObject {
    int tapertek;
    IGombasz gombaszObserver;
    Hatas hatas;

    public Spora(Grid grid, int tapErtek, IGombasz gObserver, Hatas hatas) {
        super(grid);
        this.tapertek = tapErtek;
        this.gombaszObserver = gObserver;
        this.hatas = hatas;
    }

    @Override
    public void remove() {
        // TODO
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void effektAktival(Rovar rovar) {
        // TODO
    }


}
