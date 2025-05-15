package model.grid;

import java.util.List;
import java.util.Stack;

import model.Tekton;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;

public class FonalEvo extends TektonElem{
    Stack<Fonal> fonals= new Stack<>();
    int felszivodasigFordulo;
    int felszivodasigForduloAlap;

    @Override
    public void forduloUtan() {
        if (felszivodasigFordulo > 0) {
            felszivodasigFordulo--;
        }
        if (felszivodasigFordulo == 0 && !fonals.isEmpty()) {
            this.torol(fonals.pop());
            felszivodasigFordulo=felszivodasigForduloAlap;
        }
        super.forduloUtan();
    }

    public FonalEvo(List<GameObject> gameObjects, Tekton tekton) {
        super(gameObjects, tekton);
    }

    public FonalEvo(Tekton t, int felszivodasigForduloAlap) {
        super(t);
        this.felszivodasigForduloAlap = felszivodasigForduloAlap;
        felszivodasigFordulo=-1;
    }


    @Override
    public boolean hozzaAd(GameObject gameObject) {
        if(gameObject instanceof Fonal){
            if(fonals.isEmpty()){
                felszivodasigFordulo=felszivodasigForduloAlap;
            }
            Fonal f=(Fonal)gameObject;
            fonals.push(f);
        }
        return super.hozzaAd(gameObject);
    }

    @Override
    public boolean torol(GameObject g) {
        if (g instanceof Fonal) {
            if (!fonals.isEmpty()) {
                felszivodasigFordulo = 0;
                fonals.pop();
            }
        }
        super.torol(g);
        return true;
    }
    public FonalEvo(Tekton tekton) {
        super(tekton);
    }
}
