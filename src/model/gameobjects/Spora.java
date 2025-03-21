package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Gombasz;

public class Spora extends GameObject {
    int tapertek;
    Gombasz gombasz;
    Hatas hatas;
    
    public Spora(Grid g){super(g);}
    public Spora(Grid grid, int tapErtek, Gombasz gombasz, Hatas hatas) {
        super(grid);
        this.tapertek = tapErtek;
        this.gombasz = gombasz;
        this.hatas = hatas;
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
