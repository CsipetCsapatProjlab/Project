package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.grid.Grid;
import model.players.Jatekos;

public abstract class GameObject {
    Grid grid;
    Jatekos observer;

    public boolean isAt(Grid other) {
        return grid == other;
    }
    public Grid getPosition(){return grid;}
    public void setPosition(Grid grid){
        this.grid = grid;
    }

    public Jatekos getObserver(){
        return observer;
    }


    public abstract void accept(GameObjectVisitor visitor);
    /**
     * Letrehozza a jatek objektumot az adott mezore
     * @param grid Hova keruljon a jatek objektum
     */
    public GameObject(Grid grid, Jatekos observer) {
        this.grid = grid;
        this.observer = observer;
        grid.hozzaAd(this);
    }
    public GameObject(Jatekos observer) {
        this.observer = observer;
        grid=null;
    }

    /**
     * Az objektum torli magat
     */
    public void removeFromGrid(){
        grid.torol(this);
    }

    protected abstract String[] getData();

    public String toString(){
        StringBuilder resultBuilder = new StringBuilder();
        for (String token : getData()) {
            resultBuilder.append(token).append("; ");
        }
        return resultBuilder.toString();
    }

    public abstract String toStringShort();

    public void forduloUtan(){}
}