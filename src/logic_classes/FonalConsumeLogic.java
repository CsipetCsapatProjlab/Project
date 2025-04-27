package logic_classes;

import interfaces.GameObjectVisitor;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;

public class FonalConsumeLogic implements GameObjectVisitor {
    Fonal fonal;
    boolean haveEaten=false;

    public FonalConsumeLogic(Fonal fonal) {
        this.fonal = fonal;
    }

    @Override
    public void visit(Spora spora) {}
    @Override
    public void visit(GombaTest gombaTest) {}

    @Override
    public void visit(Rovar rovar) {
        if(Math.ceil(rovar.getEnergia())<=0 && !haveEaten){
            rovar.removeFromGrid();
            haveEaten=true;
        }
    }

    @Override
    public void visit(Fonal fonal) {}

    public boolean egyel(Grid celGrid){
        haveEaten=false;

        if(fonal.getPosition()==celGrid){
            celGrid.accept(this);
            return true;
        }
        return false;
    }

}
