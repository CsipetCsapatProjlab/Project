package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public int szigetekSzama = 0;
    
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
        for(int i = 0; i < this.sor; i++){
            for(int j = 0 ; j < this.oszlop ;j++){
                if(!szigetekKeret[i][j]){
                    Tekton t = new Tekton(null);
                    connectSziget(i,j, t);
                    szigetekSzama++;
                    tektons.add(t);
                }
            }
        }
    }

    void connectSziget(int x, int y, Tekton t){
        if(szigetekKeret[x][y]){ //ellenőrzi hogy voltunk-e már azon a mezőn
            return;
        }
        if (x < 0 || y < 0 || x >= this.sor || y >= this.oszlop || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        szigetekKeret[x][y] = true;
        map[x][y] = new TektonElem(t);
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
        System.out.println("reset");
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
    
    public JatekMotor getMotor() {
        return motor;
    }

    public void ujKor() {
        // TODO
    }
    public void setMap(Grid[][] g){map = g;}
    public void setTektons(List<Tekton> t){tektons = t;}
    
}
