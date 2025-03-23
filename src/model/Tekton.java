package model;

import java.util.List;

import interfaces.GameObjectVisitor;
import model.enums.Hatas;
import model.grid.Grid;
import model.grid.TektonElem;

public class Tekton {
    List<Tekton> neighbours;
    int fonalAr;
    List<TektonElem> elemek;

    public Tekton(List<TektonElem> elemek){
        this.elemek = elemek;
        neighbours = null;
        fonalAr = 0;
    }
    public void szakad() {
        // TODO
    }

    public void visitElements(GameObjectVisitor visitor) {
        for (TektonElem elem : elemek) {
            elem.accept(visitor);
        }
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
    public int getFonalAr() {return  fonalAr;}
    public Grid[] getRandomPath() {
        // TODO
        return null;
    }
}