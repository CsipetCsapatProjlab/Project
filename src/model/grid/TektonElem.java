package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.Tekton;
import model.gameobjects.GameObject;

import java.util.List;

public class TektonElem extends Grid {
    Tekton tekton;

    public TektonElem(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects);
        this.tekton = tekton;
    }

    @Override
    public void accept(GameObjectVisitor visitor) {

    }

    @Override
    public void accept(GridVisitor visitor) {

    }
}
