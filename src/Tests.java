import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Fungorium;
import model.JatekMotor;
import model.Tekton;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;
import model.players.Gombasz;


public class Tests {
    public static Map<String, Object> ObjectMap = new HashMap<>();
    JatekMotor motor;
    Grid[][] grid;
    MyLogger ml = new MyLogger();
    Tekton[] tektons;
    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Teszt: Jatek mentese");
        System.out.println("2. Teszt: Jatek betoltese");
        System.out.println("3. Teszt: Uj jatek kezdese");
        System.out.println("4. Teszt: Karaktervalasztas");
        System.out.println("5. Teszt: Gombatest novesztes");
        System.out.println("6. Teszt: Fonal novesztes");
        System.out.println("7. Teszt: Spora loves");
        System.out.println("8. Teszt: Test fejlesztes");
        System.out.println("9. Teszt: Rovar mozgatas");
        System.out.println("10. Teszt: Fonal vagas");
        System.out.println("11. Teszt: Spora eves");
        System.out.println("0: exit");
        System.out.println("Adja meg a kivalasztott tesztet (A teszt sorszámának beutesevel):\n");
        int key = scanner.nextInt();
        switch(key){
            case 1:
                test1();
                break;
            case 2:
                test2();
                break;
            case 3:
                test3();
                break;
            case 4:
                test4();
                break;
            case 5:
                test5();
                break;
            case 6:
                test6();
                break;
            case 7:
                test7();
                break;
            case 8:
                test8();
                break;
            case 9:
                test9();
                break;
            case 10:
                test10();
                break;
            case 11:
                test11();
                break;
            case 0:
                scanner.close();
                return;
            default:
                System.out.println("Nincsen ilyen teszt, kerlek valassz ujra a tesztek kozul!");
                start();
        }
    }

    /**
     * @return 2 Tektonból álló 2db 4*4-es sziget egy 4* lávával elválasztva vertikálisan, amit láva vesz körbe.
     */
    public Fungorium genPalya() {
        tektons = new Tekton[2];
        tektons[0] = new Tekton(null);
        tektons[1] = new Tekton(null);
        ObjectMap.put("tekton0", tektons[0]);
        ObjectMap.put("tekton1", tektons[1]);
        grid = new Grid[11][6];
        for (int i = 0; i < 11; i++) {
            grid[i] = new Grid[6];
        }

        for (int x = 0; x < 11; x++) {
            grid[x][0] = new Lava();
            grid[x][5] = new Lava();
            ObjectMap.put("lava" + x + "," + "0", grid[x][0]);
            ObjectMap.put("lava" + x + "," + "5", grid[x][5]);
        }

        for (int y = 0; y < 6; y++) {
            grid[0][y] = new Lava();
            grid[10][y] = new Lava();
            grid[5][y] = new Lava();
            ObjectMap.put("lava" + "0" + "," + y, grid[0][y]);
            ObjectMap.put("lava" + "10" + "," + y, grid[10][y]);
            ObjectMap.put("lava" + "5" + "," + y, grid[5][y]);
        }

        int dx = 1, dy = 1;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                grid[x + dx][y + dy] = new TektonElem(tektons[0]);
                ObjectMap.put("tektonelem" + (x + dx) + "," + (x + dy), grid[x + dx][y + dy]);
            }
        }

        dx = 6;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                grid[x + dx][y + dy] = new TektonElem(tektons[1]);
                ObjectMap.put("tektonelem" + (x + dx) + "," + (x + dy), grid[x + dx][y + dy]);
            }
        }
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 6; y++) {
                Grid[] neighbours = new Grid[4];
                try {
                    neighbours[0] = grid[x - 1][y];
                } catch (Exception e) {
                }

                try {
                    neighbours[1] = grid[x][y - 1];
                } catch (Exception e) {
                }

                try {
                    neighbours[2] = grid[x + 1][y];
                } catch (Exception e) {
                }

                try {
                    neighbours[3] = grid[x][y + 1];
                } catch (Exception e) {
                }

                grid[x][y].setNeighbours(neighbours);
            }
        }

        Fungorium fn = new Fungorium();
        ObjectMap.put("fungorium0", fn);
        fn.setMap(grid);
        fn.setTektons(Arrays.stream(tektons).toList());
        return fn;
    }

    public void newsetup() {
        Fungorium fungorium = genPalya();
        motor = fungorium.getMotor();/*
        motor.jatekosValasztas(grid);
        motor.jatekosValasztas(grid);*/
    }
    void test1(){
        ml.Visit("Mentes");
        ml.Visit("JatekMotor.mentes()");
        motor.mentes();
        ml.In("Mentes sikeres", false);
        ml.Return("JatekMotor.mentes()");
        start();
    }
    void test2(){
        ml.Visit("Betoltes");
        ml.Visit("JatekMotor.betoltes()");
        motor.betoltes();
        ml.In("Betoltes siekres", false);
        ml.Return("JatekMotor.betoltes()");
        start();
    }
    void test3(){
        ml.Visit("Start");
        ml.Visit("JatekMotor.start()");
        motor.start();
        ml.In("Jatek inditas sikeres", false);
        ml.Return("JatekMotor.start()");
        start();
    }
    void test4(){
        ml.Visit("Jatekos valasztas");
        ml.Visit("JatekMotor.jatekosValasztas()");
        motor.jatekosValasztas(grid);
        if(motor.jelenlegiJatekos() != null) ml.In("Jatekos valasztas sikeres", false);
        ml.Return("JatekMotor.jatekosValasztas()");
        start();
    }
    void test5(){
        Gombasz gombasz = new Gombasz(grid[1][1], null);
        Fonal f = new Fonal(grid[1][1], gombasz, null, null);
        ml.Visit("GombaTest novesztes!");
        ml.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        ml.Visit("Fonal.fonalNovesztes(Grid)");
        if(ml.In("Van ut a kezdo gridhez? [y/n]", true) == "y") {
            if(ml.In("Van eleg spora? [y/n]", true) == "y") {
                f.fonalNovesztes(grid[1][2]);
                if(grid[1][2].getGameObject().isEmpty()) ml.In("Fonal novesztes sikertelen tektonra!", false);
                else ml.In("Fonal novesztes sikeres tektonra!", false);
            }else ml.In("Fonal novesztes sikertelen tektonra!", false);
        }else ml.In("Fonal novesztes sikertelen tektonra!", false);
        ml.Return("Fonal.fonalNovesztes(Gird)");
        ml.Visit("Fonal.gombaTestNovesztes(Grid)");
        if(ml.In("Van eleg spora a tektonon? [y/n]", true) == "y"){
            if(ml.In("A tektonra lehet noveszteni testet (nincsen mas gombatest vagy olyan tipusu a tekton) [y/n]", true) == "y"){
                f.gombaTestNovesztes(grid[1][2]);
                if(grid[1][2].getGameObject().isEmpty()) ml.In("Gombates novesztes sikertelen!", false);
                else ml.In("Gombatest novesztes sikeres!", false);
            }else ml.In("Gombates novesztes sikertelen!", false);
        }else ml.In("Gombates novesztes sikertelen!", false);
        ml.Return("Fonal.gombaTestNovesztes(Grid)");
        grid[1][2].clear();
        start();
    }
    void test6(){
        Gombasz gombasz = new Gombasz(grid[1][1], null);
        ml.Visit("Fonal novesztes!");
        ml.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        ml.Visit("Fonal.fonalNovesztes(Grid)");
        if(ml.In("Van ut a kezdo gridhez? [y/n]", true) == "y") {
            Fonal f = new Fonal(grid[1][1], gombasz, null, null);
            if(ml.In("Van eleg spora? [y/n]", true) == "y") {
                f.fonalNovesztes(grid[1][2]);
                if(grid[1][2].getGameObject().isEmpty()) ml.In("Fonal novesztes sikertelen tektonra!", false);
                else ml.In("Fonal novesztes sikeres tektonra!", false);
            }else ml.In("Fonal novesztes sikertelen tektonra!", false);
        }else ml.In("Fonal novesztes sikertelen tektonra!", false);
        ml.Return("Fonal.fonalNovesztes(Gird)");
        grid[1][2].clear();
        ml.Visit("Fonal.fonalNovesztes(Grid)");
        if(ml.In("Van ut a kezdo gridhez? [y/n]", true) == "y") {
            Fonal f2 = new Fonal(grid[1][1], gombasz, null, null);
            if(ml.In("Van eleg spora? [y/n]", true) == "y") {
                f2.fonalNovesztes(grid[0][2]);
                if(grid[0][2].getGameObject().isEmpty()) ml.In("Fonal novesztes sikertelen lavara!", false);
                else ml.In("Fonal novesztes sikeres lavara!", false);
            } else ml.In("Fonal novesztes sikertelen lavara!", false);
        } else ml.In("Fonal novesztes sikertelen lavara!", false);
        ml.Return("Fonal.fonalNovesztes(Gird)");
        grid[0][2].clear();
        start();
    }
    void test7(){
        Gombasz gombasz = new Gombasz(grid[1][1], null);
        GombaTest gt = new GombaTest(grid[1][1], gombasz);
        Spora s1 = new Spora(grid[1][1]);
        ml.Visit("Spora loves");
        ml.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        ml.Visit("gombaTest.sporaKilo(Grid, Spora)");
        if(ml.In("Van eleg spora a kiindulo gombatestben? [y/n]", true) == "y"){
            if(ml.In("Szomszedos a kijelolt tekton? [y/n]", true) == "y"){
                gt.sporaKilo(grid[1][2], s1);
                if(grid[1][2].getGameObject().isEmpty()) ml.In("Spora loves sikertelen!", false);
                else ml.In("Spora loves sikeres!", false);
            }else ml.In("Spora loves sikertelen!", false);
        }else ml.In("Spora loves sikertelen!", false);
        ml.Return("gombaTest.sporaKilo(Grid, Spora)");
        grid[1][2].clear();
        GombaTest gt2 = new GombaTest(grid[1][2], gombasz);
        Spora s2 = new Spora(grid[1][2]);
        ml.Visit("Spora loves elutasítas");
        ml.Visit("gombaTest.sporaKilo(Grid, Spora)");
        if(ml.In("Van eleg spora a kiindulo gombatestben? [y/n]", true) == "y"){
            if(ml.In("Szomszedos a kijelolt tekton? [y/n]", true) == "y"){
                gt2.sporaKilo(grid[3][3], s2);
                if(grid[3][3].getGameObject().isEmpty()) ml.In("Spora loves elutasitas sikeres!", false);
                else ml.In("Spora loves elutasitas sikertelen!", false);
            }else ml.In("Spora loves elutasitas sikertelen!", false);
        }else ml.In("Spora loves elutasitas sikertelen!", false);
        ml.Return("gombaTest.sporaKilo(Grid, Spora)");
        grid[1][2].clear();
        grid[3][3].clear();
        start();
    }
    void test8(){
        Gombasz gombasz = new Gombasz(grid[1][1], null);
        GombaTest gt = new GombaTest(grid[1][1], gombasz);
        ml.Visit("Gombatest fejlesztes");
        ml.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        ml.Visit("gombaTest.setFejlesztett()");
        if(ml.In("Van eleg spora a fejleszteshez? [y/n]", true) == "Y"){
            gt.setFejlesztett();
            if(gt.getFejlesztett()) ml.In("Gombatest fejlesztes sikeres!", false);
            else ml.In("Gombatest fejlesztes sikertelen!", true);
        }else ml.In("Gombatest fejlesztes sikertelen!", true);
        ml.Return("gombaTest.setFejlesztett()");
        start();
    }
    void test9(){
        Rovar rovar = new Rovar(grid[2][1]);
        ml.Visit("Rovar mozgas");
        ml.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        ml.Visit("Rovar.move(Grid)");
        if(ml.In("Van odavezeto ut? [y/n]", true) == "y"){
            rovar.move(grid[2][2]);
            if(grid[2][2].getGameObject().isEmpty()) ml.In("Rovar mozgas sikertelen!", false);
            else ml.In("Rovar mozgas sikeres!", false);
        }else ml.In("Rovar mozgas sikertelen!", false);
        ml.Return("Rovar.move(Grid)");
        grid[2][2].clear();
        start();
    }
    void test10(){
        Rovar rovar = new Rovar(grid[2][1]);
        ml.Visit("Rovar fonal eves");
        ml.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        ml.Visit("Rovar.move(Grid)");
        if(ml.In("Van odavezeto ut? [y/n]", true) == "y"){
            rovar.move(grid[2][2]);
            if(grid[2][2].getGameObject().isEmpty()) ml.In("Rovar mozgas sikertelen!", false);
            else ml.In("Rovar mozgas sikeres!", false);
        }else ml.In("Rovar mozgas sikertelen!", false);
        ml.Return("Rovar.move(Grid)");
        Fonal f = new Fonal(grid[2][2], null, null, null);
        ml.Visit("Rovar.consume()");
        if(ml.In("Van ott fonal? [y/n]", true) == "y"){
            rovar.consume();
            if(grid[2][2].getGameObject().size() == 1) ml.In("Rovar fonal eves sikeres!", false);
            else ml.In("Rovar fonal eves sikertelen!", false);
        }else ml.In("Rovar fonal eves sikertelen!", false);
        ml.Return("Rovar.consume()");
        grid[2][2].clear();
        start();
    }
    void test11(){
        Rovar rovar = new Rovar(grid[2][1]);
        ml.Visit("Rovar spora eves");
        ml.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        ml.Visit("Rovar.move(Grid)");
        if(ml.In("Van odavezeto ut? [y/n]", true) == "y"){
            rovar.move(grid[2][2]);
            if(grid[2][2].getGameObject().isEmpty()) ml.In("Rovar mozgas sikertelen!", false);
            else ml.In("Rovar mozgas sikeres!", false);
        }else ml.In("Rovar mozgas sikertelen!", false);
        ml.Return("Rovar.move(Grid)");
        Spora s = new Spora(grid[2][2]);
        ml.Visit("Rovar.consume()");
        if(ml.In("Van ott spora? [y/n]", true) == "y"){
            rovar.consume();
            if(grid[2][2].getGameObject().size() == 1) ml.In("Rovar spora eves sikeres!", false);
            else ml.In("Rovar spora eves sikertelen!", false);
        }else ml.In("Rovar spora eves sikertelen!", false);
        ml.Return("Rovar.consume()");
        grid[2][2].clear();
        start();
    }
}