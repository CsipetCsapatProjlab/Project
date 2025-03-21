package model.grid;

import java.util.ArrayList;
import java.util.List;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;
import model.enums.Hatas;
import model.gameobjects.GameObject;

public abstract class Grid {
    Grid[] neighbours;
    boolean zarolva;
    List<GameObject> gameObjects;
    protected Grid(){
        zarolva = false;
        gameObjects = new ArrayList<>();
    }
    protected Grid(List<GameObject> g) {
        this.gameObjects = g;
    }
    public List<GameObject> getGameObject(){return gameObjects;}

    public void clear() {
        List<GameObject> tmp = new ArrayList<>();
        tmp.addAll(gameObjects);
        for (GameObject g : tmp) {
            g.remove();
        }
        gameObjects.clear();
    }

    public void setNeighbours(Grid[] arr){
        neighbours = arr;
    }

    public void hozzaAd(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void torol(GameObject g) {
        gameObjects.remove(g);
    }

    public void gridPathFind(Grid kezdo, Grid cel, int depth, IDiscoverLogic dLogic) {
        // TODO
    }

    public void gridFindAll(Grid kezdo, int depth, IDiscoverLogic dLogic) {
        // TODO
    }

    public Grid[] getNeighbours() {return neighbours;}
    public abstract Hatas getHatas();
    public abstract void accept(GameObjectVisitor visitor);
    public abstract void accept(GridVisitor visitor);
}
