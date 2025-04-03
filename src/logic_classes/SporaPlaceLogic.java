package logic_classes;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.Tekton;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SporaPlaceLogic implements GridVisitor, GameObjectVisitor {
    GombaTest gt;
    List<Spora> fromGombatest;
    Tekton tekton;
    boolean __GridContainsSpora;


    boolean isSporaOnTektonElem(Grid celGrid){
        celGrid.accept((GameObjectVisitor) this);
        return __GridContainsSpora;
    }

    public SporaPlaceLogic(GombaTest gt) {
        this.gt = gt;
        fromGombatest = new ArrayList<>();
    }

    void clearState(){
        fromGombatest.clear();
        tekton=null;
        __GridContainsSpora=false;
    }

    @Override
    public void visit(Lava lava) {

    }

    @Override
    public void visit(TektonElem elem) {
        tekton=elem.getTekton();
    }


    @Override
    public void visit(Spora spora) {
        if(Objects.equals(gt.getObserver().getNev(), spora.getObserver().getNev())){
            __GridContainsSpora=true;
        }
        else{
            __GridContainsSpora=false;
        }
    }

    @Override
    public void visit(GombaTest gombaTest) {

    }

    @Override
    public void visit(Rovar rovar) {

    }

    @Override
    public void visit(Fonal fonal) {

    }



    public boolean placeSpora(Grid celGrid) throws IncompatibleGameObjectException {
    clearState();

        Tekton origin = null;
        Tekton destination = null;
        celGrid.accept((GridVisitor) this);
        if(tekton!=null){
            destination=tekton;
        }
        tekton=null;
        gt.getPosition().accept((GameObjectVisitor) this);
        if(tekton!=null){
            origin=tekton;
        }
        tekton=null;
        if(origin!=null && destination!=null){
            if(origin.getNeighbours().contains(destination)){
                for (Spora sp: fromGombatest){
                    TektonElem randomTektonElem=destination.getRandomElement();
                    if(!isSporaOnTektonElem(randomTektonElem)){
                        sp.atmozog(randomTektonElem);
                    }
                }
                return true;
            }
        }
        return false;
    }


}
