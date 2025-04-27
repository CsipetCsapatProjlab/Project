package logic_classes;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.exceptions.IncompatibleGameObjectException;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

import java.util.Optional;

public class RovarConsumeLogic implements GameObjectVisitor, GridVisitor {
    int numberEaten=0;
    Rovar rovar;

    TektonElem tekton;
    Lava lava;
    Fonal fonal;
    Spora spora;

    @Override
    public void visit(Spora spora) {
        this.spora = spora;
    }
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
        this.lava=lava;
    }
    @Override
    public void visit(TektonElem elem) {
        tekton = elem;
    }

    void clearState(){
        numberEaten=0;
        tekton=null;
        lava=null;
        fonal=null;
        spora=null;
    }

    public RovarConsumeLogic(Rovar r){
        rovar=r;
    }

    public Optional<Spora> eszikSpora(Grid from){
        if(from==null) throw new IllegalArgumentException("A honnan null értéket kapott.");

        clearState();
        from.accept((GridVisitor) this);
        from.accept((GameObjectVisitor) this);

        if(numberEaten==0 && rovar.getEnergia()>0){
            if(spora!=null && tekton!=null){
                return Optional.of(spora);
            }
        }
        return Optional.empty();
    }

    public Optional<Fonal> vagFonal(Grid from){
        if(from==null) throw new IllegalArgumentException("A honnan null értéket kapott.");

        clearState();
        from.accept((GridVisitor) this);
        from.accept((GameObjectVisitor) this);

        if(numberEaten==0 && rovar.getEnergia()>0){
            if(lava != null && fonal!=null){
                return Optional.of(fonal);
            }
        }
        return Optional.empty();
    }
}
