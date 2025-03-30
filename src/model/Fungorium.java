package model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;

public class Fungorium {
    JatekMotor motor;
    Grid[][] map;
    List<Tekton> tektons;
    int sor;
    int oszlop;

    
    public Fungorium(int ujsor,int ujoszlop){ //A pályát létrehozó konstruktor meghív minden fügvényt ami ahoz kell hogy a pálya létrejöjjön
        sor = ujsor;
        oszlop = ujoszlop;
        map = new Grid[sor][oszlop];
    }
    public void print() {
        System.out.println("Bent");
        for (int i = 0; i <= this.oszlop + 1; i++) {
            System.out.print("0");
        }
        System.out.println();
        for (int i = 0; i < this.sor; i++) {
            System.out.print("0");
            for (int j = 0; j < this.oszlop; j++) {
                //System.out.print(map[i][j]);
                System.out.print(" ");
            }
            System.out.print("0");
            System.out.println();
        }
        for (int i = 0; i <= this.oszlop + 1; i++) {
            System.out.print("0");
        }
        System.out.println();
    }

    public JatekMotor getMotor() {
        return motor;
    }
    public Fungorium(){
        motor = new JatekMotor();
    }

    public void ujKor() {
        // TODO
    }
    public void setMap(Grid[][] g){map = g;}
    public void setTektons(List<Tekton> t){tektons = t;}
    
}
