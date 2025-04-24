package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.enums.Hatas;
import model.enums.Move;
import model.enums.TektonelemTypes;
import model.gameobjects.BenitoSpora;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;
import model.gameobjects.GombaTest;
import model.gameobjects.GyorsSpora;
import model.gameobjects.LassitoSpora;
import model.gameobjects.OsztodoRovarSpora;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;

import model.grid.EgyFonal;
import model.grid.FonalEvo;
import model.grid.FonalTarto;
import model.grid.GombaTestEvo;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

import model.grid.*;
import model.grid.GombaTestEvo;
import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

public class Fungorium {
    JatekMotor motor;// A játék motorja
    Grid[][] map; // A pálya amin a játék játszódij
    boolean[][] szigetekKeret; // Segéd tömb ami a genrálást segíti
    char[][] test; // Segéd tömb ami a genrálást segíti és
    List<Tekton> tektons; // Tektonok listája
    int sor; // A pálya sorainak a száma
    int oszlop; // A pálya oszlopainak a száma
    int lavaszam = 0; // Azt tartja számon hány láva van a pályán
    Random rand = new Random();
    int szigetekSzama = 0;// Tektonok száma

    public Fungorium(int ujsor, int ujoszlop) { // A pályát létrehozó konstruktor meghív minden fügvényt ami ahoz kell
                                                // hogy a pálya létrejöjjön
        sor = ujsor;
        oszlop = ujoszlop;
        tektons = new ArrayList<>();
        map = new Grid[sor][oszlop];
        szigetekKeret = new boolean[sor][oszlop];
        test = new char[sor][oszlop];
        motor = new JatekMotor();

        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                test[i][j] = ' ';
                szigetekKeret[i][j] = false;
            }
        }

        // Itt generálódik a pálya először a lávák majd megkeresi a tektonokat és ha a
        // végeradmény nem felel meg újra kezdi
        boolean sikeres = false;
        while (!sikeres) {
            while (lavaszam <= (sor * oszlop / 3)) {
                this.generateMaze(rand.nextInt(sor), rand.nextInt(oszlop));
            }
            findSziget();
            if (szigetekSzama > 3) {
                sikeres = true;
            } else {
                reset();
            }
        }
        this.parosit();
        this.findszomszed();
    }

    /// Visszaadja a sor és oszlpszámot
    public int[] getShape() {
        return new int[] { sor, oszlop };
    }

    /// Megadja a játkmotorban lévő játkésok listáját egy tömben
    public Jatekos[] getPlayers() {
        return motor.getJatekosok().toArray(new Jatekos[0]);
    }

    // Stringé allakítja a pályát
    public String toString() {
        for (int i = 0; i <= this.oszlop + 1; i++) {
            System.out.print("0");
        }
        System.out.println();
        for (int i = 0; i < this.sor; i++) {
            System.out.print("0");
            for (int j = 0; j < this.oszlop; j++) {
                System.out.print(map[i][j]);
            }
            System.out.print("0");
            System.out.println();
        }
        for (int i = 0; i <= this.oszlop + 1; i++) {
            System.out.print("0");
        }
        System.out.println();
        return "";
    }

    void generateMaze(int x, int y) { // legenerálja a pályát
        if (szigetekKeret[x][y]) { // ellenőrzi hogy voltunk-e már azon a mezőn
            return;
        }
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop) {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        int merre = -1;
        int elozo = -1;
        if (lavaPlace(x, y)) {
            test[x][y] = '#';
            map[x][y] = new Lava();
            szigetekKeret[x][y] = true;
            this.lavaszam++;
        } else {
            return;
        }

        // Random kiválasztja merre akar menni és azt hogy hányszor próbálkozik
        // Azért loopol 2-t, mert:
        // - önmagába vissza nem tud fordulni
        // - ugyanabba az irányba nem megy tovább
        for (int i = 0; i < 2; i++) {
            do {
                merre = rand.nextInt(4);
            } while (merre == elozo);
            elozo = merre;
            if (x - 1 >= 0 && y - 1 >= 0 && x + 1 < this.sor && y + 1 < this.oszlop) {
                irany(merre, x, y);
            }
        }
    }

    // Egy ellenőrző hogy ne alakulhasson ki 2x2 es láva rész
    private boolean lavaPlace(int x, int y) {
        if (x > 0 && y > 0) {
            if (test[x - 1][y - 1] == '#' && test[x - 1][y - 1] == test[x - 1][y]
                    && test[x - 1][y - 1] == test[x][y - 1]) {
                return false;
            }
        }
        if (x < this.sor - 1 && y > 0) {
            if (test[x + 1][y - 1] == '#' && test[x + 1][y - 1] == test[x + 1][y]
                    && test[x + 1][y - 1] == test[x][y - 1]) {
                return false;
            }
        }
        if (x > 0 && y < this.oszlop - 1) {
            if (test[x - 1][y + 1] == '#' && test[x - 1][y + 1] == test[x - 1][y]
                    && test[x - 1][y + 1] == test[x][y + 1]) {
                return false;
            }
        }
        if (x < this.sor - 1 && y < this.oszlop - 1) {
            if (test[x + 1][y + 1] == '#' && test[x + 1][y + 1] == test[x + 1][y]
                    && test[x + 1][y + 1] == test[x][y + 1]) {
                return false;
            }
        }
        return true;
    }

    // A generálásnál a generálás irányába folytatja a rekurziót
    private void irany(int merre, int x, int y) {
        switch (merre) {
            case 0:
                generateMaze(x - 1, y);
                break;
            case 1:
                generateMaze(x + 1, y);
                break;
            case 2:
                generateMaze(x, y - 1);
                break;
            case 3:
                generateMaze(x, y + 1);
                break;
            default:
                break;
        }
    }

    // Megtaláljuk a tektonokat és hozzájuk tesszük a tekton elemeket
    void findSziget() {
        szigetekSzama = 0;

        // Beállítjuk a tömböt a keresés segítéséhez
        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                if (test[i][j] == '#') {
                    szigetekKeret[i][j] = true;
                } else {
                    szigetekKeret[i][j] = false;
                }
            }
        }

        for (int i = 0; i < this.sor; i++) {
            for (int j = 0; j < this.oszlop; j++) {
                if (!szigetekKeret[i][j]) {
                    TektonelemTypes[] hatasok = TektonelemTypes.values();
                    Tekton t = new Tekton(hatasok[rand.nextInt(hatasok.length)]);
                    connectSziget(i, j, t);
                    szigetekSzama++;
                    tektons.add(t);
                }
            }
        }
    }

    void connectSziget(int x, int y, Tekton t) {// Összeköti a tektonelemeket a tektonokhoz
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        if (szigetekKeret[x][y]) { // ellenőrzi hogy voltunk-e már azon a mezőn
            return;
        }
        szigetekKeret[x][y] = true;
        // Megfelelő tektoneleme létrehozása
        switch (t.getHatas()) {
            case GOMBATESTEVO:
                map[x][y] = new GombaTestEvo(t);
                t.addelem((TektonElem) map[x][y]);
                test[x][y] = '1';
                break;
            case FONALTARTO:
                map[x][y] = new FonalTarto(t);
                t.addelem((TektonElem) map[x][y]);
                test[x][y] = '2';
                break;
            case FONALEVO:
                map[x][y] = new FonalEvo(t);
                t.addelem((TektonElem) map[x][y]);
                test[x][y] = '3';
                break;
            case EGYFONAL:
                map[x][y] = new EgyFonal(t);
                t.addelem((TektonElem) map[x][y]);
                test[x][y] = '4';
                break;

            default:
                System.err.println("Nem kezelt tektonfalyta");
                break;
        }
        if (x - 1 >= 0) {
            connectSziget(x - 1, y, t);
        }
        if (x + 1 < this.sor) {
            connectSziget(x + 1, y, t);
        }
        if (y - 1 >= 0) {
            connectSziget(x, y - 1, t);
        }
        if (y + 1 < this.oszlop) {
            connectSziget(x, y + 1, t);
        }
    }

    // reseteli a pályát egy rossz generálás esetén
    void reset() {
        // Törlés minden pályával kapcsolatos adatot
        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                test[i][j] = ' ';
                szigetekKeret[i][j] = false;
                map[i][j] = null; // A pálya cellái is null-ra kerülnek
            }
        }

        // Törlés minden tektonikus elemről
        tektons.clear();

        // Változók alaphelyzetbe állítása
        szigetekSzama = 0;
        lavaszam = 0;
    }

    private void parosit() { // Összepárosítja a szomszédos grideket
        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                Grid[] szomszedok = new Grid[4];
                int hany = 0;
                if (i - 1 > 0) { // fel
                    szomszedok[hany] = map[i - 1][j];
                    hany++;
                }
                if (i + 1 < sor) { // le
                    szomszedok[hany] = map[i + 1][j];
                    hany++;
                }
                if (j + 1 < oszlop) { // jobb
                    szomszedok[hany] = map[i][j + 1];
                    hany++;
                }
                if (j - 1 > 0) { // ball
                    szomszedok[hany] = map[i][j - 1];
                    hany++;
                }
                map[i][j].setNeighbours(szomszedok, hany);
            }
        }
    }

    // Tekton szomszéd keresés része
    void findszomszed() {
        for (int i = 0; i < this.sor; i++) {
            for (int j = 0; j < this.oszlop; j++) {
                if (!szigetekKeret[i][j]) {
                    tektonSzomszedKeres(i, j);
                }
            }
        }
    }

    public void tektonSzomszedKeres(int x, int y) {// megkeresi a tektonok szomszédjait
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        // hozzárendeleés feltételei
        if (x - 2 >= 0 && test[x - 1][y] == '#' && test[x - 2][y] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x - 2][y]).getTekton());
        }
        if (x + 2 < this.sor && test[x + 1][y] == '#' && test[x + 2][y] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x + 2][y]).getTekton());
        }
        if (y - 2 >= 0 && test[x][y - 1] == '#' && test[x][y - 2] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x][y - 2]).getTekton());
        }
        if (y + 2 < this.oszlop && test[x][y + 1] == '#' && test[x][y + 2] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x][y + 2]).getTekton());
        }

        if (x - 1 >= 0) {
            tektonSzomszedKeres(x - 1, y);
        }
        if (x + 1 < this.sor) {
            tektonSzomszedKeres(x + 1, y);
        }
        if (y - 1 >= 0) {
            tektonSzomszedKeres(x, y - 1);
        }
        if (y + 1 < this.oszlop) {
            tektonSzomszedKeres(x, y + 1);
        }
    }

    /// visszaadja a Tektonok számát
    public int getSzigetSzam() {
        return szigetekSzama;
    }

    /// Visszadja a játékmotort
    public JatekMotor getMotor() {
        return motor;
    }

    // szakadás előkészítő fügvény
    public void szakad() {
        for (int i = 0; i < 10; i++) {
            // kiválasztjuk a pontott amiből indulunk
            Tekton valasztott = tektons.get(rand.nextInt(tektons.size()));
            if (valasztott.getTektonszam() > 6 && valasztott.getTektonszam() > valasztott.getTektonszam() / 2) {
                List<TektonElem> elemek = valasztott.getTektonElems();
                TektonElem valasz = elemek.get(rand.nextInt(elemek.size()));
                int mezosor = -1;
                int mezooszlop = -1;
                for (int x = 0; x < sor; x++) {
                    for (int y = 0; y < oszlop; y++) {
                        if (valasz.equals(map[x][y])) {
                            mezosor = x;
                            mezooszlop = y;
                        }
                    }
                }
                // Szakadunk
                if (mezooszlop != -1 && mezosor != -1 && map[mezosor][mezooszlop].getSzomszedokSzama() == 4) {
                    szakadjon(mezosor, mezooszlop);
                }
                parosit();
                keresTekton();
                findszomszed();
            }
        }
    }

    // a szakadást végző fügvény
    private void szakadjon(int kezdosor, int kezdooszlop) {
        if (lavaPlace(kezdosor, kezdooszlop)) {
            test[kezdosor][kezdooszlop] = '#';
            map[kezdosor][kezdooszlop] = new Lava();
        }
        System.out.println(kezdosor + " " + kezdooszlop);
        if (kezdosor - 1 >= 0 && szomszedLava(kezdosor, kezdooszlop) <= 2) {
            if (test[kezdosor - 1][kezdooszlop] != '#') {
                szakadjon(kezdosor - 1, kezdooszlop);
            }
        }
        if (kezdosor + 1 < this.sor && szomszedLava(kezdosor, kezdooszlop) <= 2) {
            if (test[kezdosor + 1][kezdooszlop] != '#') {
                szakadjon(kezdosor + 1, kezdooszlop);
            }
        }
        if (kezdooszlop - 1 >= 0 && szomszedLava(kezdosor, kezdooszlop) <= 2) {
            if (test[kezdosor - 1][kezdooszlop] != '#') {
                szakadjon(kezdosor, kezdooszlop - 1);
            }
        }
        if (kezdooszlop + 1 < this.oszlop && szomszedLava(kezdosor, kezdooszlop) <= 2) {
            if (test[kezdosor - 1][kezdooszlop] != '#') {
                szakadjon(kezdosor, kezdooszlop - 1);
            }
        }
    }

    // Ellenőrizzük hány szomszéd van aki láva
    private int szomszedLava(int x, int y) {
        int mennyi = 0;
        if (x - 1 >= 0) {
            if (test[x - 1][y] == '#') {
                mennyi++;
            }
        } else {
            mennyi++;
        }
        if (x + 1 < this.sor) {
            if (test[x + 1][y] == '#') {
                mennyi++;
            }
        } else {
            mennyi++;
        }
        if (y - 1 >= 0) {
            if (test[x][y - 1] == '#') {
                mennyi++;
            }
        } else {
            mennyi++;
        }
        if (y + 1 < this.oszlop) {
            if (test[x][y + 1] == '#') {
                mennyi++;
            }
        } else {
            mennyi++;
        }
        return mennyi;
    }

    // Szakadás után újra rendelni a tektonokhoz a tektonelemeket
    void keresTekton() {
        tektons.clear();
        szigetekSzama = 0;
        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                if (test[i][j] == '#') {
                    szigetekKeret[i][j] = true;
                } else {
                    szigetekKeret[i][j] = false;
                }
            }
        }
        for (int i = 0; i < this.sor; i++) {
            for (int j = 0; j < this.oszlop; j++) {
                if (!szigetekKeret[i][j]) {
                    Tekton t = null;
                    if (test[i][j] == '1') {
                        t = new Tekton(TektonelemTypes.GOMBATESTEVO);
                    } else if (test[i][j] == '2') {
                        t = new Tekton(TektonelemTypes.FONALTARTO);
                    } else if (test[i][j] == '3') {
                        t = new Tekton(TektonelemTypes.FONALEVO);
                    } else if (test[i][j] == '4') {
                        t = new Tekton(TektonelemTypes.EGYFONAL);
                    }
                    szakadKeresTekton(i, j, t);
                    szigetekSzama++;
                    tektons.add(t);
                }
            }
        }
    }

    void szakadKeresTekton(int x, int y, Tekton t) {// Összeköti a tektonelemeket a tektonokhoz
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        if (szigetekKeret[x][y]) { // ellenőrzi hogy voltunk-e már azon a mezőn
            return;
        }
        szigetekKeret[x][y] = true;
        t.addelem((TektonElem) map[x][y]);
        ((TektonElem) map[x][y]).setTekton(t);
        if (x - 1 >= 0) {
            szakadKeresTekton(x - 1, y, t);
        }
        if (x + 1 < this.sor) {
            szakadKeresTekton(x + 1, y, t);
        }
        if (y - 1 >= 0) {
            szakadKeresTekton(x, y - 1, t);
        }
        if (y + 1 < this.oszlop) {
            szakadKeresTekton(x, y + 1, t);
        }
    }

    /// új kört kezeli megpróbálja szakadást előállítani és menti az állást
    public void ujKor() {
        szakad();
        mentes("mentes");
    }

    /// Menti a pálya állását a kijelölt mappába és abba létrehozza a a filokat vagy ha létezik felülírja
    public void mentes(String alapmappa) {
        String filePath = alapmappa + "/tekton/palya_tektonelemek.txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Létrehozza a mappastruktúrát, ha még nem létezik

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < sor; i++) {
                for (int j = 0; j < oszlop; j++) {
                    writer.write(test[i][j]);
                }
                writer.newLine();
            }
            System.out.println("Pálya mentése sikeres: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a pálya mentésekor: " + e.getMessage());
        }
        gombaMentes(alapmappa);
        fonalMentes(alapmappa);
        sporaMentes(alapmappa);
        rovarMentes(alapmappa);
        motor.mentes();
        saveMapSize(alapmappa + "/tekton/valtozok.txt");
    }

    /// menti a gombák helyét és azt hogy a listában meik játékoshoz tartozik
    public void gombaMentes(String alapmappa) {
        String filePath = alapmappa + "/objetumok/Gombak.txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        List<GameObject> elemek;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < sor; i++) {
                for (int j = 0; j < oszlop; j++) {
                    elemek = map[i][j].getGameObject();
                    boolean gomba = false;
                    GombaTest test = null;
                    for (GameObject gameObject : elemek) {
                        if (gameObject instanceof GombaTest) {
                            gomba = true;
                            test = (GombaTest) gameObject;
                        }
                    }
                    if (gomba) {
                        writer.write(Integer.toString(test.getGombasz().meik));
                    } else {
                        writer.write("-");
                    }
                }
                writer.newLine();
            }
            System.out.println("Gombatestetek mentése sikeres: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a gombatestek mentésekor: " + e.getMessage());
        }
    }

    // Menti a fonalakat a szerint hogy a motorban meik játékoshoz tartozik
    public void fonalMentes(String alapmappa) {
        String filePath = alapmappa + "/objetumok/Fonalak.txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        List<GameObject> elemek;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < sor; i++) {
                for (int j = 0; j < oszlop; j++) {
                    elemek = map[i][j].getGameObject();
                    boolean gomba = false;
                    Fonal test = null;
                    for (GameObject gameObject : elemek) {
                        if (gameObject instanceof Fonal) {
                            gomba = true;
                            test = (Fonal) gameObject;
                        }
                    }
                    if (gomba) {
                        writer.write(Integer.toString(test.getGombasz().meik));
                    } else {
                        writer.write("-");
                    }
                }
                writer.newLine();
            }
            System.out.println("Fonal mentése sikeres: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a fonal mentésekor: " + e.getMessage());
        }
    }

    /// menti a sporákat a szerint hogy milyen fajta spóra
    public void sporaMentes(String alapmappa) {
        String filePath = alapmappa + "/objetumok/Sporak.txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        List<GameObject> elemek;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < sor; i++) {
                for (int j = 0; j < oszlop; j++) {
                    elemek = map[i][j].getGameObject();
                    char meik = '-';
                    for (GameObject gameObject : elemek) {
                        if (gameObject instanceof BenitoSpora) {
                            meik = '1';
                        } else if (gameObject instanceof GyorsSpora) {
                            meik = '2';
                        } else if (gameObject instanceof LassitoSpora) {
                            meik = '3';
                        } else if (gameObject instanceof OsztodoRovarSpora) {
                            meik = '4';
                        }
                    }
                    writer.write(meik);
                }
                writer.newLine();
            }
            System.out.println("sporak mentése sikeres: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a sporak mentésekor: " + e.getMessage());
        }
        sporaKiMentes(alapmappa);
    }

    /// azt menti melyik spóra meik játékoshoz tartozik
    public void sporaKiMentes(String alapmappa) {
        String filePath = alapmappa + "/objetumok/Sporak_kihez.txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        List<GameObject> elemek;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < sor; i++) {
                for (int j = 0; j < oszlop; j++) {
                    elemek = map[i][j].getGameObject();
                    boolean gomba = false;
                    Spora test = null;
                    for (GameObject gameObject : elemek) {
                        if (gameObject instanceof Spora) {
                            gomba = true;
                            test = (Spora) gameObject;
                        }
                    }
                    if (gomba) {
                        writer.write(Integer.toString(test.getGombasz().meik));
                    } else {
                        writer.write("-");
                    }
                }
                writer.newLine();
            }
            System.out.println("Sporakinek mentése sikeres: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a sporakinek mentésekor: " + e.getMessage());
        }
    }

    /// menti a rovarakat-a szerint kihez tartoznak és hol vannak
    public void rovarMentes(String alapmappa) {
        String filePath = alapmappa + "/objetumok/Rovarok.txt";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        List<GameObject> elemek;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < sor; i++) {
                for (int j = 0; j < oszlop; j++) {
                    elemek = map[i][j].getGameObject();
                    boolean gomba = false;
                    Rovar test = null;
                    for (GameObject gameObject : elemek) {
                        if (gameObject instanceof Rovar) {
                            gomba = true;
                            test = (Rovar) gameObject;
                        }
                    }
                    if (gomba) {
                        writer.write(Integer.toString(test.getRovarasz().meik));
                    } else {
                        writer.write("-");
                    }
                }
                writer.newLine();
            }
            System.out.println("Rovarok mentése sikeres: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a Rovarok mentésekor: " + e.getMessage());
        }
    }

    /// menti a pálya sorai és oszlopainak a számát
    public void saveMapSize(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(sor + "," + oszlop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /// betölti a pálya oszlopainak és sorainak a számát
    public void loadMapSize(String path) {
        int[] values = new int[2]; // [sor, oszlop]
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            String[] parts = line.split(",");
            values[0] = Integer.parseInt(parts[0]);
            values[1] = Integer.parseInt(parts[1]);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            values[0] = 10; // default fallback érték
            values[1] = 10;
        }
        this.sor = values[0];
        this.oszlop = values[1];
    }
    /// Egyszerű másolás, a másolt objektumban és ebben ugyanazokra lesz referencia
    private void shallowCopy(Fungorium other) {
        this.motor = other.motor;
        this.map = other.map;
        this.szigetekKeret = other.szigetekKeret;
        this.test = other.test;
        this.tektons = other.tektons;
        this.sor = other.sor;
        this.oszlop = other.oszlop;
        this.lavaszam = other.lavaszam;
        this.rand = other.rand;
        this.szigetekSzama = other.szigetekSzama;
    }

    /// Egy egyszerű betöltés
    public void betoltes(String name){
        shallowCopy(new Fungorium(name));
    }
    /// Egy egyszerű generálás
    public void ujraGeneralas() {
        shallowCopy(new Fungorium(sor, oszlop));
    }

    /// Egy új pályát hoz létre egy korábbi mentés alapján
    public Fungorium(String alapmappa) {
        loadMapSize(alapmappa + "/tekton/valtozok.txt");

        map = new Grid[sor][oszlop];
        test = new char[sor][oszlop];
        szigetekKeret = new boolean[sor][oszlop];
        tektons = new ArrayList<>();
        motor = new JatekMotor();
        motor.betoltes();

        // 1. Pálya típusainak betöltése
        try (BufferedReader reader = new BufferedReader(new FileReader("mentes/tekton/palya_tektonelemek.txt"))) {
            for (int i = 0; i < sor; i++) {
                String line = reader.readLine();
                for (int j = 0; j < oszlop; j++) {
                    test[i][j] = line.charAt(j);
                    Tekton idegigelene = new Tekton();
                    switch (test[i][j]) {
                        case '1' -> map[i][j] = new GombaTestEvo(idegigelene);
                        case '2' -> map[i][j] = new FonalTarto(idegigelene);
                        case '3' -> map[i][j] = new FonalEvo(idegigelene);
                        case '4' -> map[i][j] = new EgyFonal(idegigelene);
                        case '#' -> map[i][j] = new Lava();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Nem sikerült betölteni a pályát: " + e.getMessage());
        }

        // 2. GameObject-ek betöltése
        betoltGombatestek(alapmappa + "/objetumok/Gombak.txt");
        betoltFonalak(alapmappa + "/objetumok/Fonalak.txt");
        betoltSporak(alapmappa + "/objetumok/Sporak.txt", "mentes/objetumok/Sporak_kihez.txt");
        betoltRovarok(alapmappa + "/objetumok/Rovarok.txt");

        // 3. Újrarendezés és szomszédok
        keresTekton();
        parosit();
        findszomszed();
    }

    /// betölti a gombákat és hozzáadja őket a játékosaikhoz
    private void betoltGombatestek(String path) {
        List<Jatekos> jatekosok = motor.getJatekosok();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < sor; i++) {
                String line = reader.readLine();
                if (line == null || line.length() < oszlop) {
                    System.err.println("Hibás sor a fájlban a " + i + ". sorban. Elég rövid!");
                    continue; // Ha a sor túl rövid, lépj a következőre
                }
                for (int j = 0; j < oszlop; j++) {
                    char currentChar = line.charAt(j);
                    if (currentChar != '-') {
                        // Ellenőrizzük, hogy a karakter számjegy-e
                        if (Character.isDigit(currentChar)) {
                            int index = currentChar - '0';
                            if (index >= 0 && index < jatekosok.size()) {
                                GombaTest g = new GombaTest(map[i][j], (Gombasz) jatekosok.get(index));
                                ((Gombasz) jatekosok.get(index)).add(g);
                            } else {
                                System.err.println("Érvénytelen index: " + index + " a " + i + "." + j + " pozícióban.");
                            }
                        } else {
                            System.err.println("Érvénytelen karakter a sorban: " + currentChar + " a " + i + "." + j + " pozícióban.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Nem sikerült betölteni a gombákat: " + e.getMessage());
        }
    }    

    /// betölti a fonalakat és hozzá rendeli őket a játékosaikhoz
    private void betoltFonalak(String path) {
        List<Jatekos> jatekosok = motor.getJatekosok();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < sor; i++) {
                String line = reader.readLine();
                if (line == null || line.length() < oszlop) {
                    System.err.println("Hibás sor a fájlban a " + i + ". sorban. Elég rövid!");
                    continue; // Ha a sor túl rövid, lépj a következőre
                }
                for (int j = 0; j < oszlop; j++) {
                    char currentChar = line.charAt(j);
                    if (currentChar != '-') {
                        // Ellenőrizzük, hogy a karakter számjegy-e
                        if (Character.isDigit(currentChar)) {
                            int index = currentChar - '0';
                            if (index >= 0 && index < jatekosok.size()) {
                                Fonal f = new Fonal(map[i][j], (Gombasz) jatekosok.get(index));
                                ((Gombasz) jatekosok.get(index)).add(f);
                            } else {
                                System.err.println("Érvénytelen index: " + index + " a " + i + "." + j + " pozícióban.");
                            }
                        } else {
                            System.err.println("Érvénytelen karakter a sorban: " + currentChar + " a " + i + "." + j + " pozícióban.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Nem sikerült betölteni a fonalakat: " + e.getMessage());
        }
    }

    /// betölti a spórákat és hozzá rendeli őket a játékosaikhoz
    private void betoltSporak(String tipusPath, String kihezPath) {
        List<Jatekos> jatekosok = motor.getJatekosok();
        try (
            BufferedReader tipusReader = new BufferedReader(new FileReader(tipusPath));
            BufferedReader kihezReader = new BufferedReader(new FileReader(kihezPath));) {
            for (int i = 0; i < sor; i++) {
                String tipusSor = tipusReader.readLine();
                String kihezSor = kihezReader.readLine();
                if (tipusSor == null || kihezSor == null || tipusSor.length() < oszlop || kihezSor.length() < oszlop) {
                    System.err.println("Hibás sor a fájlban a " + i + ". sorban.");
                    continue;
                }
                for (int j = 0; j < oszlop; j++) {
                    char tipus = tipusSor.charAt(j);
                    char kihez = kihezSor.charAt(j);
                    if (tipus != '-' && kihez != '-') {
                        if (Character.isDigit(kihez)) {
                            int index = kihez - '0';
                            if (index >= 0 && index < jatekosok.size()) {
                                Spora s = switch (tipus) {
                                    case '1' -> new BenitoSpora(map[i][j],(Gombasz) jatekosok.get(index));
                                    case '2' -> new GyorsSpora(map[i][j],(Gombasz) jatekosok.get(index));
                                    case '3' -> new LassitoSpora(map[i][j],(Gombasz) jatekosok.get(index));
                                    case '4' -> new OsztodoRovarSpora(map[i][j],(Gombasz) jatekosok.get(index));
                                    default -> null;
                                };
                                if (s != null) {
                                    ((Gombasz) jatekosok.get(index)).add(s);
                                }
                            } else {
                                System.err.println("Érvénytelen kihez index: " + index + " a " + i + "." + j + " pozícióban.");
                            }
                        } else {
                            System.err.println("Érvénytelen kihez karakter a sorban: " + kihez + " a " + i + "." + j + " pozícióban.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Nem sikerült betölteni a spórákat: " + e.getMessage());
        }
    }
    

    /// betölti a rovarakat és hozzá rendeli őket a játékosaikhoz
    private void betoltRovarok(String path) {
        List<Jatekos> jatekosok = motor.getJatekosok();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < sor; i++) {
                String line = reader.readLine();
                if (line == null || line.length() < oszlop) {
                    System.err.println("Hibás sor a fájlban a " + i + ". sorban. Elég rövid!");
                    continue;
                }
                for (int j = 0; j < oszlop; j++) {
                    char currentChar = line.charAt(j);
                    if (currentChar != '-') {
                        // Ellenőrizzük, hogy a karakter számjegy-e
                        if (Character.isDigit(currentChar)) {
                            int index = currentChar - '0';
                            if (index >= 0 && index < jatekosok.size()) {
                                Rovar r = new Rovar(map[i][j], (Rovarasz) jatekosok.get(index));
                                ((Rovarasz) jatekosok.get(index)).hozzaAd(r);
                            } else {
                                System.err.println("Érvénytelen index: " + index + " a " + i + "." + j + " pozícióban.");
                            }
                        } else {
                            System.err.println("Érvénytelen karakter a sorban: " + currentChar + " a " + i + "." + j + " pozícióban.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Nem sikerült betölteni a rovarokat: " + e.getMessage());
        }
    }    

    /// beállítja a pályát egy másik pálya alapján
    public void setMap(Grid[][] g) {
        map = g;
    }

    /// beállítja a tektonok listáját egy másik lista alapján
    public void setTektons(List<Tekton> t) {
        tektons = t;
    }

    /// egy lépés regisztrál és lép meg
    public void makeMove(int startCoordinate, int startCoordinate1, int endCoordinate, int endCoordinate1, Move move) {
        motor.kovetkezoLepes(map[startCoordinate][startCoordinate1], map[endCoordinate][endCoordinate1], move);
    }

    /// megadja az adott helyen lévő gridet
    public Grid getGrid(int i, int i1) {
        return map[i][i1];
    }

    /// hozzáad egy új játékost
    public void addJatekos(Jatekos j) {
        motor.jatekosHozzaAd(j);
    }
}