import model.Fungorium;
import model.Fungorium;
import testing.CommandLine;
import testing.Tests;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Fungorium f = new Fungorium(10,10);
        System.out.println(f);
        System.out.println(f.getSzigetSzam());
        f.ujKor();
    }
}