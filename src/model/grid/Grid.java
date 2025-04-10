package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;

import java.util.*;

import jdk.jshell.spi.ExecutionControl;
import model.enums.Hatas;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.GameObject;

public abstract class Grid {
    Grid[] neighbours;
    boolean zarolva;
    protected List<GameObject> gameObjects;
    protected Grid(){
        zarolva = false;
        gameObjects = new ArrayList<>();
    }
    protected Grid(List<GameObject> g) {
        this.gameObjects = g;
    }
    public List<GameObject> getGameObject(){return gameObjects;}

    /**
     * Megtisztitja a mezot minden rajta elhelyezheto GameObject-tol
     */
    public int clear(){
        List<GameObject> tmp = new ArrayList<>();
        int numsdeleted=0;
        tmp.addAll(gameObjects);
        for (GameObject g : tmp) {
            numsdeleted++;
            try {
                g.remove();
            } catch (IncompatibleGameObjectException e) {numsdeleted--;}
        }
        gameObjects.clear();
        return numsdeleted;
    }

    /**
     * Beallitja a mezo szomszedos mezoit
     * @param arr Grid tomb, amely a mezoket tartalmazza
     */
    public void setNeighbours(Grid[] arr){
        neighbours = arr;
    }

    /**
     * GameObject hozzaadasa a mezohoz
     * @param gameObject Mit adjon hozza
     */
    public boolean hozzaAd(GameObject gameObject){
        if(elfogadGameObject(gameObject)){
            return gameObjects.add(gameObject);
        }
        return false;
    }

    public abstract boolean elfogadGameObject(GameObject gameObject);

    /**
     * GameObject torlese a mezorol
     * @param g Mit toroljon
     */
    public boolean torol(GameObject g)  {
        return gameObjects.remove(g);
    }

    /**
     * A mezo szomszedait lekerdezi
     * @return Mezo tomb a szomszedokkal
     */
    public Grid[] getNeighbors() {return neighbours;}
    public abstract Hatas getHatas();

    void forduloUtan(){}

    /**
     * Fogadja a jatekobjektum visitort a mezon
     * @param visitor
     */
    public abstract void accept(GameObjectVisitor visitor);

    /**
     * Fogadja a mezo visitort a mezon
     * @param visitor
     */
    public abstract void accept(GridVisitor visitor);
}
