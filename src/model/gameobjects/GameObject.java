package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.grid.Grid;
import model.players.Jatekos;

public abstract class GameObject {
    Grid grid;
    Jatekos observer;

    /**
     * Letrehozza a jatek objektumot az adott mezore
     * @param grid Hova keruljon a jatek objektum
     */
    public GameObject(Grid grid, Jatekos observer) {
        this.grid = grid;
        grid.hozzaAd(this);
        this.observer = observer;
    }
    public Grid getPosition(){
        return grid;
    }
    public Jatekos getObserver(){
        return observer;
    }
    
    /**
     * Az objektum torli magat
     */
    public abstract void remove();

    /**
     * Elfogadja a visitort
     * @param visitor
     */
    public abstract void accept(GameObjectVisitor visitor);

    /**
     * A megadott Grid-en van-e a GameObject
     * @param other Hasonlitando Grid
     * @return Megegyezik-e a ketto
     */
    public boolean isAt(Grid other) {
        return grid == other;
    }
}