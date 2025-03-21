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
