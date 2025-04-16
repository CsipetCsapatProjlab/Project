package model.grid;

import java.util.List;

import model.Tekton;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;

public class FonalTarto extends TektonElem{
    public FonalTarto(Tekton t) {
        super(t);
    }

    @Override
    public boolean hozzaAd(GameObject gameObject) {
        return super.hozzaAd(gameObject);
    }

    public FonalTarto(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects, tekton);
    }

    @Override
    public boolean torol(GameObject go) {
        if (go instanceof Fonal) {
            return false;
        } else {
            return super.torol(go);
        }
    }


}
