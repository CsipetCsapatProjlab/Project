package model.gameobjects;

import java.util.Scanner;

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


    public Rovar(Grid grid) {super(grid);}
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Van ott enni valo, ha igen 1-nel nagyobb, ha nem akkor nem");
        int a = scanner.nextInt();
        if(a < 1) return;
        grid.clear();
        grid.hozzaAd(this);
    }

    public void move(Grid destination) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ha van oda ut 1-nel nagyobb, ha nem akkor nem");
        int a = scanner.nextInt();
        if(a < 1) return;
        grid.torol(this);
        grid = destination;
        destination.hozzaAd(this);
    }

    public void addHatas(Hatas hatas) {
        // TODO
    }
}
