package model.grid;

import java.util.ArrayList;
import java.util.List;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;
import model.enums.Hatas;
import model.enums.TektonelemTypes;
import model.gameobjects.GameObject;

public abstract class Grid {
    Grid[] neighbours; // a griddel szomszédos gridek
    boolean zarolva; //azt mutatja lehet e rajta gameObject vagy sem
    List<GameObject> gameObjects; //a tektonon lévő objectek listálya
    int szomszedokSzama; //Szomszédok száma
    
    //alap konstruktor
    protected Grid(){
        zarolva = false;
        gameObjects = new ArrayList<>();
    }

    //gamelista alapján törénő konstruktor
    protected Grid(List<GameObject> g) {
        this.gameObjects = g;
    }
    public List<GameObject> getGameObject(){return gameObjects;}

    //Törli a gameobjecteket a gridről
    public void clear() {
        List<GameObject> tmp = new ArrayList<>();
        tmp.addAll(gameObjects);
        for (GameObject g : tmp) {
            g.remove();
        }
        gameObjects.clear();
    }

    public void setNeighbours(Grid[] arr, int hany){
        neighbours = arr;
        szomszedokSzama = hany;
    }
    public int getSzomszedokSzama(){return szomszedokSzama;}
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

    public String toString(){
        return " ";
    }
    public Grid[] getNeighbours() {return neighbours;}
    public abstract TektonelemTypes getHatas();
    public abstract void accept(GameObjectVisitor visitor);
    public abstract void accept(GridVisitor visitor);
}
