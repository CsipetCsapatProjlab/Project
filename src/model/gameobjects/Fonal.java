package model.gameobjects;

import interfaces.GameObjectVisitor;
import logic_classes.FonalGrowLogic;
import logic_classes.GombaTestPlaceLogic;
import model.enums.Hatas;
import model.grid.Grid;
import model.players.Gombasz;

public class Fonal extends GameObject {
    Gombasz gombasz;
    FonalGrowLogic fonalGrowLogic;
    GombaTestPlaceLogic gombaTestPlaceLogic;

    /**
     * Letrehozza a Fonalat
     * @param grid Melyik mezore
     * @param gombasz Ki birtokolja
     */
    public Fonal(Grid grid, Gombasz gombasz) {
        super(grid, gombasz);
        this.gombasz = gombasz;
        this.fonalGrowLogic = new FonalGrowLogic(this);
        this.gombaTestPlaceLogic = new GombaTestPlaceLogic();

        gombasz.add(this);
    }

    /**
     * Az objektum torli magat
     */
    @Override
    public void remove() {
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
        fonalGrowLogic.noveszt(destination);
    }

    /**
     * Noveszt egy testet a kivant gridre
     * @param grid Novesztes cel gridje
     */
    public void gombaTestNovesztes(Grid grid) {


    }
}
