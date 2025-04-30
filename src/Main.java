import model.Fungorium;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Rovarasz;
import model.utils.Constants;
import testing.CommandLine;

public class Main {
    public static void main(String[] args) {
        Constants.loadConstants();


        Fungorium f2 = new Fungorium("src/test_script");
        System.out.println(f2);
        System.out.println(f2.getSzigetSzam());
        CommandLine cl = new CommandLine(f2);
        cl.start();
    }
}