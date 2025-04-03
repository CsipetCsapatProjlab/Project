package model.grid;

import model.Tekton;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;

public class FonalTarto extends TektonElem{
    public FonalTarto(Tekton t) {
        super(t);
    }

    @Override
    public boolean hozzaAd(GameObject gameObject){
        return super.hozzaAd(gameObject);
    }

    @Override
    public boolean torol(GameObject go){
        if(go instanceof Fonal){
            return false;
        }else{
            return super.torol(go);
        }
    }
}
