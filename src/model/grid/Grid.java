package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;

import java.util.*;

import model.gameobjects.Fonal;
import model.gameobjects.GameObject;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;

public abstract class Grid {
    Grid[] neighbours;
    boolean zarolva;
    List<GameObject> gameObjects;
    int szomszedokSzama;
    protected Grid(){
        zarolva = false;
        gameObjects = new ArrayList<>();
    }

    //gamelista alapján törénő konstruktor
    protected Grid(List<GameObject> g) {
        this.gameObjects = g;
    }
    public List<GameObject> getGameObject(){return gameObjects;}

    public int clear() {
        List<GameObject> tmp = new ArrayList<>();
        int numsdeleted=0;
        tmp.addAll(gameObjects);
        for (GameObject g : tmp) {
            numsdeleted++;
            g.remove();
        }
        gameObjects.clear();
        return numsdeleted;
    }

    public void setNeighbours(Grid[] arr){
        neighbours = arr;
    }

    public void setNeighbours(Grid[] arr, int hany){
        neighbours = arr;
        szomszedokSzama = hany;
    }

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

    public String toString(){
        boolean rovar = false;
        boolean spora = false;
        boolean gomba = false;
        boolean fonal = false;
        for (GameObject gameObject : gameObjects) {
            if(gameObject instanceof Rovar){
                rovar = true;
            }else if(gameObject instanceof Spora){
                spora = true;
            }else if(gameObject instanceof GombaTest){
                gomba = true;
            }else if(gameObject instanceof Fonal){
                fonal = true;
            } 
        }
        if(rovar){
            return "R";
        }else if(gomba){
            return "G";
        }else if(spora){
            return "S";
        }else if(fonal){
            return "$";
        }
        return " ";
    }
    
    /**
     * A mezo szomszedait lekerdezi
     * @return Mezo tomb a szomszedokkal
     */
    public Grid[] getNeighbors() {return neighbours;}
    public int getSzomszedokSzama() {return szomszedokSzama;}
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
