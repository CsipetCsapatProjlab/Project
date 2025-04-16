package model.grid;

import model.Tekton;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.GameObject;
import model.gameobjects.GombaTest;

public class GombaTestEvo extends TektonElem {
    public GombaTestEvo(Tekton t) {
        super(t);
    }

    @Override
    public boolean elfogadGameObject(GameObject gameObject){
        if(gameObject instanceof GombaTest) {
            return false;
        }
        return true;
    }
}
