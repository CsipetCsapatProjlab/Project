package model;

import interfaces.GameObjectVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.enums.Hatas;
import model.enums.TektonelemTypes;
import model.grid.Grid;
import model.grid.TektonElem;

public class Tekton {
    TektonelemTypes hatas;
    List<Tekton> neighbours;
    int fonalAr;
    int tektonszam;
    List<TektonElem> elemek;

    public Tekton(TektonelemTypes h) {
        hatas = h;
        neighbours = new ArrayList<>();
        List<TektonElem> elemek;
    }
    /**
     * Letrehoz egy tektont
     * @param elemek TektonElemek amikbol felepul
     */
    public Tekton(List<TektonElem> elemek){
        this.elemek = elemek;
        neighbours = null;
        fonalAr = 0;
        elemek = new ArrayList<>();
    }

    /**
     * Eltori a tektont
     */
    public void szakad() {
        // TODO
    }

    /**
     * Minden, a tektonon levo elemet regisztral
     * @param visitor
     */
    public void visitElements(GameObjectVisitor visitor) {
        for (TektonElem elem : elemek) {
            elem.accept(visitor);
        }
    }

    /**
     * Beallitja a szomszedos tektonjait
     * @param l Szomszedos tektonok listaja
     * @param a Hany szomszedja van
     */
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
    public List<Tekton> getNeighbours() {
        return neighbours;
    }

    public void addelem(TektonElem e){
        elemek.add(e);
    }
    public void addNeighbour(Tekton t){
        if(!neighbours.contains(t)){
            neighbours.add(t);
        }
    }
    public TektonelemTypes getHatas(){return hatas;}
    public int getFonalAr() {return  fonalAr;}
    public Grid[] getRandomPath() {
        // TODO
        return null;
    }

    public TektonElem getRandomElement() {
        return elemek.get(CONSTANTS.rnd.nextInt(elemek.size()));
    }
}