package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.RovarConsumeLogic;
import logic_classes.RovarMoveLogic;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Rovarasz;

public class Rovar extends GameObject {
    Rovarasz rovarasz;
    RovarConsumeLogic rovarConsumeLogic;
    RovarMoveLogic rovarMoveLogic;
    int energia;
    int hatasCooldown;
    Hatas jelenlegiHatas;

    public Rovar(Grid grid, Rovarasz r){super(grid, r);}
    public Rovar(Grid grid, Rovarasz rovarasz, RovarConsumeLogic rcl, RovarMoveLogic rml, int energia, int hatasCooldown) {
        super(grid, rovarasz);
        this.rovarasz = rovarasz;
        this.rovarConsumeLogic = rcl;
        this.rovarMoveLogic = rml;
        this.energia = energia;
        this.hatasCooldown = hatasCooldown;
    }

    /**
     * Megvalositja a GameObject remove() fv.-et a sajat modjan
     */
    @Override
    public void remove() {
        // TODO
    }

    /**
     * Megvalositja a GameObject accept() fv.-et a sajat modjan
     */
    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void consume() {
        grid.clear();
        grid.hozzaAd(this);
    }

    /**
     * Rovar mozgatasa a megadott mezore
     * @param destination Melyik mezora mozogjon
     */
    public void move(Grid destination) {
        //if(rovarMoveLogic.canMove(grid, destination)){
            grid.torol(this);
            grid = destination;
            destination.hozzaAd(this);
        //}
    }

    /**
     * Rovar ellatasa a kapott hatassal
     * @param hatas Milyen hatas hasson ra
     */
    public void addHatas(Hatas hatas) {
        // TODO
    }
}
