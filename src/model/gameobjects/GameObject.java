package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.grid.Grid;
import model.players.Jatekos;

public abstract class GameObject {
    Grid grid;
    Jatekos observer;

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
    /// Törli a GameObjectet
    public abstract void remove();

    public abstract void accept(GameObjectVisitor visitor);
    public boolean isAt(Grid other) {
        return grid == other;
    }
}