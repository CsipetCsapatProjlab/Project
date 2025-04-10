package model.grid;

import java.util.List;

import model.Tekton;
import model.gameobjects.GameObject;

public class FonalEvo extends TektonElem{

    public FonalEvo(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects, tekton);
    }

    public FonalEvo(Tekton tekton) {
        super(tekton);
    }
}
