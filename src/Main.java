import model.Fungorium;
import model.Fungorium;
import testing.CommandLine;
import testing.Tests;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Pelda sorozat
        /*testing.MyLogger.Visit("asd");
        testing.MyLogger.Visit("asd2");
        testing.MyLogger.In("Van bab? [y/n]", true); //Valaszt var
        testing.MyLogger.Return("asd2");
        testing.MyLogger.In("bab2 created", false); //Nem var valaszt
        testing.MyLogger.Visit("asd3");
        testing.MyLogger.Return("asd3");
        testing.MyLogger.Return("asd");
        Tests t = new Tests();
        t.newsetup();
        t.start();
         */

        Fungorium fg = new Fungorium();
        CommandLine cl = new CommandLine(fg);
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Enter command: ");
            String command = scanner.nextLine();
            if (command.equals("quit")) {
                quit = true;
            } else {
                cl.executeCommand(command);
            }
        }
        Fungorium f = new Fungorium(20,100);
        System.out.println(f);
        System.out.println(f.getSzigetSzam());
    }
}