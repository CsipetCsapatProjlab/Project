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
    private List<Jatekos> jatekosok;
    int currentPlayer = 0;

    public JatekMotor(){
        jatekosok = new ArrayList<>();
    }

    public void jatekosHozzaAd(Jatekos jatekos){
        jatekosok.add(jatekos);
    }

    /**
     * Visszaadja ki a jelenlegi jatekos
     * @return
     */
    public Jatekos jelenlegiJatekos() {
        return jatekosok.get(currentPlayer);
    }

    /**
     * Visszaadja ki lesz a kovetkezo jatekos
     */
    public void kovetkezoJatekos(){
        currentPlayer=(currentPlayer+1)%jatekosok.size();
    }

    /**
     * Lep a felhasznalo babujaval
     * @param kezdo Honnan
     * @param cel   Hova
     * @param move  Milyen modon
     */
    public void kovetkezoLepes(Grid kezdo, Grid cel, Move move) {
        try {
            jatekosok.get(currentPlayer).lepes(kezdo, cel, move);
        }catch (Exception e) {}
        currentPlayer++;
    }

    /**
     * Jatekallas mentese
     */
    public void mentes(){
        
    }

    /**
     * Jatekallas betoltese
     */
    public void betoltes(){
        
    }

    /**
     * Elinditja a jatekot az elso jatekossal
     */
    public void start(){
        currentPlayer = 0;
        //kovetkezoJatekos(null, null, null);
    }

    /**
     * Kiosztja a szerepeket a felhasznaloknak es meghatarozza a kezdohelyuket
     * @param g A jatek palyaja
     */
    public void jatekosValasztas(Grid[][] g){
        int jT = 0;
        String jatekosTipus; String jatekosNev; Grid kezdoPoz;
        try (Scanner scanner = new Scanner(System.in)) {
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
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
