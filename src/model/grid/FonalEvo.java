package model.grid;

import java.util.List;

import model.Tekton;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;

import java.util.Stack;

public class FonalEvo extends TektonElem{
    Stack<Fonal> fonals= new Stack<>();
    int felszivodasigFordulo;
    int felszivodasigForduloAlap;

    @Override
    void forduloUtan() {
        if(felszivodasigFordulo>0){
            felszivodasigFordulo--;
        }
        if(felszivodasigFordulo==0){

                this.torol(fonals.peek());

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
            fonals.add(f);
        }
        return super.hozzaAd(gameObject);
    }

    @Override
    public boolean torol(GameObject g){
        if(g instanceof Fonal){
            if(fonals.isEmpty()){
                felszivodasigFordulo=-1;
            }
            Fonal f=(Fonal) g;
            fonals.pop();
        }
        super.torol(g);
        return true;
    }
    public FonalEvo(Tekton tekton) {
        super(tekton);
    }
}
