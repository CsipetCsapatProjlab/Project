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


    public Rovar(Grid grid, Rovarasz rovarasz) {super(grid,rovarasz);}
    public Rovar(Grid grid, Rovarasz rovarasz, RovarConsumeLogic rcl, RovarMoveLogic rml, int energia, int hatasCooldown) {
        super(grid,rovarasz);
        this.rovarasz = rovarasz;
        this.rovarConsumeLogic = rcl;
        this.rovarMoveLogic = rml;
        this.energia = energia;
        this.hatasCooldown = hatasCooldown;
    }

    @Override
    public void remove() {
        // TODO
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void consume() {
        grid.clear();
        grid.hozzaAd(this);
    }

    public void move(Grid destination) {
        //if(rovarMoveLogic.canMove(grid, destination)){
            grid.torol(this);
            grid = destination;
            destination.hozzaAd(this);
        //}
    }

    public void addHatas(Hatas hatas) {
        // TODO
    }
}
