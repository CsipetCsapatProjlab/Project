package model.gameobjects;

import interfaces.GameObjectVisitor;
import interfaces.IRovarasz;
import logic_classes.RovarConsumeLogic;
import logic_classes.RovarMoveLogic;
import model.enums.Hatas;
import model.grid.Grid;

public class Rovar extends GameObject {
    IRovarasz  rovarObserever;
    RovarConsumeLogic rovarConsumeLogic;
    RovarMoveLogic rovarMoveLogic;
    int energia;
    int hatasCooldown;
    Hatas jelenlegiHatas;



    public Rovar(Grid grid, IRovarasz rObserever, RovarConsumeLogic rcl, RovarMoveLogic rml, int energia, int hatasCooldown) {
        super(grid);
        this.rovarObserever = rObserever;
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
        // TODO
    }

    public void move(Grid destination) {
        // TODO
    }

    public void addHatas(Hatas hatas) {
        // TODO
    }
}
