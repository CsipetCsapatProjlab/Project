package model.players;

import java.util.ArrayList;
import java.util.List;

import model.Tekton;
import model.enums.Move;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.TektonElem;

public class Gombasz extends Jatekos {
    int kinottGombatest;
    List<Spora> sporak=new ArrayList<>();
    List<Fonal> fonalak=new ArrayList<>();
    List<GombaTest> gombaTestek=new ArrayList<>();

    public Gombasz(TektonElem grid, String nev) {
        super(nev);
        gombaTestek.add(new GombaTest(grid, this));
        grid.hozzaAd(gombaTestek.getFirst());
        kinottGombatest=0;
    }

    public Gombasz(List<Spora> s, List<Fonal> f, List<GombaTest> g){
        super("");
        sporak = s;
        fonalak = f;
        gombaTestek = g;
    }

    public List<GombaTest> getGombaTests(){return gombaTestek;}

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
                        try{
                            f.fonalNovesztes(cel);
                        }
                        catch (Exception e){}
                        return;
                    }
                }
                //később throw lesz
                System.out.println("Innen nem lehet fonalat növeszteni");
            }
            case GombaTest_noveszt -> {
                for(Fonal f : fonalak){
                    if (f.isAt(kezdo) /*&& kezdo.getTekton().getSporas().lentgh >= kezdo.getTekton().getFonalAr()*/) {
                        f.gombaTestNovesztes(cel);
                        return;
                    }
                }
            }
        }
    }

    public void add(GombaTest g){
        kinottGombatest++;
        gombaTestek.add(g);
    }

    public void add(Fonal f){
        fonalak.add(f);
    }
    public void add(Spora s){
        sporak.add(s);
    }

    public List<Spora> getSporas(){return sporak;}
}
