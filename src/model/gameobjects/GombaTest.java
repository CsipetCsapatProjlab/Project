package model.gameobjects;

import java.util.LinkedList;

import interfaces.GameObjectVisitor;
import logic_classes.SporaPlaceLogic;
import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.grid.Grid;
import model.players.Gombasz;
import model.utils.Constants;

public class GombaTest extends GameObject {

    LinkedList<Spora> storedSporas = new LinkedList<>();
    double sporaNoves=0.0;
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

    @Override
    public String toStringShort() {
        return "G";
    }

    @Override
    public void forduloUtan() {
        sporaNoves+= Constants.gombaTestSporaGenPerKor;
        if(sporaNoves>=1){
            sporaNoves-=1;
            storedSporas.add(new Spora(getGombasz()));
        }
    }

    /**
     * Kivalasztott sporat kilovi a kivalasztott mezore
     * @param destination Hova lojje
     */
    public void sporaKilo(Grid destination) throws FailedMoveException {
        if(storedSporas.isEmpty()) return;

        Spora sp=storedSporas.pop();
        var grid=sporaPlaceLogic.placeSpora(destination);

        if(grid.isPresent()){
            grid.get().hozzaAd(sp);
            sp.setPosition(grid.get());
        }
        else throw new FailedMoveException("Nem sikerült a sporakilövés",this, Move.Spora_lo);
    }

}
