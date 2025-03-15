package model.gameobjects;

import java.util.Scanner;

import interfaces.GameObjectVisitor;
import interfaces.IGombasz;
import logic_classes.SporaPlaceLogic;
import model.grid.Grid;

public class GombaTest extends GameObject {
    private IGombasz gombaszObserver;
    boolean fejlesztett = false;
    SporaPlaceLogic sporaPlaceLogic;

    public GombaTest(Grid grid, IGombasz gObserver,  SporaPlaceLogic spl) {
        super(grid);
        this.gombaszObserver = gObserver;
        this.sporaPlaceLogic = spl;
    }

    @Override
    public void remove() {
        //TODO
    }

    @Override
    public void accept(GameObjectVisitor visitor) {
        // TODO
    }
    public boolean getFejlesztett(){return fejlesztett;}

    public void setFejlesztett() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hany spora van, ha elegendo 1-nel nagyobb, ha nem akkor nem");
        int a = scanner.nextInt();
        if(a < 1) return;
        fejlesztett = true;
    }

    public void sporaKilo(Grid destination, Spora spora) {
        /*int a = 0;
        for (int i = 0; i < gombaszObserver.getSporas().size(); i++){
            if(gombaszObserver.getSporas() == spora) a++;
        }
        if(a < gombaszObserver.getSporas().size()) return;*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hany spora van, ha elegendo 1-nel nagyobb, ha nem akkor nem");
        int a = scanner.nextInt();
        if(a < 1) return;
        grid.torol(spora);
        destination.hozzaAd(spora);
    }
}
