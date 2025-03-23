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

    public void kovetkezoJatekos(){
        currentPlayer=(currentPlayer+1)%jatekosok.size();
    }

    public void kovetkezoLepes(Grid kezdo, Grid cel, Move move) {
        jatekosok.get(currentPlayer).lepes(kezdo, cel, move);
        currentPlayer++;
    }
    public void mentes(){
        
    }
    public void betoltes(){
        
    }
    public void start(){
        currentPlayer = 0;
        //kovetkezoJatekos(null, null, null);
    }
    public void jatekosValasztas(Grid[][] g){
        int jT = 0;
        String jatekosTipus; String jatekosNev; Grid kezdoPoz;
        Scanner scanner=new Scanner(System.in);
        System.out.println("JatekosTipus (Rovarasz/Gombasz):");
        jatekosTipus=scanner.nextLine();

        System.out.println("JatekosNev:");
        jatekosNev=scanner.nextLine();
        if(jatekosTipus.equals("Rovarasz") || jatekosTipus.equals("rovarasz")) jT = 1;
        else if(jatekosTipus.equals("Gombasz") || jatekosTipus.equals("gombasz")) jT = 2;

        System.out.println("KezdoPoz: (x,y)");
        String tmp = scanner.nextLine();
        String[] parts = tmp.split("\\s*,\\s*");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        if(x > g.length || y > g[0].length){
            System.out.println("Hibas koordinatakat adott meg!");
            jatekosValasztas(g);
        }
        TektonElem elem = (TektonElem) g[x][y];
        switch (jT){
            case 1:
                Rovarasz j = new Rovarasz(elem, jatekosNev);
                jatekosok.add(j);
                break;
            case 2:
                Gombasz jg = new Gombasz(elem, jatekosNev);
                jatekosok.add(jg);
                break;
            default:
                System.out.println("Hibas JatekosTipust adott meg!");
                jatekosValasztas(g);
        }
    }
}
