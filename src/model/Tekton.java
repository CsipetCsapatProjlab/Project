package model;

import interfaces.GameObjectVisitor;
import java.util.ArrayList;
import java.util.List;

import model.enums.TektonelemTypes;
import model.grid.Grid;
import model.grid.TektonElem;
import model.utils.CONSTANTS;

public class Tekton {
    TektonelemTypes hatas; //Tekton hatása a rajta lévő elemkre
    List<Tekton> neighbours; //Tekton szomszédjai
    int fonalAr; //A fonal ára a tektonon
    int tektonszam = 0; //a tekton elemeinek a száma
    List<TektonElem> elemek;

    public Tekton(){
        neighbours = new ArrayList<>();
        fonalAr = 0;
        elemek = new ArrayList<>();
    }
    
    public Tekton(TektonelemTypes h){
        hatas = h;
        neighbours = new ArrayList<>();
        fonalAr = 0;
        elemek = new ArrayList<>();
    }
    /**
     * Letrehoz egy tektont
     * @param elemek TektonElemek amikbol felepul
     */
    public Tekton(List<TektonElem> elemek){
        this.elemek = elemek;
        neighbours = null;
        fonalAr = 0;
        int tektonszam = 0; //a tekton elemeinek a száma
        elemek = new ArrayList<>();
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
    public void addelem(TektonElem e){
        elemek.add(e);
        tektonszam++;
    }
    public void addNeigbour(Tekton t){
        if(!neighbours.contains(t) && t != this){
            neighbours.add(t);
        }
    }
    public List<Tekton> getNeighbours(){
        return neighbours;
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

    public TektonElem getRandomElement() {
        return elemek.get(CONSTANTS.rnd.nextInt(elemek.size()));
    }
}