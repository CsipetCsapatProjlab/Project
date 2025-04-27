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

    public Grid[] getNeighbors() {return neighbours;}
    public void setNeighbours(Grid[] arr, int hany){
        neighbours = arr;
        szomszedokSzama = hany;
    }

    public int getSzomszedokSzama() {return szomszedokSzama;}

    public int clear() {
        List<GameObject> tmp = new ArrayList<>();
        int numsdeleted=0;
        tmp.addAll(gameObjects);
        for (GameObject g : tmp) {
            numsdeleted++;
            g.removeFromGrid();
        }
        gameObjects.clear();
        return numsdeleted;
    }

    public boolean hozzaAd(GameObject gameObject){
        if(elfogadGameObject(gameObject)){
            return gameObjects.add(gameObject);
        }
        return false;
    }

    public boolean torol(GameObject g)  {
        return gameObjects.remove(g);
    }

    public abstract boolean elfogadGameObject(GameObject gameObject);


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

    public void forduloUtan(){}
    public abstract void accept(GameObjectVisitor visitor);
    public abstract void accept(GridVisitor visitor);
}
