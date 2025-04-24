package model.players;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Letrehozza a gombaszt
     * @param grid Kezdo test helye
     * @param nev Identifikacios nev
     */
    public Gombasz(TektonElem grid, String nev) {
        super(nev);
        gombaTestek.add(new GombaTest(grid, this));
        kinottGombatest=0;
    }
    public Gombasz(String nev) {
        super(nev);
        kinottGombatest=0;
    }

    /**
     *
     * @param s
     * @param f
     * @param g
     */
    public Gombasz(List<Spora> s, List<Fonal> f, List<GombaTest> g){
        super("");
        sporak = s;
        fonalak = f;
        gombaTestek = g;
    }

    /**
     * Visszaadja a gombasz testeit
     * @return
     */
    public List<GombaTest> getGombaTests(){return gombaTestek;}

    /**
     * Fonal novesztes kezdo mezorol cel mezore, megadott modon
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

    /**
     * Gombatest regisztralasa
     * @param g
     */
    public void add(GombaTest g){
        kinottGombatest++;
        gombaTestek.add(g);
    }

    /**
     * Fonal regisztralasa
     * @param f
     */
    public void add(Fonal f){
        fonalak.add(f);
    }

    @Override
    public int getPoints() {return kinottGombatest;}

    /**
     * Spora regisztralasa
     * @param s
     */
    public void add(Spora s){
        sporak.add(s);
    }

    /**
     * Spora lista lekerdezese
     */
    public List<Spora> getSporas(){return sporak;}

    public String mentes(){
        return nev + " ; " + Integer.toString(meik) + " ; " + Integer.toString(kinottGombatest) + " ; " + "Gombasz";
    }

    public void setgombatest(int g){
        kinottGombatest = g;
    }
}
