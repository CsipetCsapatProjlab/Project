package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.RovarConsumeLogic;
import logic_classes.RovarMoveLogic;
import model.utils.CONSTANTS;
import model.enums.Hatas;
import model.exceptions.IncompatibleGameObjectException;
import model.exceptions.InvalidMoveException;
import model.grid.Grid;
import model.players.Rovarasz;

public class Rovar extends GameObject{
    Rovarasz rovarasz;
    RovarConsumeLogic rovarConsumeLogic;
    RovarMoveLogic rovarMoveLogic;
    double energia;

    public Rovar(Rovar other) {
        super(other.grid,other.rovarasz);
        energia = CONSTANTS.ROVARENERGIA;
        rovarConsumeLogic = new RovarConsumeLogic(this);
        rovarMoveLogic = new RovarMoveLogic(this);
    }

    public Rovar(Grid grid, Rovarasz r){super(grid, r);}
    public Rovar(Grid grid, Rovarasz rovarasz, int energia) {
        super(grid, rovarasz);
        this.rovarasz = rovarasz;
        this.rovarConsumeLogic = new RovarConsumeLogic(this);
        this.rovarMoveLogic = new RovarMoveLogic(this);
        this.energia = energia;

        rovarasz.hozzaAd(this);
    }

    public static void CloneRovar(Rovar r){
        Rovar ro=new Rovar(r);
    }

    /**
     * Megvalositja a GameObject remove() fv.-et a sajat modjan
     */
    @Override
    public void remove() {
        rovarasz.torol(this);
        super.remove();
    }

    /**
     * Megvalositja a GameObject accept() fv.-et a sajat modjan
     */
    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    @Override
    protected String[] getData() {
        return new String[]{
                getClass().getSimpleName() + ": " + observer.getNev(),
                "Energia: " + energia,
        };
    }

    public double getEnergia() {
        return energia;
    }

    public void consume() throws InvalidMoveException {
        try {
            if (rovarConsumeLogic.eszik(grid)) {
                energia--;
            }
        }catch (IncompatibleGameObjectException e) {
            InvalidMoveException ime=new InvalidMoveException("Nem sikerült az evés.");
            ime.initCause(e);
            throw ime;
        }
    }

    /**
     * Rovar mozgatasa a megadott mezore
     * @param destination Melyik mezora mozogjon
     */
    public void move(Grid destination){
        if(rovarMoveLogic.mozog(destination)){
            atmozog(destination);
        }
    }

    /**
     * Rovar ellatasa a kapott hatassal
     * @param hatas Milyen hatas hasson ra
     */
    public void addHatas(Hatas hatas) {
        // TODO
    }

    public Rovarasz getRovarasz(){return rovarasz;}
}
