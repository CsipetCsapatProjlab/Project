package logic_classes;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.Tekton;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.utils.GridUtils;
import model.grid.Lava;
import model.grid.TektonElem;
import model.players.Gombasz;

import java.util.List;
import java.util.Objects;



public class FonalGrowLogic extends DiscoverLogic{
    Fonal fonalOrigin;
    Lava lavaVisited;
    GombaTest gombaTestVisited;
    Tekton tektonVisited;
    int sporaCount=0;

    @Override
    public void visit(Spora spora) {
        if(Objects.equals(spora.getObserver().getNev(), fonalOrigin.getObserver().getNev())){
            sporaCount++;
        }
    }

    @Override
    public void visit(GombaTest gombaTest) {
        this.gombaTestVisited=gombaTest;
    }

    @Override
    public void visit(Rovar rovar) {
    }

    @Override
    public void visit(Fonal fonal) {
    }

    @Override
    public void visit(Lava lava) {
        this.lavaVisited=lava;
    }

    @Override
    public void visit(TektonElem elem) {
        tektonVisited=elem.getTekton();
    }

    private void clearState(){
        gombaTestVisited=null;
        lavaVisited=null;
        sporaCount=0;
        tektonVisited=null;
    }

    /**
     * https://www.iit.bme.hu/file/11582/feladat
     * "Ha egy tektonon spóra található, akkor az egy időre meggyorsítja a fonal növekedését."
     * @param tekton Ahonnan fonalat akarunk növeszteni
     * @return A játékos spóráinak száma az adott TektonElemen
     */
    private int getSporaOnTekton(Tekton tekton){
        sporaCount=0;
        tekton.visitElements(this);
        return sporaCount;
    }

    /**
     * TODO
     * @param sporaszam
     * @return
     */
    private double calculateWeight(int sporaszam){
        if(sporaszam==0) return 1;
        else{
            return 1/(double)sporaszam;
        }
    }

    /**
     * Csak akkor nőhet fonál a from-ról a neighbour-ra, ha:
     * from == TektonElem || neighbour == TektonElem
     * neighbour-on nincs gombaTest
     * @param from
     * @param neighbour
     * @return Nőhet oda?
     */
    private boolean possibleMove(Grid from, Grid neighbour){
        clearState();

        from.accept((GridVisitor) this);
        if(lavaVisited!=null){
            lavaVisited=null;
            neighbour.accept((GridVisitor) this);
            if (lavaVisited != null) {
                return false;
            }
        }
        neighbour.accept((GameObjectVisitor) this);
        if(this.gombaTestVisited!=null){
            return false;
        }
        return true;
    }

    /**
     * Ha lehet oda növeszteni, akkor annak a súlya
     * @param from ahonnan növesztünk
     * @param neighbour ahová növesztünk
     * @return Az élsúlya a from-neighbour élnek (Double.POSITIVE_INFINITY ha nem létezik!)
     */
    @Override
    public double canMove(Grid from, Grid neighbour) {
        if(!possibleMove(from,neighbour)) return Double.POSITIVE_INFINITY;

        Tekton fromElem;
        Tekton neighbourElem;

        // Megnézi hogy ugyanazon a tektonon van-e
        from.accept((GridVisitor) this);
        fromElem=tektonVisited;
        tektonVisited=null;
        neighbour.accept((GridVisitor) this);
        neighbourElem=tektonVisited;

        //Visszaadja az élsúlyokat
        if(neighbourElem==fromElem){
            return calculateWeight(getSporaOnTekton(fromElem));
        }
        else return 1; // Valamelyik biztosan láva, oda nehéz növeszteni.
    }

    /**
     * Csak akkor jó a növesztés, ha:
     * Kiválasztott Tekton
     * @param celGrid
     */

    public void noveszt(Grid celGrid, int depth) throws Exception {
        List<Grid> path= GridUtils.GridPathFinder.gridPathFind(fonalOrigin.getPosition(),celGrid,depth,this);
        if(!path.isEmpty()){
            path.removeFirst(); // Az első elem maga a kezdő fonál
            for (Grid g : path) {
                Fonal sp=new Fonal(g, (Gombasz)fonalOrigin.getObserver());
                g.hozzaAd(sp);
            }
        }
        else{
            throw new Exception("Nem tudtunk növeszteni!");
        }
    }

    public FonalGrowLogic(Fonal fonal){
        this.fonalOrigin = fonal;
    }

}
