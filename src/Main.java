import GUI.FungoriumGUI;
import model.Fungorium;
import model.utils.Constants;
import testing.CommandLine;

public class Main {
    public static void main(String[] args) {
        Constants.loadConstants();
        Fungorium f2 = new Fungorium("src/test_script");
        FungoriumGUI fg = new FungoriumGUI(f2);
        System.out.println(f2);
        System.out.println(f2.getSzigetSzam());
        CommandLine cl = new CommandLine(f2);
        cl.start();
    }
}