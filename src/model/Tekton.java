package model;

import java.util.ArrayList;
import java.util.List;

import model.enums.Hatas;
import model.enums.TektonelemTypes;
import model.grid.Grid;
import model.grid.TektonElem;

public class Tekton {
    TektonelemTypes hatas; //Tekton hatása a rajta lévő elemkre
    List<Tekton> neighbours; //Tekton szomszédjai
    int fonalAr; //A fonal ára a tektonon
    int tektonszam = 0; //a tekton elemeinek a száma
    List<TektonElem> elemek;

    //típus alapján konstruktor
    public Tekton(TektonelemTypes h){
        hatas = h;
        neighbours = new ArrayList<>();
        fonalAr = 0;
        elemek = new ArrayList<>();
    }

    //Beállítja a szomszédokat
    public void setNeighbours(List<Tekton> l, int a){
        if(a == 0) return;
        neighbours = l;
        List<Tekton> tmp = l;
        for (Tekton tekton : l) {
            tmp.remove(tekton);
            tmp.add(this);
            tekton.setNeighbours(tmp, --a);
        }

    }

    //hozzáad egy új tektonelemet
    public void addelem(TektonElem e){
        elemek.add(e);
        tektonszam++;
    }

    //új szomszédot add hozzá
    public void addNeigbour(Tekton t){
        if(!neighbours.contains(t)){
            neighbours.add(t);
        }
    }
    public TektonelemTypes getHatas(){return hatas;}
    public int getFonalAr() {return  fonalAr;}
    public int getTektonszam(){return tektonszam;}
    public List<TektonElem> getTektonElems(){return elemek;}
    public void resetTektonelemek(){elemek.clear();}
    public Grid[] getRandomPath() {
        // TODO
        return null;
    }
}