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

    /**
     * A rovar táplálkozási logikáját végrehajtó metódus, amely ellenőrzi és kezeli a rovar
     * által fogyasztható elemeket a rácsban.
     *
     * A metódus két fő esetet kezel:
     * 1. Láva és fonal páros: A fonal eltávolítása a lávából
     * 2. Spóra és tekton páros: A spóra effektjének aktiválása és eltávolítása
     *
     * A metódus csak akkor hajtja végre a táplálkozást, ha a rovar energiaszintje pozitív.
     *
     * @param from A rács, amelyben a rovar táplálkozni fog
     * @return true ha a rovar pontosan egy elemet fogyasztott, false egyébként
     * @throws IllegalArgumentException ha a paraméter null
     *
     * @see Rovar#getEnergia()
     * @see Fonal#remove()
     * @see Lava#torol(model.gameobjects.GameObject)
     * @see Spora#effektAktival(Rovar)
     * @see Spora#remove()
     * @see TektonElem#torol(model.gameobjects.GameObject)
     */
    public boolean eszik(Grid from){
        if(from==null) throw new IllegalArgumentException("A honnan null értéket kapott.");

        clearState();
        from.accept((GridVisitor) this);
        from.accept((GameObjectVisitor) this);

        if(numberEaten==0 && rovar.getEnergia()>0){
            if(lava != null && fonal!=null){
                fonal.remove();
                lava.torol(fonal);
                numberEaten++;
            }
            if(spora!=null && tekton!=null){
                spora.effektAktival(rovar);
                spora.remove();
                tekton.torol(spora);
                numberEaten++;
            }
        }
        return numberEaten==1;
    }
}
