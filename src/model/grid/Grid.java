package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;
import model.gameobjects.GameObject;

import java.util.List;

public abstract class Grid {
    boolean zarolva;
    List<GameObject> gameObjects;

    public Grid(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void clear() {
        gameObjects.forEach(GameObject::remove);
        gameObjects = null;
    }

    public void hozzaAd(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void torol(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void gridPathFind(Grid kezdo, Grid cel, int depth, IDiscoverLogic dLogic) {
        // TODO
    }

    public void gridFindAll(Grid kezdo, int depth, IDiscoverLogic dLogic) {
        // TODO
    }

    public abstract void accept(GameObjectVisitor visitor);
    public abstract void accept(GridVisitor visitor);
}
