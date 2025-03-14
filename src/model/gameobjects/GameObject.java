package model.gameobjects;

import interfaces.GameObjectVisitor;
import model.grid.Grid;

public abstract class GameObject {
    Grid grid;

    public GameObject(Grid grid) {
        this.grid = grid;
    }

    /// Törli a GameObjectet
    public abstract void remove();

    public abstract void accept(GameObjectVisitor visitor);
}