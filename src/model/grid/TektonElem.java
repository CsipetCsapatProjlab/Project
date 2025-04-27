package model.grid;

import java.util.List;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.Tekton;
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

    public Tekton getTekton(){return tekton;}
    public void setTekton(Tekton t){tekton = t;}

    /**
     * Megvalositja a Grid accept(GameObjectVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GameObjectVisitor visitor) {
        for (var obj:gameObjects){
            obj.accept(visitor);
        }
    }

    @Override
    public boolean elfogadGameObject(GameObject gameObject){
        return true;
    }

    /**
     * Megvalositja a Grid accept(GridVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GridVisitor visitor) {
        visitor.visit(this);
    }
}
