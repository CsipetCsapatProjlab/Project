package model;

import java.util.List;

import model.enums.Hatas;
import model.grid.Grid;

public class Tekton {
    Hatas hatas;
    List<Tekton> neighbours;
    int fonalAr;
    
    public Tekton(Hatas h){
        hatas = h;
        neighbours = null;
        fonalAr = 0;
    }
    public void szakad() {
        // TODO
    }
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
    public Hatas getHatas(){return hatas;}
    public int getFonalAr() {return  fonalAr;}
    public Grid[] getRandomPath() {
        // TODO
        return null;
    }
}