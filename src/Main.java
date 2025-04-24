import model.Fungorium;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Rovarasz;
import testing.CommandLine;

public class Main {
    public static void main(String[] args) {
        Fungorium f = new Fungorium(10,10);
        Gombasz g = new Gombasz((TektonElem) f.getGrid(0, 0),"g");
        Rovarasz r = new Rovarasz((TektonElem) f.getGrid(0, 1),"r");
        f.addJatekos(g);
        f.addJatekos(r);
        System.out.println(f);
        System.out.println(f.getSzigetSzam());
        f.ujKor();
        System.out.println(f);
        System.out.println(f.getSzigetSzam());
        Fungorium f2 = new Fungorium("mentes");
        System.out.println(f2);
        System.out.println(f2.getSzigetSzam());
        CommandLine cl = new CommandLine(f);
        cl.start();
    }
}