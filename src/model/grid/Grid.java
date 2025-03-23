package model.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;
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

    /**
     * Csak a szomszédot nézi meg, mert még nem fogom implementálni a Dijkstrát.
     * @param kezdo
     * @param cel
     * @param depth Mélység
     * @param dLogic Func<double, Grid, Grid>, ami meghatározza az élsúlyt
     * @return Utvonal lista, ami tartalmazza a kezdő és a végpontokat.
     * @throws ExecutionControl.NotImplementedException
     */
    public List<Grid> gridPathFind(Grid kezdo, Grid cel, int depth, IDiscoverLogic dLogic) throws ExecutionControl.NotImplementedException {
        if(!Arrays.stream(kezdo.neighbours).toList().contains(cel)) throw new ExecutionControl.NotImplementedException("Celgrid nem szomszédja a kezdőgridnek");
        if(dLogic.canMove(kezdo,cel)<=depth){
            List<Grid> lista= new LinkedList<>();
            lista.add(kezdo);
            lista.add(cel);
            return lista;
        }
        return new LinkedList<>();
    }

    public List<Grid> gridFindAll(Grid kezdo, int depth, IDiscoverLogic dLogic) {
        return null;
    }

    public Grid[] getNeighbours() {return neighbours;}
    public abstract Hatas getHatas();
    public abstract void accept(GameObjectVisitor visitor);
    public abstract void accept(GridVisitor visitor);
}
