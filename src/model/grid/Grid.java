package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import jdk.jshell.spi.ExecutionControl;
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

    /**
     * Megtisztitja a mezot minden rajta elhelyezheto GameObject-tol
     */
    public void clear() {
        List<GameObject> tmp = new ArrayList<>();
        tmp.addAll(gameObjects);
        for (GameObject g : tmp) {
            g.remove();
        }
        gameObjects.clear();
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
    public void hozzaAd(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    /**
     * GameObject torlese a mezorol
     * @param g Mit toroljon
     */
    public void torol(GameObject g) {
        gameObjects.remove(g);
    }

    /**
     * Csak a szomszédot nézi meg, mert még nem fogom implementálni a Dijkstrát.
     * @param kezdo
     * @param cel
     * @param depth Mélység
     * @param dLogic Func<double, Grid, Grid>, ami meghatározza az élsúlyt
     * @return Utvonal lista, ami tartalmazza a kezdő és a végpontokat.
     * @throws ExecutionControl.NotImplementedException
     */
    public List<Grid> gridPathFind(Grid kezdo, Grid cel, int depth, IDiscoverLogic dLogic){
        if(!Arrays.stream(kezdo.neighbours).toList().contains(cel)) return null;
        if(dLogic.canMove(kezdo,cel)<=depth){
            List<Grid> lista= new LinkedList<>();
            lista.add(kezdo);
            lista.add(cel);
            return lista;
        }
        return new LinkedList<>();
    }

    /**
     * Megtalalja a kezdo pontbol, adott melysegig 
     * @param kezdo Kezdo mezo
     * @param depth Milyen tavolsagban (melysegben)
     * @param dLogic Felderitesi logika
     */
    public List<Grid> gridFindAll(Grid kezdo, int depth, IDiscoverLogic dLogic) {
        return null;
    }

    /**
     * A mezo szomszedait lekerdezi
     * @return Mezo tomb a szomszedokkal
     */
    public Grid[] getNeighbours() {return neighbours;}
    public abstract Hatas getHatas();

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
