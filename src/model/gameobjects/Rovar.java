package model.gameobjects;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import interfaces.GameObjectVisitor;
import logic_classes.RovarConsumeLogic;
import logic_classes.RovarMoveLogic;
import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.utils.Constants;
import model.enums.Hatas;
import model.exceptions.IncompatibleGameObjectException;
import model.exceptions.InvalidMoveException;
import model.grid.Grid;
import model.players.Rovarasz;
import model.utils.Constants;
import model.utils.GridUtils;

public class Rovar extends GameObject{
    Rovarasz rovarasz;
    RovarConsumeLogic rovarConsumeLogic;
    RovarMoveLogic rovarMoveLogic;
    double energia;

    public Rovar(Rovar other) {
        super(other.grid,other.rovarasz);
        this.rovarasz = other.rovarasz;
        energia = Constants.rovarMozgasEnergia;
        rovarConsumeLogic = new RovarConsumeLogic(this);
        rovarMoveLogic = new RovarMoveLogic(this);
    }

    public Rovar(Grid grid, Rovarasz r){
        super(grid, r);
        this.rovarasz = r;
        energia = 5;
        this.rovarConsumeLogic = new RovarConsumeLogic(this);
        this.rovarMoveLogic = new RovarMoveLogic(this);
    }

    public Rovar(Grid grid, Rovarasz rovarasz, int energia) {
        super(grid, rovarasz);
        this.rovarasz = rovarasz;
        this.rovarConsumeLogic = new RovarConsumeLogic(this);
        this.rovarMoveLogic = new RovarMoveLogic(this);
        this.energia = energia;

        rovarasz.hozzaAd(this);
    }

    public static void CloneRovar(Rovar r){
        Rovar ro = new Rovar(r);
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

    public void consume() throws FailedMoveException {
        if (rovarConsumeLogic.eszik(grid)) {
            //Csökkentjük az energiáját ha sikerült az evés
            energia--;
        }
        else { // Ha .eszik nem sikerült
            throw new FailedMoveException("Nem sikerült az evés.", this, Move.Rovar_eszik);
        }
    }

    /**
     * Rovar mozgatasa a megadott mezore
     * @param destination Melyik mezora mozogjon
     */
    public void move(Grid destination){
        LinkedList<Grid> path=rovarMoveLogic.mozog(destination);
        double sum = GridUtils.GridPathFinder.getPathWeightSum(path, rovarMoveLogic);

        if(sum > energia || sum<=0){
            throw new FailedMoveException("A célba nem tud mozogni a rovar mert vagy nem létezik út, vagy nincs elég energiája",this,Move.Rovar_mozog);
        }
        else{
            energia-=sum;
            atmozog(destination);
        }
    }

    public Rovarasz getRovarasz(){return rovarasz;}
}
