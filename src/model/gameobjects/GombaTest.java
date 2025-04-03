package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.SporaPlaceLogic;
import model.exceptions.IncompatibleGameObjectException;
import model.grid.Grid;
import model.players.Gombasz;

public class GombaTest extends GameObject {
    private Gombasz gombasz;
    boolean fejlesztett = false;
    SporaPlaceLogic sporaPlaceLogic=new SporaPlaceLogic(this);

    /**
     * Letrehozza a testet
     * @param grid Melyik mezore
     * @param gombasz Ki birtokolja
     */
    public GombaTest(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        this.gombasz = gombasz;
    }

    /**
     * Megvalositja a GameObject remove() fv.-et a sajat modjan
     */
    @Override
    public void remove() {
        //TODO
    }

    /**
     * Megvalositja a GameObject accept() fv.-et a sajat modjan
     */
    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    /**
     * Visszaadja a test fejlesztesi szintjet
     * @return Fejlesztett-e a test
     */
    public boolean getFejlesztett(){return fejlesztett;}

    /**
     * Fejleszti a testet
     */
    public void setFejlesztett() {
        fejlesztett = true;
    }

    /**
     * Kivalasztott sporat kilovi a kivalasztott mezore
     * @param destination Hova lojje
     * @param spora Melyik sporat
     */
    public void sporaKilo(Grid destination, Spora spora) throws IncompatibleGameObjectException {
        /*int a = 0;
        for (int i = 0; i < gombasz.getSporas().size(); i++){
            if(gombasz.getSporas() == spora) a++;
        }
        if(a < gombasz.getSporas().size()) return;
        gombasz.getSporas().torol(gombasz.getSporas().get(a));*/
        grid.torol(spora);
        destination.hozzaAd(spora);
    }
}
