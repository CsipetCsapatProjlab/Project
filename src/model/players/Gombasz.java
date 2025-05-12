package model.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.exceptions.InvalidMoveException;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.TektonElem;

import static model.enums.Move.*;

public class Gombasz extends Jatekos {
    int kinottGombatest=0;
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
        hozzaAd(new GombaTest(grid, this));
    }

    public Gombasz(String nev) {
        super(nev);
    }

    public Gombasz(List<Spora> s, List<Fonal> f, List<GombaTest> g){
        super("");
        sporak = s;
        fonalak = f;
        gombaTestek = g;
    }

    /**
     * Vissaadja a azt a fonalat (ha nincs akkor InvalidMoveException), ami a megadott griden található
     * @param grid a megadott grid
     * @param exception az exception, amit dobunk
     * @return A fonal
     */
    private Fonal findFonalOnGrid(Grid grid, InvalidMoveException exception) throws InvalidMoveException {
        return fonalak.stream()
                .filter(cur->cur.isAt(grid))
                .findFirst()
                .orElseThrow(() -> exception);
    }

    /**
     * Vissaadja a azt a Gombatestet (ha nincs akkor InvalidMoveException), ami a megadott griden található
     * @param grid a megadott grid
     * @param exception az exception, amit dobunk
     * @return A gombatest
     */
    private GombaTest findGombaTestOnGrid(Grid grid, InvalidMoveException exception) throws InvalidMoveException {
        return gombaTestek.stream()
                .filter(cur->cur.isAt(grid))
                .findFirst()
                .orElseThrow(() -> exception);
    }



    /**
     * Fonal novesztes kezdo mezorol cel mezore, megadott modon
     * @param kezdo a grid ahonnan a move indul
     * @param cel ahova érkezik
     * @param move a move
     */
    @Override
    public void lepes(Grid kezdo, Grid cel, Move move) throws InvalidMoveException {

        InvalidMoveException exception = new InvalidMoveException("Hibas kezdo grid, " +move.name(), kezdo, cel, move);
        switch (move) {

            // Elméletileg az volt, hogy ahol gombatest van ott fonal is de nem akarok belenyúlni
            case Fonal_noveszt -> {
                Optional<Fonal> f=fonalak.stream().filter(cur->cur.isAt(kezdo)).findFirst();
                Optional<GombaTest> gt=gombaTestek.stream().filter(cur->cur.isAt(kezdo)).findFirst();

                if(f.isPresent()){
                    f.get().fonalNovesztes(cel);
                }
                else if(gt.isPresent()){
                    //hackelős solution, egyenlőre jó lesz
                    Fonal ujFonal=new Fonal(kezdo,this);
                    ujFonal.fonalNovesztes(cel);
                }

                if(f.isEmpty() && gt.isEmpty()){
                    throw exception;
                }
            }
            case Gombatest_noveszt -> findFonalOnGrid(kezdo, exception).gombaTestNovesztes(cel);
            case Fonal_fogyaszt -> findFonalOnGrid(kezdo, exception).rovarFogyasztas();
            case Spora_lo ->  findGombaTestOnGrid(kezdo, exception).sporaKilo(cel);
            case Gombatest_fejleszt -> {
                var gt=findGombaTestOnGrid(kezdo, exception);
                if (gt.getFejlesztett()) throw new FailedMoveException("A gombatest már fejlesztve van", gt, move);
                else gt.setFejlesztett();
            }
            case Kezdo_lepes -> {
                if(kezdo instanceof TektonElem e) firstMove(e);
                else throw new InvalidMoveException("Csak TektonElemre lehet lerakni gombatestet!",kezdo,cel,move);
            }
            default -> throw new InvalidMoveException("Hibas move! " + move.name(), kezdo, cel, move);

        }
    }

    private void firstMove(TektonElem elem) {
        hozzaAd(new GombaTest(elem,this));
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
    public String mentes(){return nev + " ; " + melyik + " ; " + kinottGombatest + " ; " + "Gombasz";}

    @Override
    public Move[] getMoveTypes() {
       List<Move> arr= new ArrayList<>(List.of(Fonal_noveszt, Spora_lo, Fonal_fogyaszt, Gombatest_noveszt, Gombatest_fejleszt));
       if(gombaTestek.isEmpty())
           arr.add(Kezdo_lepes);
       return arr.toArray(new Move[arr.size()]);
    }
}
