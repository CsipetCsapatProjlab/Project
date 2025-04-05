package model.grid;

import java.util.List;

import model.Tekton;
import model.gameobjects.GameObject;

public class EgyFonal extends TektonElem{

    public EgyFonal(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects, tekton);
    }

    public EgyFonal( Tekton tekton) {
        super(tekton);
    }
}
