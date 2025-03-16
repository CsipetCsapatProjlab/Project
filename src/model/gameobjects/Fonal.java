package model.gameobjects;

import java.util.Scanner;

import interfaces.GameObjectVisitor;
import interfaces.IGombasz;
import logic_classes.FonalGrowLogic;
import logic_classes.GombaTestPlaceLogic;
import model.enums.Hatas;
import model.grid.Grid;

public class Fonal extends GameObject {
    IGombasz gombaszObserver;
    FonalGrowLogic fonalGrowLogic;
    GombaTestPlaceLogic gombaTestPlaceLogic;

    public Fonal(Grid grid, IGombasz gObserver, FonalGrowLogic fgl, GombaTestPlaceLogic gtp) {
        super(grid);
        this.gombaszObserver = gObserver;
        this.fonalGrowLogic = fgl;
        this.gombaTestPlaceLogic = gtp;
    }

    @Override
    public void remove() {
        grid.torol(this);
        grid = null;
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }

    public void fonalNovesztes(Grid destination) {
        System.out.println("Ha van elérhető út: poz, ha nincs akkor 0");
        Scanner scanner = new Scanner(System.in);
        int b = scanner.nextInt();
        if(b < 1) return;
        System.out.println("Hany spora van, ha elegendo 1-nel nagyobb, ha nem akkor nem");
        int a = scanner.nextInt();
        if(a < 1) return;
        Fonal f = new Fonal(destination, gombaszObserver, fonalGrowLogic, gombaTestPlaceLogic);
        destination.hozzaAd(f);
        //gombaszObserver.addFonal(this);
    }

    public void gombaTestNovesztes() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hany spora van, ha elegendo 1-nel nagyobb, ha nem akkor nem");
        int a = scanner.nextInt();
        if(a < 1) return;
        if(grid.getHatas() == Hatas.NO_GOMBATEST) return;
        GombaTest g = new GombaTest(grid, gombaszObserver, null);
        grid.hozzaAd(g);
        //gombaszObserver.addTest(g);
    }
}
