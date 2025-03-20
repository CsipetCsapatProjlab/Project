package logic_classes;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

import java.util.Arrays;

public class RovarMoveLogic extends DiscoverLogic {
    TektonElem elem;
    Lava lava;
    Fonal fonal;
    private void clearState(){
        elem=null; lava=null; fonal=null;
    }
    @Override
    public void visit(Spora spora) {

    }
    @Override
    public void visit(GombaTest gombaTest) {

    }
    @Override
    public void visit(Rovar rovar) {

    }
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
    public boolean canMove(Grid from, Grid neighbour) {
        if(from==neighbour) return true;
        if(Arrays.stream(from.getNeighbours()).noneMatch((x)->x==neighbour)){
            return false;
        }
        from.accept((GridVisitor) this);

        if(elem!=null || lava!=null && fonal!=null) {
            return true;
        }
        return false;
    }
}
