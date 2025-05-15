package logic_classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.Tekton;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

public class SporaPlaceLogic implements GridVisitor, GameObjectVisitor {
    GombaTest gt;
    List<Spora> fromGombatest;
    Tekton tekton;
    boolean __GridContainsSpora;

    public SporaPlaceLogic(GombaTest gt) {
        this.gt = gt;
        fromGombatest = new ArrayList<>();
    }

    void clearState(){
        fromGombatest.clear();
        tekton=null;
        __GridContainsSpora=false;
    }

    boolean isSporaOnTektonElem(Grid celGrid){
        __GridContainsSpora = false;

        celGrid.accept((GameObjectVisitor) this);
        return __GridContainsSpora;
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
    public void visit(Lava lava) {}
    @Override
    public void visit(GombaTest gombaTest) {}
    @Override
    public void visit(Rovar rovar) {}
    @Override
    public void visit(Fonal fonal) {}


    public Optional<Grid> placeSpora(Grid celGrid){
        clearState();

        Tekton origin = null;
        Tekton destination = null;

        celGrid.accept((GridVisitor) this);
        if(tekton!=null){
            destination=tekton;
        }
        tekton=null;

        gt.getPosition().accept((GridVisitor) this);
        if(tekton!=null){
            origin=tekton;
        }
        tekton=null;

        if(origin!=null && destination!=null){
                if(origin.getNeighbours().contains(destination)){
                    return Optional.of(celGrid);
                }
                for (Tekton tekton : origin.getNeighbours()) {
                    if(tekton.getNeighbours().contains(destination)){
                        if(gt.getFejlesztett())
                            return Optional.of(celGrid);
                    }
                }
        }
        return Optional.empty();
    }


}
