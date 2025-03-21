package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.enums.Move;
import model.grid.Grid;
import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

public class JatekMotor {
    List<Jatekos> jatekosok=new ArrayList<>();
    int currentPlayer;

    public JatekMotor(){

    }

    public void jelenlegiJatekos() {

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
    public void getJatekosValasztas(Grid g){
        String jatekosTipus; String jatekosNev; Grid kezdoPoz;
        Scanner scanner=new Scanner(System.in);
        System.out.println("JatekosTipus (Rovarasz/Gombasz):");
        jatekosTipus=scanner.nextLine();

        System.out.println("JatekosNev:");
        jatekosNev=scanner.nextLine();

        System.out.println("KezdoPoz: (x,y)");
        kezdoPoz=g;

        switch (jatekosTipus){
            case "Rovarasz":
                jatekosok.add(new Rovarasz(g, jatekosNev));
                break;
            case "Gombasz":
                jatekosok.add(new Gombasz(g,jatekosNev));
        }
    }
}
