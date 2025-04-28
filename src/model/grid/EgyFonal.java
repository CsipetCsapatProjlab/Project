package model.grid;

import java.util.List;

import model.Tekton;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;

public class EgyFonal extends TektonElem{

    public EgyFonal(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects, tekton);
    }

    public EgyFonal( Tekton tekton) {
        super(tekton);
    }

    @Override
    public boolean elfogadGameObject(GameObject go){
        if(gameObjects.stream().anyMatch(x->x instanceof Fonal)){
            return false;
        }
        else{
            return super.elfogadGameObject(go);
        }
    }
}
