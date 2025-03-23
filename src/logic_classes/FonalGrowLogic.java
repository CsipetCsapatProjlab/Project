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

public class FonalGrowLogic extends DiscoverLogic{
    Fonal fonal;
    Lava lava;
    GombaTest gombaTest;

    @Override
    public void visit(Spora spora) {
    }

    @Override
    public void visit(GombaTest gombaTest) {
        this.gombaTest=gombaTest;
    }

    @Override
    public void visit(Rovar rovar) {
    }

    @Override
    public void visit(Fonal fonal) {
    }

    @Override
    public void visit(Lava lava) {
        this.lava=lava;
    }

    @Override
    public void visit(TektonElem elem) {
    }

    private void clearState(){
        gombaTest=null;
        lava=null;
    }

    /**
     * Csak akkor nőhet fonál a from-ról a neighbour-ra, ha:
     * from == TektonElem || neighbour == TektonElem
     * neighbour-on nincs gombaTest
     * @param from
     * @param neighbour
     * @return
     */
    @Override
    public boolean canMove(Grid from, Grid neighbour) {
        clearState();
        from.accept((GridVisitor) this);
        if(lava!=null){
            lava=null;
            neighbour.accept((GridVisitor) this);
            if (lava != null) {
                return false;
            }
        }
        neighbour.accept((GameObjectVisitor) this);
        if(this.gombaTest!=null){
            return false;
        }
        return true;
    }

    FonalGrowLogic(Fonal fonal){
        this.fonal = fonal;
    }

}
