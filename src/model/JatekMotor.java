package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.enums.Move;
import model.grid.Grid;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

public class JatekMotor {
    List<Jatekos> jatekosok=new ArrayList<>();
    int currentPlayer;

    public JatekMotor(){

    }

    public Jatekos jelenlegiJatekos() {
        return jatekosok.get(currentPlayer);
    }

    public void kovetkezoJatekos(Grid kezdo, Grid cel, Move move) {
        jatekosok.get(currentPlayer).lepes(kezdo, cel, move);
    }
    public void mentes(){
        System.out.println("A jatek mentese sikeres!");
    }
    public void betoltes(){
        System.out.println("A jatek betoltese sikeres!");
    }
    public void start(){

    }
    public void jatekosValasztas(Grid[][] g){
        String jatekosTipus; String jatekosNev; Grid kezdoPoz;
        Scanner scanner=new Scanner(System.in);
        System.out.println("JatekosTipus (Rovarasz/Gombasz):");
        jatekosTipus=scanner.nextLine();

        System.out.println("JatekosNev:");
        jatekosNev=scanner.nextLine();

        System.out.println("KezdoPoz: (x,y)");
        String tmp = scanner.nextLine();
        String[] parts = tmp.split("\\s*,\\s*");
        TektonElem elem = (TektonElem) g[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];

        switch (jatekosTipus){
            case "Rovarasz":
                Rovarasz j = new Rovarasz(elem, jatekosNev);
                jatekosok.add(j);
                break;
            case "Gombasz":
                Gombasz jg = new Gombasz(elem,jatekosNev);
                jatekosok.add(jg);
            default:
                System.out.println("Hibas JatekosTipust adott meg!");
                jatekosValasztas(g);
        }
    }
}
