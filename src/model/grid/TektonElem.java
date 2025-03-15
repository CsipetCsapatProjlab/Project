package model.grid;

import java.util.List;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.Tekton;
import model.enums.Hatas;
import model.gameobjects.GameObject;

public class TektonElem extends Grid {
    Tekton tekton;

    public TektonElem(Tekton t){
        super();
        tekton = t;
    }
    public TektonElem(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects);
        this.tekton = tekton;
    }

    public Tekton getTekton(){return tekton;}

    @Override
    public void accept(GameObjectVisitor visitor) {

    }

    @Override
    public Hatas getHatas(){return tekton.getHatas();}

    @Override
    public void accept(GridVisitor visitor) {

    }
}
