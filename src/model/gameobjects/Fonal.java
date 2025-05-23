package model.gameobjects;

import interfaces.GameObjectVisitor;
import java.util.LinkedList;
import logic_classes.FonalConsumeLogic;
import logic_classes.FonalGrowLogic;
import logic_classes.GombaTestPlaceLogic;
import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.grid.Grid;
import model.players.Gombasz;
import model.utils.Constants;
import model.utils.GridUtils;

public class Fonal extends GameObject {
    FonalGrowLogic fonalGrowLogic=new FonalGrowLogic(this);
    GombaTestPlaceLogic gombaTestPlaceLogic=new GombaTestPlaceLogic(this);
    FonalConsumeLogic fonalConsumeLogic=new FonalConsumeLogic(this);

    @Override
    public void accept(GameObjectVisitor visitor) {visitor.visit(this);}
    public Gombasz getGombasz(){return (Gombasz)observer;}

    public Fonal(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        gombasz.hozzaAd(this);
    }
    @Override
    public void removeFromGrid(){
        super.removeFromGrid();
        getGombasz().torol(this);
    }
    /**
     * Noveszt egy fonalat a kivant gridre
     * @param destination Novesztes cel gridje
     */
    public void fonalNovesztes(Grid destination) throws FailedMoveException{
        LinkedList<Grid> path=GridUtils.GridPathFinder.gridPathFind(this.getPosition(),destination,Constants.fonalNovesztesEnergia,fonalGrowLogic);
        if(!path.isEmpty()){
            path.removeFirst(); // Az első elem maga a kezdő fonál
            for (Grid g : path) {
                new Fonal(g, getGombasz());
            }
        }
        else{
            throw new FailedMoveException("A fonál növesztés nem sikerült!", this, Move.Fonal_noveszt);
        }
    }
    /**
     * Noveszt egy testet a kivant gridre
     * @param grid Novesztes cel gridje
     */
    public void gombaTestNovesztes(Grid grid) {
        var te=gombaTestPlaceLogic.getGombaTestPlacement(grid,Constants.gombaTestNovesztesEnergia);

        if(te.isPresent()){
            var sporak=te.get();
            for (int i = 0; i < Constants.gombaTestNovesztesEnergia; i++) {
                sporak.get(Constants.rnd.nextInt(sporak.size())).removeFromGrid();
            }
            new GombaTest(grid,getGombasz());
        }
        else{
            throw new FailedMoveException("A gombatest növesztés nem sikerült!", this, Move.Gombatest_noveszt);
        }
    }

    public void rovarFogyasztas(){
        var rovar=fonalConsumeLogic.getRovar(grid);

        if(rovar.isPresent()){
            rovar.get().removeFromGrid();
        }
        else{
            throw new FailedMoveException("A rovar elfogyasztasa nem sikerült!", this, Move.Fonal_fogyaszt);
        }
    }

    @Override
    protected String[] getData() {
        return new String[] {
                getClass().getSimpleName() + ": " + observer.getNev(),
        };
    }

    @Override
    public String toStringShort() {
        return "F";
    }
}
