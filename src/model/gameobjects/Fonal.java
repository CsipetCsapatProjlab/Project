package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.FonalGrowLogic;
import logic_classes.GombaTestPlaceLogic;
import model.enums.Hatas;
import model.exceptions.IncompatibleGameObjectException;
import model.grid.Grid;
import model.players.Gombasz;

import java.util.List;

public class Fonal extends GameObject {
    Gombasz observer;
    FonalGrowLogic fonalGrowLogic;
    GombaTestPlaceLogic gombaTestPlaceLogic;

    /**
     * Letrehozza a Fonalat
     * @param grid Melyik mezore
     * @param gombasz Ki birtokolja
     */
    public Fonal(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        this.observer = gombasz;
        this.fonalGrowLogic = new FonalGrowLogic(this);
        this.gombaTestPlaceLogic = new GombaTestPlaceLogic(this);

        gombasz.add(this);
    }

    /**
     * Az objektum torli magat
     */
    @Override
    public void remove() throws IncompatibleGameObjectException {
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

    /**
     * Noveszt egy fonalat a kivant gridre
     * @param destination Novesztes cel gridje
     */
    public void fonalNovesztes(Grid destination) throws Exception {
        List<Grid> path=destination.gridPathFind(this.getPosition(),destination,10,fonalGrowLogic);
        if(!path.isEmpty()){
            path.removeFirst(); // Az első elem maga a kezdő fonál
            for (Grid g : path) {
                Fonal sp=new Fonal(g, observer);
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
            GombaTest gt=new GombaTest(grid, observer);
        }
    }
}
