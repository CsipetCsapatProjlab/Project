package logic_classes;

import java.util.Arrays;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

public class RovarMoveLogic extends DiscoverLogic {

    Rovar rovar;
    TektonElem elem;
    Lava lava;
    Fonal fonal;

    public RovarMoveLogic(Rovar r){
        rovar=r;
    }

    private void clearState(){
        elem=null; lava=null; fonal=null;
    }
    @Override
    public void visit(Spora spora) {}
    @Override
    public void visit(GombaTest gombaTest) {}
    @Override
    public void visit(Rovar rovar) {}
    @Override
    public void visit(Fonal fonal) {
        this.fonal = fonal;
    }
    @Override
    public void visit(Lava lava) {
        this.lava = lava;
        lava.accept((GameObjectVisitor) this);
    }
    @Override
    public void visit(TektonElem elem) {
        this.elem=elem;
    }


    @Override
    public double canMove(Grid from, Grid neighbour) {
        clearState();

        if(from==neighbour) return 0;
        if(Arrays.stream(from.getNeighbors()).noneMatch((x)->x==neighbour)){
            return Double.POSITIVE_INFINITY;
        }
        neighbour.accept((GridVisitor) this);

        if(elem!=null || lava!=null && fonal!=null) {
            return 1;
        }
        return Double.POSITIVE_INFINITY;
    }


}
