import model.Fungorium;
import model.Fungorium;
import testing.CommandLine;
import testing.Tests;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Fungorium f = new Fungorium(20,100);
        System.out.println(f);
        System.out.println(f.getSzigetSzam());
    }
}