
package terkep;

import java.util.Random;

public class Maze {
    char[][] test;
    boolean[][] szigetekKeret;
    int x;
    int y;
    int vizszam = 0;
    Random rand = new Random();

    public Maze(int x, int y) {
        this.x = x;
        this.y = y;
        test = new char[x][y];
        szigetekKeret = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                test[i][j] = ' ';
                szigetekKeret[i][j] = false;
            }
        }
        while(vizszam <= (x*y/3)){
            this.generateMaze(rand.nextInt(x),rand.nextInt(y));
        }
    }

    public void print() {
        for (int i = 0; i <= this.y + 1; i++) {
            System.out.print("0");
        }
        System.out.println();
        for (int i = 0; i < this.x; i++) {
            System.out.print("0");
            for (int j = 0; j < this.y; j++) {
                System.out.print(test[i][j]);
            }
            System.out.print("0");
            System.out.println();
        }
        for (int i = 0; i <= this.y + 1; i++) {
            System.out.print("0");
        }
        System.out.println();
    }

    public void generateMaze(int x, int y) {
        if(szigetekKeret[x][y]){
            return;
        }
        if (x < 0 || y < 0 || x >= this.x || y >= this.y || test[x][y] == '#') {
            return; // Ha már fal van, vagy a határokon kívül vagyunk, lépjünk ki
        }
        int merre = -1;
        int elozo = -1;
        if (fluidPlace(x, y)) {
            test[x][y] = '#';
            szigetekKeret[x][y] = true;
            this.vizszam++;
        }else{
            return;
        }
        for (int i = 0; i < 3; i++) {
            do{
                merre = rand.nextInt(4);
            }while(merre == elozo);
            elozo = merre;
            if (x - 1 >= 0 && y - 1 >= 0 && x + 1 < this.x && y + 1 < this.y) {
                irany(merre ,x ,y);
            }
        }
    }

    void irany(int merre, int x, int y){
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
    private boolean fluidPlace(int x, int y) {
        if (x > 0 && y > 0) {
            if (test[x - 1][y - 1] == '#' && test[x - 1][y - 1] == test[x - 1][y]
                    && test[x - 1][y - 1] == test[x][y - 1]) {
                return false;
            }
        }
        if (x < this.x - 1 && y > 0) {
            if (test[x + 1][y - 1] == '#' && test[x + 1][y - 1] == test[x + 1][y]
                    && test[x + 1][y - 1] == test[x][y - 1]) {
                return false;
            }
        }
        if (x > 0 && y < this.y - 1) {
            if (test[x - 1][y + 1] == '#' && test[x - 1][y + 1] == test[x - 1][y]
                    && test[x - 1][y + 1] == test[x][y + 1]) {
                return false;
            }
        }
        if (x < this.x - 1 && y < this.y - 1) {
            if (test[x + 1][y + 1] == '#' && test[x + 1][y + 1] == test[x + 1][y]
                    && test[x + 1][y + 1] == test[x][y + 1]) {
                return false;
            }
        }
        return true;
    }
    

}
