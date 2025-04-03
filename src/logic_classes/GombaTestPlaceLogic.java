package logic_classes;

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

import java.util.List;
import java.util.Objects;

public class GombaTestPlaceLogic implements GridVisitor, GameObjectVisitor {
    boolean vanGombaTest;
    Fonal fonal;
    List<Spora> sporas;
    TektonElem tektonelem;

    @Override
    public void visit(Spora spora) {
        if(Objects.equals(spora.getObserver().getNev(), fonal.getObserver().getNev())) {
            sporas.add(spora);
        }
    }

    @Override
    public void visit(GombaTest gombaTest) {
        if(Objects.equals(gombaTest.getObserver().getNev(), fonal.getObserver().getNev())){
            vanGombaTest = true;
        }
    }

    @Override
    public void visit(Rovar rovar) {

    }

    @Override
    public void visit(Fonal fonal) {

    }

    @Override
    public void visit(Lava lava) {

    }

    @Override
    public void visit(TektonElem elem) {

    }

    void clearState(){
        vanGombaTest = false;
        sporas.clear();
        tektonelem = null;
    }

    public GombaTestPlaceLogic(Fonal fonal) {
        this.fonal = fonal;
        clearState();
    }

    public boolean placeGombaTest(Grid celgrid, List<Spora> sporas){
        celgrid.accept((GridVisitor) this);
        if(tektonelem != null){
            tektonelem.getTekton().visitElements(this);
            if(vanGombaTest){
                return false;
            }
            else{
                for (int i = 0; i < sporas.size(); i++) {
                    Spora act=sporas.get(i);
                    act.remove();
                }
                return true;
            }
        }
        return false;
    }






}
