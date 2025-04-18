package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Gombasz;

public class Spora extends GameObject {
    int tapertek;
    Gombasz gombasz;
    Hatas hatas;
    
    public Spora(Grid g, Gombasz gombasz){super(g, gombasz);}

    /**
     * Letrehozza a sporat
     * @param grid Melyik mezore
     * @param tapErtek Milyen taperteke van
     * @param gombasz Ki birtokolja
     * @param hatas Mi a hatasa
     */
    public Spora(Grid grid, int tapErtek, Gombasz gombasz, Hatas hatas) {
        super(grid, gombasz);
        this.tapertek = tapErtek;
        this.observer = gombasz;
        this.hatas = hatas;

        gombasz.add(this);
    }

    /**
     * Visszaadja a taperteket
     * @return Tapertek
     */
    public int getTap(){return tapertek;}

    /**
     * Megvalositja a GameObject remove() fv.-et a sajat modjan
     */
    @Override
    public void remove() {
        //TODO
    }

    /**
     * Megvalositja a GameObject accept(GameObjectVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    /**
     * Hatas aktivalasa az ot elfogyaszto rovaron
     * @param rovar Elfogyaszto rovar
     */
    public void effektAktival(Rovar rovar) {
        // TODO
    }

    public Gombasz getGombasz(){return gombasz;}
}
