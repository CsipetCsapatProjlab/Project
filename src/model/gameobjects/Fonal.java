package model.gameobjects;

import interfaces.GameObjectVisitor;
import java.util.LinkedList;
import logic_classes.FonalConsumeLogic;
import logic_classes.FonalGrowLogic;
import logic_classes.GombaTestPlaceLogic;
import model.grid.Grid;
import model.players.Gombasz;
import model.utils.GridUtils;

public class Fonal extends GameObject {
    Gombasz gombasz;
    FonalGrowLogic fonalGrowLogic;
    GombaTestPlaceLogic gombaTestPlaceLogic;
    FonalConsumeLogic fonalConsumeLogic;

    /**
     * Letrehozza a Fonalat
     * @param grid Melyik mezore
     * @param gombasz Ki birtokolja
     */
    public Fonal(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        this.gombasz = gombasz;
        this.fonalGrowLogic = new FonalGrowLogic(this);
        this.gombaTestPlaceLogic = new GombaTestPlaceLogic(this);
        this.fonalConsumeLogic = new FonalConsumeLogic(this);

        gombasz.add(this);
    }

    /**
     * Az objektum torli magat
     */
    @Override
    public void remove(){
        grid.torol(this);
        grid = null;
    }

    /**
     * Elfogadja a visitort
     */
    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    @Override
    protected String[] getData() {
        return new String[] {
                getClass().getSimpleName() + ": " + observer.getNev(),
        };
    }

    /**
     * Noveszt egy fonalat a kivant gridre
     * @param destination Novesztes cel gridje
     */
    public void fonalNovesztes(Grid destination) throws Exception {
        LinkedList<Grid> path=GridUtils.GridPathFinder.gridPathFind(this.getPosition(),destination,10,fonalGrowLogic);
        if(!path.isEmpty()){
            path.removeFirst(); // Az első elem maga a kezdő fonál
            for (Grid g : path) {
                Fonal sp=new Fonal(g, gombasz);
                g.hozzaAd(sp);
            }
        }
        else{
            throw new Exception("Nem tudtunk növeszteni!");
        }
    }

    /**
     * Noveszt egy testet a kivant gridre
     * @param grid Novesztes cel gridje
     */
    public void gombaTestNovesztes(Grid grid) {
        if(gombaTestPlaceLogic.placeGombaTest(grid, 5)){
            GombaTest gt=new GombaTest(grid, gombasz);
        }
    }
    public void rovarEves(Grid grid){
        fonalConsumeLogic.egyel(grid);
    }

    public Gombasz getGombasz(){return gombasz;}
}
