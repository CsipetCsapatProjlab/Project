package logic_classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

public class GombaTestPlaceLogic implements GridVisitor, GameObjectVisitor {
    Fonal fonal;
    boolean vanGombaTest=false;
    List<Spora> sporas=new ArrayList<>();
    TektonElem tektonelem;

    @Override
    public void visit(Spora spora) {
        if(Objects.equals(spora.getObserver().getNev(), fonal.getObserver().getNev())) {
            sporas.add(spora);
        }
    }
    @Override
    public void visit(GombaTest gombaTest) {
        if (Objects.equals(gombaTest.getObserver().getNev(), fonal.getObserver().getNev())) {
            vanGombaTest = true;
        }
    }
    @Override
    public void visit(Rovar rovar) {}
    @Override
    public void visit(Fonal fonal) {}
    @Override
    public void visit(Lava lava) {}
    @Override
    public void visit(TektonElem elem) {}

    void clearState(){
        vanGombaTest = false;
        sporas.clear();
        tektonelem = null;
    }

    public GombaTestPlaceLogic(Fonal fonal) {
        this.fonal = fonal;
        clearState();
    }

    public Optional<List<Spora>> getGombaTestPlacement(Grid celgrid, int num) {
        clearState();

        celgrid.accept((GridVisitor) this);
        if (tektonelem != null) {
            tektonelem.getTekton().visitElements(this); // Ha gombatestre bukkanunk a tektonon, vagy ha spórára, ekkor pedig hozzáadjuk a listához
            if (!vanGombaTest && sporas.size() >= num) {
                return Optional.of(sporas);
            }
        }
        return Optional.empty();
    }


}
