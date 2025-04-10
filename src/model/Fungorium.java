package model;

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
    JatekMotor motor;
    Grid[][] map;
    boolean[][] szigetekKeret;
    char[][] test;
    List<Tekton> tektons;
    int sor;
    int oszlop;
    int lavaszam = 0;
    Random rand = new Random();
    int szigetekSzama = 0;
    
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

    void findSziget(){
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
        switch (t.getHatas()) {
            case    GOMBATESTEVO:
                map[x][y] = new GombatestEvo(t);
                t.addelem((TektonElem)map[x][y]);
                break;
            case    FONALTARTO:
                map[x][y] = new FonalTarto(t);
                t.addelem((TektonElem)map[x][y]);
                break;
            case    FONALEVO:
                map[x][y] = new FonalEvo(t);
                t.addelem((TektonElem)map[x][y]);
                break;
            case    EGYFONAL:
                map[x][y] = new EgyFonal(t);
                t.addelem((TektonElem)map[x][y]);
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
                }
                map[i][j].setNeighbours(szomszedok);
            }
        }
    }

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

    public void ujKor() {
        // TODO
    }
    public void setMap(Grid[][] g){map = g;}
    public void setTektons(List<Tekton> t){tektons = t;}
    
}
