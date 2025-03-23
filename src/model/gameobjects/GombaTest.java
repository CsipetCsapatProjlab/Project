package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.SporaPlaceLogic;
import model.grid.Grid;
import model.players.Gombasz;

public class GombaTest extends GameObject {
    private Gombasz gombasz;
    boolean fejlesztett = false;
    SporaPlaceLogic sporaPlaceLogic=new SporaPlaceLogic();

    public GombaTest(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        this.gombasz = gombasz;
    }

    @Override
    public void remove() {
        //TODO
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }
    public boolean getFejlesztett(){return fejlesztett;}

    public void setFejlesztett() {
        fejlesztett = true;
    }

    public void sporaKilo(Grid destination, Spora spora) {
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
