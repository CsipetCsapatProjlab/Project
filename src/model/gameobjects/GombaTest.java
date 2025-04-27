package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.SporaPlaceLogic;
import model.exceptions.IncompatibleGameObjectException;
import model.grid.Grid;
import model.players.Gombasz;

public class GombaTest extends GameObject {
    boolean fejlesztett = false;
    SporaPlaceLogic sporaPlaceLogic=new SporaPlaceLogic(this);

    public Gombasz getGombasz(){return (Gombasz)observer;}
    public boolean getFejlesztett(){return fejlesztett;}
    public void setFejlesztett() {fejlesztett = true;}

    @Override
    public void accept(GameObjectVisitor visitor) {visitor.visit(this);}

    public GombaTest(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        fejlesztett = false;

        gombasz.hozzaAd(this);
    }

    @Override
    public void removeFromGrid() {
        super.removeFromGrid();
        getGombasz().torol(this);
    }


    @Override
    protected String[] getData() {
        return new String[]{
                getClass().getSimpleName() + ": " + getGombasz().getNev(),
                fejlesztett ? "fejlett" : "fejletlen",
        };
    }

    /**
     * Kivalasztott sporat kilovi a kivalasztott mezore
     * @param destination Hova lojje
     * @param spora Melyik sporat
     */
    public void sporaKilo(Grid destination, Spora spora){
       //TODO
    }
}
