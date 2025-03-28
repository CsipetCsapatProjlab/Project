package model.players;

import java.util.ArrayList;
import java.util.List;

import model.enums.Move;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;
import model.grid.Grid;

public class Gombasz extends Jatekos {
    int kinottGombatest;
    List<Spora> sporak=new ArrayList<>();
    List<Fonal> fonalak=new ArrayList<>();
    List<GombaTest> gombaTestek=new ArrayList<>();

    public Gombasz(Grid grid, String nev) {
        super(nev);
        gombaTestek.add(new GombaTest(grid, this));
        kinottGombatest=0;
    }

    public Gombasz(List<Spora> s, List<Fonal> f, List<GombaTest> g){
        super("");
        sporak = s;
        fonalak = f;
        gombaTestek = g;
    }

    /**
     *
     * @param kezdo a grid ahonnan a move indul
     * @param cel ahova érkezik
     * @param move a move
     */
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) {
        switch (move) {
            case Fonal_noveszt -> {
                for(Fonal f : fonalak){
                    if (f.isAt(kezdo)) {
                        f.fonalNovesztes(cel);
                        return;
                    }
                }
                //később throw lesz
                System.out.println("Innen nem lehet fonalat növeszteni");
            }
        }
    }
    public void addTest(GombaTest g){
        kinottGombatest++;
        gombaTestek.add(g);
    }
    public void addFonal(Fonal f){
        fonalak.add(f);
    }
    public List<Spora> getSporas(){return sporak;}
}
