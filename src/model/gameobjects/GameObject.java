package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.exceptions.IncompatibleGameObjectException;
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
        this.observer = observer;
        grid.hozzaAd(this);
    }

    /**
     * A mezojet visszaadja
     * @return A mezo
     */
    public Grid getPosition(){
        return grid;
    }

    public void atmozog(Grid other){
        if(other==grid) return;
        else{
            grid.torol(this);
            other.hozzaAd(this);
            grid=other;
        }
    }
    /**
     * Beállítja a mezőjét
     * @param grid
     */
    protected void setPosition(Grid grid){
        this.grid = grid;
    }

    /**
     * Az obeservert visszaadja
     * @return Az observer jatekos
     */
    public Jatekos getObserver(){
        return observer;
    }

    /**
     * Az objektum torli magat
     */
    public abstract void remove() throws IncompatibleGameObjectException;

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