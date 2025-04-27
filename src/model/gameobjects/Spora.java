package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Gombasz;
import model.utils.Constants;

public class Spora extends GameObject {
    int tapertek;

    public int getTapertek(){return tapertek;}
    public Gombasz getGombasz(){return (Gombasz)observer;}

    @Override
    public void accept(GameObjectVisitor visitor) {visitor.visit(this);}

    public Spora(Grid g, Gombasz gombasz){
        super(g, gombasz);
        this.tapertek = Constants.sporaTapanyag;
    }

    public Spora(Grid grid,  Gombasz gombasz, int tapErtek) {
        super(grid, gombasz);
        this.tapertek = tapErtek;
        gombasz.hozzaAd(this);
    }

    @Override
    public void removeFromGrid() {
        super.removeFromGrid();
        getGombasz().torol(this);
    }

    public void effektAktival(Rovar rovar) {}

    @Override
    protected String[] getData() {
        return new String[]{
                getClass().getSimpleName() + ": " + getGombasz().getNev(),
                "Tapertek: " + tapertek,
        };
    }
}
