package model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.enums.Move;
import model.exceptions.InvalidMoveException;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.TektonElem;

import static model.enums.Move.GombaTest_noveszt;

public class Gombasz extends Jatekos {
    int kinottGombatest;
    List<Spora> sporak=new ArrayList<>();
    List<Fonal> fonalak=new ArrayList<>();
    List<GombaTest> gombaTestek=new ArrayList<>();


    @Override
    public int getPoints() {return kinottGombatest;}

    public List<Spora> getSporas(){return sporak;}
    public void setgombatest(int g){kinottGombatest = g;}
    public List<GombaTest> getGombaTests(){return gombaTestek;}
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

    public Gombasz(List<Spora> s, List<Fonal> f, List<GombaTest> g){
        super("");
        sporak = s;
        fonalak = f;
        gombaTestek = g;
    }


    /**
     * Fonal novesztes kezdo mezorol cel mezore, megadott modon
     * @param kezdo a grid ahonnan a move indul
     * @param cel ahova érkezik
     * @param move a move
     */
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) throws InvalidMoveException {
        switch (move) {
            case Fonal_noveszt -> {
                Optional<Fonal> f=fonalak.stream().filter(cur->cur.isAt(kezdo)).findFirst();
                Optional<GombaTest> gt=gombaTestek.stream().filter(cur->cur.isAt(kezdo)).findFirst();

                if(f.isPresent()){
                    f.get().fonalNovesztes(cel);
                }
                else{
                    if(gt.isPresent()){
                    //hackelős solution, egyenlőre jó lesz
                        Fonal ujFonal=new Fonal(kezdo,this);
                        ujFonal.fonalNovesztes(cel);
                    }
                }

                if(!f.isPresent() && !gt.isPresent()){
                    throw new InvalidMoveException("Hibas kezdo grid, "+move.name(),kezdo,cel,move);
                }
            }

            case GombaTest_noveszt -> {
                var fn=fonalak.stream().filter(cur->cur.isAt(kezdo)).findFirst();
                if(fn.isPresent()){
                    fn.get().gombaTestNovesztes(cel);
                }
                else{
                    throw new InvalidMoveException("Hibas kezdo grid, "+ move.name(),kezdo,cel,move);
                }
            }

            case Fonal_fogyaszt -> {
                var fn = fonalak.stream().filter(cur->cur.isAt(kezdo)).findFirst();
                if(fn.isPresent()){
                    fn.get().rovarFogyasztas();
                }
                else{
                    throw new InvalidMoveException("Hibas kezdo grid, "+ move.name(),kezdo,cel,move);
                }
            }


        }
    }

    public void hozzaAd(GombaTest g){
        kinottGombatest++;
        gombaTestek.add(g);
    }
    public void hozzaAd(Fonal f){fonalak.add(f);}
    public void hozzaAd(Spora s){sporak.add(s);}
    public void torol(Spora s){sporak.remove(s);}
    public void torol(GombaTest s){gombaTestek.remove(s);}
    public void torol(Fonal s){fonalak.remove(s);}

    @Override
    public String mentes(){return nev + " ; " + Integer.toString(melyik) + " ; " + Integer.toString(kinottGombatest) + " ; " + "Gombasz";}
}
