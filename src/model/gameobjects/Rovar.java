package model.gameobjects;

import java.util.LinkedList;
import interfaces.GameObjectVisitor;
import logic_classes.RovarConsumeLogic;
import logic_classes.RovarMoveLogic;
import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.utils.Constants;
import model.grid.Grid;
import model.players.Rovarasz;
import model.utils.GridUtils;

public class Rovar extends GameObject{
    double energia;
    RovarConsumeLogic rovarConsumeLogic=new RovarConsumeLogic(this);
    RovarMoveLogic rovarMoveLogic=new RovarMoveLogic(this);

    public Rovarasz getRovarasz(){return (Rovarasz)observer;}
    public double getEnergia() {
        return energia;
    }

    @Override
    public void accept(GameObjectVisitor visitor) {visitor.visit(this);}

    public Rovar(Rovar other) {
        super(other.grid,other.observer);
        energia = Constants.rovarMozgasEnergia;

        getRovarasz().hozzaAd(this);
    }

    public Rovar(Grid grid, Rovarasz r){
        super(grid, r);
        energia = Constants.rovarMozgasEnergia;

        getRovarasz().hozzaAd(this);
    }

    public static void CloneRovar(Rovar r){new Rovar(r);}

    @Override
    public void removeFromGrid() {
        super.removeFromGrid();
        getRovarasz().torol(this);
    }
    /**
     * Rovar mozgatasa a megadott mezore
     * @param destination Melyik mezora mozogjon
     */
    public void mozog(Grid destination){
        LinkedList<Grid> path=GridUtils.GridPathFinder.gridPathFind(getPosition(),destination,energia,rovarMoveLogic);
        double sum = GridUtils.GridPathFinder.getPathWeightSum(path, rovarMoveLogic);

        if(sum > energia || sum<=0){
            throw new FailedMoveException("A célba nem tud mozogni a rovar mert vagy nem létezik út, vagy nincs elég energiája",this,Move.Rovar_mozog);
        }
        else{
            energia-=sum;

            grid.torol(this);
            grid=destination;
            destination.hozzaAd(this);
        }
    }

    public void eszik(Grid dest) throws FailedMoveException {
        var sp=rovarConsumeLogic.eszikSpora(dest); // amin jelenleg vagyunk, enni szeretnénk
        if(sp.isPresent()) {
            sp.get().removeFromGrid();
            sp.get().effektAktival(this);
            energia--;
        }
    }
    public void vag(Grid dest) throws FailedMoveException {
        var fn=rovarConsumeLogic.vagFonal(dest);
        if(fn.isPresent()) {
            fn.get().removeFromGrid();
            energia--;
            if(grid==dest){ // meghal
                removeFromGrid();
            }
        }
    }


    @Override
    protected String[] getData() {
        return new String[]{
                getClass().getSimpleName() + ": " + observer.getNev(),
                "Energia: " + energia,
        };
    }

    @Override
    public void forduloUtan(){
        energia+=Constants.rovarMozgasEnergia;
    }


}
