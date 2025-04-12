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
import model.enums.TektonelemTypes;
import model.grid.EgyFonal;
import model.grid.FonalEvo;
import model.grid.FonalTarto;
import model.grid.GombatestEvo;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

public class Fungorium {
    JatekMotor motor;// A játék motorja 
    Grid[][] map; //A pálya amin a játék játszódij
    boolean[][] szigetekKeret; // Segéd tömb ami a genrálást segíti
    char[][] test;  // Segéd tömb ami a genrálást segíti és 
    List<Tekton> tektons; //Tektonok listálya
    int sor; //A pálya sorainak a száma
    int oszlop; //A pálya oszlopainak a száma
    int lavaszam = 0; //Azt tartja számon hány láva van a pályán
    Random rand = new Random();
    int szigetekSzama = 0;// Tektonok száma
    
    public Fungorium(int ujsor,int ujoszlop){ //A pályát létrehozó konstruktor meghív minden fügvényt ami ahoz kell hogy a pálya létrejöjjön
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
        boolean sikeres = false;
        //Itt generálódik a pálya először a lávák majd megkeresi a tektonokat és ha a végeradmény nem felel meg újra kezdi
        while(!sikeres){
            while(lavaszam <= (sor*oszlop/3)){
                this.generateMaze(rand.nextInt(sor),rand.nextInt(oszlop)); 
            }
            findSziget();
            if(szigetekSzama > 3){
                sikeres = true;
            }else{
                reset();
            }
        }
        this.parosit();
        this.findszomszed();
    }
    //Stringé allakítja a pályát
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

    void generateMaze(int x, int y){ // legenerálja a pályát
        if(szigetekKeret[x][y]){ //ellenőrzi hogy voltunk-e már azon a mezőn
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
        }else{
            return;
        }
        //Random kiválasztja merre akar menni és azt hogy hányszor próbálkozik
        for (int i = 0; i < 2; i++) {
            do{
                merre = rand.nextInt(4);
            }while(merre == elozo);
            elozo = merre;
            if (x - 1 >= 0 && y - 1 >= 0 && x + 1 < this.sor && y + 1 < this.oszlop) {
                irany(merre ,x ,y);
            }
        }
    }
    //Egy ellenőrző hogy ne alakulhasson ki 2x2 es láva rész
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
    //A generálásnál a generálás irányába folytatja a rekurziót 
    private void irany(int merre, int x, int y){
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
    //Megtaláljuk a tektonokat és hozzájuk tesszük a tekton elemeket
    void findSziget(){
        szigetekSzama = 0;
        //Beállítjuk a tömböt a keresés segítéséhez
        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                if(test[i][j] == '#'){
                    szigetekKeret[i][j] = true;
                }else{
                    szigetekKeret[i][j] = false;
                }
            }
        }
        for(int i = 0; i < this.sor; i++){
            for(int j = 0 ; j < this.oszlop ;j++){
                if(!szigetekKeret[i][j]){
                    TektonelemTypes[] hatasok = TektonelemTypes.values();
                    Tekton t = new Tekton(hatasok[rand.nextInt(hatasok.length)]);
                    connectSziget(i,j, t);
                    szigetekSzama++;
                    tektons.add(t);
                }
            }
        }
    }

    void connectSziget(int x, int y, Tekton t){//Összeköti a tektonelemeket a tektonokhoz
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        if(szigetekKeret[x][y]){ //ellenőrzi hogy voltunk-e már azon a mezőn
            return;
        }
        szigetekKeret[x][y] = true;
        //Megfelelő tektoneleme létrehozása
        switch (t.getHatas()) {
            case    GOMBATESTEVO:
                map[x][y] = new GombatestEvo(t);
                t.addelem((TektonElem)map[x][y]);
                test[x][y] = '1';
                break;
            case    FONALTARTO:
                map[x][y] = new FonalTarto(t);
                t.addelem((TektonElem)map[x][y]);
                test[x][y] = '2';
                break;
            case    FONALEVO:
                map[x][y] = new FonalEvo(t);
                t.addelem((TektonElem)map[x][y]);
                test[x][y] = '3';
                break;
            case    EGYFONAL:
                map[x][y] = new EgyFonal(t);
                t.addelem((TektonElem)map[x][y]);
                test[x][y] = '4';
                break;
        
            default:
                System.err.println("Nem kezelt tektonfalyta");
                break;
        }
        if (x - 1 >= 0) {
            connectSziget(x-1, y,t);
        }
        if (x + 1 < this.sor) {
            connectSziget(x+1, y,t);
        }
        if (y - 1 >= 0) {
            connectSziget(x, y-1,t);
        }
        if (y + 1 < this.oszlop) {
            connectSziget(x, y+1,t);
        }
    }
    //reseteli a pályát egy rossz generálás esetén
    void reset(){
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
    private void parosit(){ //Összepárosítja a szomszédos grideket
        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                Grid[] szomszedok = new Grid[4];
                int hany = 0; 
                if(i - 1 > 0){ //fel
                    szomszedok[hany] = map[i-1][j];
                    hany++;
                }
                if(i + 1 < sor){ //le
                    szomszedok[hany] = map[i+1][j];
                    hany++;
                }
                if(j + 1 < oszlop){ //jobb
                    szomszedok[hany] = map[i][j+1];
                    hany++;
                }
                if(j - 1 > 0){ //ball
                    szomszedok[hany] = map[i][j-1];
                    hany++;
                }
                map[i][j].setNeighbours(szomszedok, hany);
            }
        }
    }
    //Tekton szomszéd keresés része
    void findszomszed(){
        for(int i = 0; i < this.sor; i++){
            for(int j = 0 ; j < this.oszlop ;j++){
                if(!szigetekKeret[i][j]){
                    tektonSzomszedKeres(i,j);
                }
            }
        }
    }
    
    public void tektonSzomszedKeres(int x,int y){//megkeresi a tektonok szomszédjait
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        //hozzárendeleés feltételei
        if (x - 2 >= 0 && test[x-1][y] == '#' && test[x-2][y] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x-2][y]).getTekton());
        }
        if (x + 2 < this.sor && test[x+1][y] == '#' && test[x+2][y] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x+2][y]).getTekton());
        }
        if (y - 2 >= 0 && test[x][y-1] == '#' && test[x][y-2] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x][y-2]).getTekton());
        }
        if (y + 2 < this.oszlop && test[x][y+1] == '#' && test[x][y+2] != '#') {
            ((TektonElem) map[x][y]).getTekton().addNeigbour(((TektonElem) map[x][y+2]).getTekton());
        }

        if (x - 1 >= 0) {
            tektonSzomszedKeres(x-1, y);
        }
        if (x + 1 < this.sor) {
            tektonSzomszedKeres(x+1, y);
        }
        if (y - 1 >= 0) {
            tektonSzomszedKeres(x, y-1);
        }
        if (y + 1 < this.oszlop) {
            tektonSzomszedKeres(x, y+1);
        }
    }

    public int getSzigetSzam(){
        return szigetekSzama;
    }
    public JatekMotor getMotor() {
        return motor;
    }
    //szakadás előkészítő fügvény
    public void szakad() {
        for(int i = 0; i < 10; i++){
            //kiválasztjuk a pontott amiből indulunk
            Tekton valasztott = tektons.get(rand.nextInt(tektons.size()));
            if(valasztott.getTektonszam() > 6 && valasztott.getTektonszam() > valasztott.getTektonszam()/2){
                List<TektonElem> elemek = valasztott.getTektonElems();
                TektonElem valasz = elemek.get(rand.nextInt(elemek.size()));
                int mezosor = -1;
                int mezooszlop = -1;
                for(int x = 0; x < sor; x++){
                    for(int y = 0; y < oszlop; y++){
                        if(valasz.equals(map[x][y])){
                            mezosor = x;
                            mezooszlop = y;
                        }
                    }
                }
                //Szakadunk
                if(mezooszlop != -1 && mezosor != -1 && map[mezosor][mezooszlop].getSzomszedokSzama() == 4){
                    szakadjon(mezosor, mezooszlop);
                }
                parosit();
                keresTekton();
                findszomszed();
            }
        }
    }
    //a szakadást végző fügvény
    private void szakadjon(int kezdosor, int kezdooszlop){
        if(lavaPlace(kezdosor, kezdooszlop)){
            test[kezdosor][kezdooszlop] = '#';
            map[kezdosor][kezdooszlop] = new Lava();
        }
        System.out.println(kezdosor + " " + kezdooszlop);
        if(kezdosor-1 >= 0 && szomszedLava(kezdosor, kezdooszlop) <= 2){
            if(test[kezdosor-1][kezdooszlop] != '#'){
                szakadjon(kezdosor-1, kezdooszlop);
            }
        }
        if(kezdosor+1 < this.sor && szomszedLava(kezdosor, kezdooszlop) <= 2){
            if(test[kezdosor+1][kezdooszlop] != '#'){
                szakadjon(kezdosor+1, kezdooszlop);
            }
        }
        if(kezdooszlop-1 >= 0 && szomszedLava(kezdosor, kezdooszlop) <= 2){
            if(test[kezdosor-1][kezdooszlop] != '#'){
                szakadjon(kezdosor, kezdooszlop-1);
            }
        }
        if(kezdooszlop+1 < this.oszlop && szomszedLava(kezdosor, kezdooszlop) <= 2){
            if(test[kezdosor-1][kezdooszlop] != '#'){
                szakadjon(kezdosor, kezdooszlop-1);
            }
        }
    }
    //Ellenőrizzük hány szomszéd van aki láva
    private int szomszedLava(int x,int y){
        int mennyi = 0;
        if(x-1 >= 0){
            if(test[x-1][y] == '#'){
                mennyi++;
            }
        }else{
            mennyi++;
        }
        if(x+1 < this.sor){
            if(test[x+1][y] == '#'){
                mennyi++;
            }
        }else{
            mennyi++;
        }
        if(y-1 >= 0){
            if(test[x][y-1] == '#'){
                mennyi++;
            }
        }else{
            mennyi++;
        }
        if(y+1 > this.oszlop){
            if(test[x][y+1] == '#'){
                mennyi++;
            }
        }else{
            mennyi++;
        }
        return mennyi;
    }

    //Szakadás után újra rendelni a tektonokhoz a tektonelemeket
    void keresTekton(){
        szigetekSzama = 0;
        for (int i = 0; i < sor; i++) {
            for (int j = 0; j < oszlop; j++) {
                if(test[i][j] == '#'){
                    szigetekKeret[i][j] = true;
                }else{
                    szigetekKeret[i][j] = false;
                }
            }
        }
        for(int i = 0; i < this.sor; i++){
            for(int j = 0 ; j < this.oszlop ;j++){
                if(!szigetekKeret[i][j]){
                    Tekton t = null;
                    if(test[i][j] == '1'){
                        t = new Tekton(TektonelemTypes.GOMBATESTEVO);
                    }else if(test[i][j] == '2'){
                        t = new Tekton(TektonelemTypes.FONALTARTO);
                    }else if(test[i][j] == '3'){
                        t = new Tekton(TektonelemTypes.FONALEVO);
                    }else if(test[i][j] == '4'){
                        t = new Tekton(TektonelemTypes.EGYFONAL);
                    }
                    connectSziget(i,j, t);
                    szigetekSzama++;
                    tektons.add(t);
                }
            }
        }
    }

    void szakadKeresTekton(int x, int y, Tekton t){//Összeköti a tektonelemeket a tektonokhoz
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        if(szigetekKeret[x][y]){ //ellenőrzi hogy voltunk-e már azon a mezőn
            return;
        }
        szigetekKeret[x][y] = true;
        t.addelem((TektonElem)map[x][y]);
        ((TektonElem) map[x][y]).setTekton(t);
        if (x - 1 >= 0) {
            connectSziget(x-1, y,t);
        }
        if (x + 1 < this.sor) {
            connectSziget(x+1, y,t);
        }
        if (y - 1 >= 0) {
            connectSziget(x, y-1,t);
        }
        if (y + 1 < this.oszlop) {
            connectSziget(x, y+1,t);
        }
    }

    public void ujKor() {
        szakad();
        mentes();
        System.out.println(this);
    }

    public void mentes(){
        String filePath = "mentes/tekton/palya_tektonelemek.txt";
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

        saveMapSize("mentes/tekton/valtozok.txt");
    }

    public void saveMapSize(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(sor + "," + oszlop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
    
    public Fungorium() {
        loadMapSize("mentes/tekton/valtozok.txt");
        tektons = new ArrayList<>();
        map = new Grid[sor][oszlop];
        szigetekKeret = new boolean[sor][oszlop];
        test = new char[sor][oszlop];

        String filePath = "mentes/Tekton/palya_tektonelemek.txt";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("A fájl nem található: " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null && i < sor) {
                for (int j = 0; j < Math.min(line.length(), oszlop); j++) {
                    test[i][j] = line.charAt(j);
                }
                i++;
            }

            System.out.println("Pálya betöltve: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba történt a pálya betöltésekor: " + e.getMessage());
        }

        Tekton ideiglenes = new Tekton(null);
        for(int i = 0; i < sor; i++){
            for(int j = 0; j < oszlop; j++){
                if(test[i][j] == '1'){
                    map[i][j] = new GombatestEvo(ideiglenes); 
                }else if(test[i][j] == '2'){
                    map[i][j] = new FonalTarto(ideiglenes); 
                }else if(test[i][j] == '3'){
                    map[i][j] = new FonalEvo(ideiglenes); 
                }else if(test[i][j] == '4'){
                    map[i][j] = new EgyFonal(ideiglenes); 
                }else if(test[i][j] == '#'){
                    map[i][j] = new Lava();
                }
            }
        }

        palyabetoltes();
    }

    private void palyabetoltes(){
        keresTekton();
        findszomszed();
        parosit();
    }
    public void setMap(Grid[][] g){map = g;}
    public void setTektons(List<Tekton> t){tektons = t;}
    
}