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
    public Rovar(Grid grid, Rovarasz rovarasz, int energia, int hatasCooldown) {
        super(grid, rovarasz);
        this.rovarasz = rovarasz;
        this.rovarConsumeLogic = new RovarConsumeLogic(this);
        this.rovarMoveLogic = new RovarMoveLogic(this);
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

    public int getEnergia() {
        return energia;
    }

    public void consume() {
        if(rovarConsumeLogic.eszik(grid)){
            energia--;
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
}
