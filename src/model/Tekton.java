package model;

import interfaces.GameObjectVisitor;
import java.util.List;
import model.enums.Hatas;
import model.grid.Grid;
import model.grid.TektonElem;

public class Tekton {
    List<Tekton> neighbours;
    int fonalAr;
    List<TektonElem> elemek;

    /**
     * Letrehoz egy tektont
     * @param elemek TektonElemek amikbol felepul
     */
    public Tekton(List<TektonElem> elemek){
        this.elemek = elemek;
        neighbours = null;
        fonalAr = 0;
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

    /**
     * Mennyibe kerul a fonal novesztese a tektonon
     * @return Fonal novesztes ara
     */
    public int getFonalAr() {return  fonalAr;}

    /**
     * Tores algoritmus
     * @return A tores utjan atszelt mezok
     */
    private Grid[] getRandomPath() {
        // TODO
        return null;
    }

    /**
     * Visszaadja a tekton hatasat
     */
    public Hatas getHatas() {
        // TODO
        return null;
    }
}