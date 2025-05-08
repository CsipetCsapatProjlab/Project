package logic_classes;

import interfaces.GameObjectVisitor;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;

import java.util.Optional;

public class FonalConsumeLogic implements GameObjectVisitor {
    Fonal fonal;
    Rovar rovar;

    public FonalConsumeLogic(Fonal fonal) {
        this.fonal = fonal;
    }

    @Override
    public void visit(Spora spora) {}
    @Override
    public void visit(GombaTest gombaTest) {}

    @Override
    public void visit(Rovar rovar) {
        if(Math.ceil(rovar.getEnergia())<=0 ){
            this.rovar=rovar;
        }
    }
    @Override
    public void visit(Fonal fonal) {}

    public Optional<Rovar> getRovar(Grid celGrid){
        rovar=null;

        if(fonal.getPosition()!=celGrid)
            return Optional.empty();
        else{
            celGrid.accept(this);
            return Optional.of(rovar);
        }
    }

}
