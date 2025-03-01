package Tektonok.Tektonok;

import java.util.List;
import java.util.Random;

import Gombak.Gombak.Gombasz;
import Rovarok.Rovarok.Rovarasz;

public class Fungorium
{
    public List<Tektonelem> terkepkep;
    public boolean[][] szigetekKeret;
    public int x;
    public int y;
    public int vizszam = 0;
    public int szigetekSzama = 1;
    public Random rand = new Random();
    public List<Gombasz> gombaszok;
    public List<Rovarasz> rovaraszok;
    public Fungorium() { x = 0; }
    public void print() { y = 0; }
    public void generateMaze(int x, int y)
    {
        x = 0; y = 2;
    }
    public void irany(int merre, int x, int y)
    {
        merre = 2;
    }
    public boolean fluidPlace(int x, int y)
    {
        return true;
    }
    public void findSziget()
    {
        x = 2;
    }
}