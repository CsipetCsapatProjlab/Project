package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import java.util.List;
import model.Tekton;
import model.enums.Hatas;
import model.gameobjects.GameObject;

public class TektonElem extends Grid {
    Tekton tekton;

    public TektonElem(Tekton t){
        super();
        tekton = t;
    }

    /**
     * Letrehozza a TektonElemet
     * @param gameObjects Mik vannak rajta
     * @param tekton Melyik tekton resze
     */
    public TektonElem(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects);
        this.tekton = tekton;
    }

    /**
     * Visszaadja melyik tekton resze
     * @return Anyatekton
     */
    public Tekton getTekton(){return tekton;}

    /**
     * Megvalositja a Grid accept(GameObjectVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GameObjectVisitor visitor) {

    }

    /**
     * Megvalositja a Grid getHatas() fv.-et a sajat modjan
     */
    @Override
    public Hatas getHatas(){return tekton.getHatas();}

    /**
     * Megvalositja a Grid accept(GridVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GridVisitor visitor) {

    }
}
