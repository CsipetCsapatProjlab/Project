package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    public void mentes() {
        String filePath = "mentes/jatekosok.txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Jatekos jatekos : jatekosok) {
                writer.write(jatekos.mentes());
                writer.newLine();
            }
            System.out.println("Játékosok mentése sikeres: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a játékosok mentésekor: " + e.getMessage());
        }
    }


    /**
     * Jatekallas betoltese
     */
    public void betoltes() {
        String filePath = "mentes/jatekosok.txt";
        File file = new File(filePath);
    
        if (!file.exists()) {
            System.err.println("A betöltendő fájl nem létezik: " + file.getAbsolutePath());
            return;
        }
    
        try (Scanner scanner = new Scanner(file)) {
            jatekosok.clear(); // Előző játékosokat töröljük
    
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
    
                String[] parts = line.split(";");
                if (parts.length != 4) {
                    System.err.println("Hibás sorformátum: " + line);
                    continue;
                }
    
                String nev = parts[0].trim();
                int meik = Integer.parseInt(parts[1].trim());
                int extra = Integer.parseInt(parts[2].trim());
                String tipus = parts[3].trim();
    
                switch (tipus) {
                    case "Rovarasz" -> {
                        Rovarasz rovarasz = new Rovarasz(null, nev);
                        rovarasz.setTapanyagok(extra);
                        rovarasz.meik = meik;
                        jatekosok.add(rovarasz);
                    }
                    case "Gombasz" -> {
                        Gombasz gombasz = new Gombasz(null, nev);
                        gombasz.setgombatest(extra);
                        gombasz.meik = meik;
                        jatekosok.add(gombasz);
                    }
                    default -> System.err.println("Ismeretlen típus: " + tipus);
                }
            }
    
            System.out.println("Játékosok betöltése sikeres: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Hiba történt a játékosok betöltésekor: " + e.getMessage());
        }
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
     */
    public void jatekosValasztas(Grid kezdoPoz, String jatekosNev, String jatekostype){
           try{
            switch (jatekostype){
                case "Rovarasz":
                    Rovarasz j = new Rovarasz((TektonElem) kezdoPoz, jatekosNev);
                    jatekosok.add(j);
                    break;
                case "Gombasz":
                    Gombasz jg = new Gombasz((TektonElem) kezdoPoz, jatekosNev);
                    jatekosok.add(jg);
                    break;
                default:
                    throw new IllegalArgumentException("Rossz jatekostipus!");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
