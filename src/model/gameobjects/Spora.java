package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Gombasz;

public class Spora extends GameObject {
    int tapertek;
    Gombasz observer;
    Hatas hatas;
    
    public Spora(Grid g, Gombasz gombasz){super(g, gombasz);}
    public Spora(Grid grid, int tapErtek, Gombasz gombasz, Hatas hatas) {
        super(grid, gombasz);
        this.tapertek = tapErtek;
        this.observer = gombasz;
        this.hatas = hatas;

        observer.add(this);
    }
    public int getTap(){return tapertek;}

    @Override
    public void remove() {
        //TODO
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void effektAktival(Rovar rovar) {
        // TODO
    }


}
