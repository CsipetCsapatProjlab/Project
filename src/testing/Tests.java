package testing;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.JatekMotor;
import model.Tekton;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Rovarasz;


public class Tests {
    public static Map<String, Object> ObjectMap = new HashMap<>();
    JatekMotor motor;
    Grid[][] grid;
    Tekton[] tektons;
    static String y ="y";
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
    /*
    public Fungorium genPalya() {
        tektons = new Tekton[2];
        tektons[0] = new Tekton((List<TektonElem>)null);
        tektons[1] = new Tekton((List<TektonElem>)null);
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
    */


    public void newsetup() {
    }
    void test1(){
        MyLogger.Visit("Mentes");
        MyLogger.Visit("JatekMotor.mentes()");
        motor.mentes("mentes");
        MyLogger.In("Mentes sikeres", false);
        MyLogger.Return("JatekMotor.mentes()");
        MyLogger.Return("");
        start();
    }
    void test2(){
        MyLogger.Visit("Betoltes");
        MyLogger.Visit("JatekMotor.betoltes()");
        motor.betoltes();
        MyLogger.In("Betoltes siekres", false);
        MyLogger.Return("JatekMotor.betoltes()");
        MyLogger.Return("");
        start();
    }
    void test3(){
        MyLogger.Visit("Start");
        MyLogger.Visit("JatekMotor.start()");
        motor.start();
        MyLogger.In("Jatek inditas sikeres", false);
        MyLogger.Return("JatekMotor.start()");
        MyLogger.Return("");
        start();
    }
    void test4(){
        MyLogger.Visit("Jatekos valasztas");
        MyLogger.Visit("JatekMotor.jatekosValasztas()");
        //motor.jatekosValasztas(grid);
        if(motor.jelenlegiJatekos() != null) MyLogger.In("Jatekos valasztas sikeres", false);
        MyLogger.Return("JatekMotor.jatekosValasztas()");
        MyLogger.Return("");
        start();
    }
    void test5(){
        Gombasz gombasz = new Gombasz((TektonElem) grid[1][1], "");
        Fonal f = new Fonal(grid[1][1], gombasz);
        MyLogger.Visit("GombaTest novesztes!");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Fonal.fonalNovesztes(Grid)");
        if(MyLogger.In("Van ut a kezdo gridhez? [y/n]", true).equals(y)) {
            if(MyLogger.In("Van eleg spora? [y/n]", true).equals(y)) {
                try{f.fonalNovesztes(grid[1][2]);}
                catch (Exception e){}
                if(grid[1][2].getGameObject().isEmpty()) MyLogger.In("Fonal novesztes sikertelen tektonra!", false);
                else MyLogger.In("Fonal novesztes sikeres tektonra!", false);
            }else MyLogger.In("Fonal novesztes sikertelen tektonra!", false);
        }else MyLogger.In("Fonal novesztes sikertelen tektonra!", false);
        MyLogger.Return("Fonal.fonalNovesztes(Gird)");
        MyLogger.Visit("Fonal.gombaTestNovesztes(Grid)");
        if(MyLogger.In("Van eleg spora a tektonon? [y/n]", true).equals(y)){
            if(MyLogger.In("A tektonra lehet noveszteni testet (nincsen mas gombatest vagy olyan tipusu a tekton) [y/n]", true).equals(y)){
                f.gombaTestNovesztes(grid[1][2]);
                if(grid[1][2].getGameObject().isEmpty()) MyLogger.In("Gombates novesztes sikertelen!", false);
                else MyLogger.In("Gombatest novesztes sikeres!", false);
            }else MyLogger.In("Gombates novesztes sikertelen!", false);
        }else MyLogger.In("Gombates novesztes sikertelen!", false);
        MyLogger.Return("Fonal.gombaTestNovesztes(Grid)");
        MyLogger.Return("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Return("");
        grid[1][2].clear();
        start();
    }
    void test6(){
        Gombasz gombasz = new Gombasz((TektonElem) grid[1][1], "");
        MyLogger.Visit("Fonal novesztes!");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Fonal.fonalNovesztes(Grid)");
        if(MyLogger.In("Van ut a kezdo gridhez? [y/n]", true).equals(y)) {
            Fonal f = new Fonal(grid[1][1], gombasz);
            if(MyLogger.In("Van eleg spora? [y/n]", true).equals(y)) {
                try{f.fonalNovesztes(grid[1][2]);}
                catch (Exception e){}
                if(grid[1][2].getGameObject().isEmpty()) MyLogger.In("Fonal novesztes sikertelen tektonra!", false);
                else MyLogger.In("Fonal novesztes sikeres tektonra!", false);
            }else MyLogger.In("Fonal novesztes sikertelen tektonra!", false);
        }else MyLogger.In("Fonal novesztes sikertelen tektonra!", false);
        MyLogger.Return("Fonal.fonalNovesztes(Gird)");
        grid[1][2].clear();
        MyLogger.Visit("Fonal.fonalNovesztes(Grid)");
        if(MyLogger.In("Van ut a kezdo gridhez? [y/n]", true).equals(y)) {
            Fonal f2 = new Fonal(grid[1][1], gombasz);
            if(MyLogger.In("Van eleg spora? [y/n]", true).equals(y)) {
                try{f2.fonalNovesztes(grid[0][2]);}
                catch (Exception e){}
                if(grid[0][2].getGameObject().isEmpty()) MyLogger.In("Fonal novesztes sikertelen lavara!", false);
                else MyLogger.In("Fonal novesztes sikeres lavara!", false);
            } else MyLogger.In("Fonal novesztes sikertelen lavara!", false);
        } else MyLogger.In("Fonal novesztes sikertelen lavara!", false);
        MyLogger.Return("Fonal.fonalNovesztes(Gird)");
        MyLogger.Return("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Return("");
        grid[0][2].clear();
        start();
    }
    void test7(){
        Gombasz gombasz = new Gombasz((TektonElem) grid[1][1], "");
        GombaTest gt = new GombaTest(grid[1][1], gombasz);
        Spora s1 = new Spora(grid[1][1], gombasz);
        MyLogger.Visit("Spora loves");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("gombaTest.sporaKilo(Grid, Spora)");
        if(MyLogger.In("Van eleg spora a kiindulo gombatestben? [y/n]", true).equals(y)){
            if(MyLogger.In("Szomszedos a kijelolt tekton? [y/n]", true).equals(y)){
                //gt.sporaKilo(grid[1][2], s1);
                if(grid[1][2].getGameObject().isEmpty()) MyLogger.In("Spora loves sikertelen!", false);
                else MyLogger.In("Spora loves sikeres!", false);
            }else MyLogger.In("Spora loves sikertelen!", false);
        }else MyLogger.In("Spora loves sikertelen!", false);
        MyLogger.Return("gombaTest.sporaKilo(Grid, Spora)");
        MyLogger.Return("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Return("");
        grid[1][2].clear();
        start();
    }
    void test8(){
        Gombasz gombasz = new Gombasz((TektonElem) grid[1][1], "");
        GombaTest gt = new GombaTest(grid[1][1], gombasz);
        MyLogger.Visit("Gombatest fejlesztes");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("gombaTest.setFejlesztett()");
        if(MyLogger.In("Van eleg spora a fejleszteshez? [y/n]", true).equals(y)){
            gt.setFejlesztett();
            if(gt.getFejlesztett()) MyLogger.In("Gombatest fejlesztes sikeres!", false);
            else MyLogger.In("Gombatest fejlesztes sikertelen!", true);
        }else MyLogger.In("Gombatest fejlesztes sikertelen!", true);
        MyLogger.Return("gombaTest.setFejlesztett()");
        MyLogger.Return("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Return("");
        start();
    }
    void test9(){
        Rovarasz rv=new Rovarasz((TektonElem) grid[2][1],"Helo");
        MyLogger.Visit("Rovar mozgas");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Rovar.move(Grid)");
        if(MyLogger.In("Van odavezeto ut? [y/n]", true).equals(y)){
            //rv.lepes(grid[2][1],grid[2][2], Move.Rovar_mozog);
            if(grid[2][2].getGameObject().isEmpty()) MyLogger.In("Rovar mozgas sikertelen!", false);
            else MyLogger.In("Rovar mozgas sikeres!", false);
        }else MyLogger.In("Rovar mozgas sikertelen!", false);
        MyLogger.Return("Rovar.move(Grid)");
        MyLogger.Return("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Return("");
        grid[2][2].clear();
        start();
    }
    void test10(){
        Rovarasz rv=new Rovarasz((TektonElem) grid[2][1],"Helo");
        MyLogger.Visit("Rovar fonal eves");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Rovar.move(Grid)");
        if(MyLogger.In("Van odavezeto ut? [y/n]", true).equals(y)){
            //rv.lepes(grid[2][1],grid[2][2],Move.Rovar_mozog);
            if(grid[2][2].getGameObject().isEmpty()) MyLogger.In("Rovar mozgas sikertelen!", false);
            else MyLogger.In("Rovar mozgas sikeres!", false);
        }else MyLogger.In("Rovar mozgas sikertelen!", false);
        MyLogger.Return("Rovar.move(Grid)");
        MyLogger.Visit("Rovar.consume()");
        if(MyLogger.In("Van ott fonal? [y/n]", true).equals(y)){
            //rv.lepes(grid[2][2],grid[2][2],Move.Rovar_vag);
            if(grid[2][2].getGameObject().size() == 1) MyLogger.In("Rovar fonal eves sikeres!", false);
            else MyLogger.In("Rovar fonal eves sikertelen!", false);
        }else MyLogger.In("Rovar fonal eves sikertelen!", false);
        MyLogger.Return("Rovar.consume()");
        MyLogger.Return("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Return("");
        grid[2][2].clear();
        start();
    }
    void test11(){
        Rovarasz rv=new Rovarasz((TektonElem) grid[2][1],"Helo");
        MyLogger.Visit("Rovar spora eves");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Rovar.move(Grid)");
        if(MyLogger.In("Van odavezeto ut? [y/n]", true).equals(y)){
            //rv.lepes(grid[2][1],grid[2][2],Move.Rovar_mozog);
            if(grid[2][2].getGameObject().isEmpty()) MyLogger.In("Rovar mozgas sikertelen!", false);
            else MyLogger.In("Rovar mozgas sikeres!", false);
        }else MyLogger.In("Rovar mozgas sikertelen!", false);
        MyLogger.Return("Rovar.move(Grid)");
        MyLogger.Visit("Rovar.consume()");
        if(MyLogger.In("Van ott spora? [y/n]", true).equals(y)){
            //rv.lepes(grid[2][2],grid[2][2],Move.Rovar_eszik);
            if(grid[2][2].getGameObject().size() == 1) MyLogger.In("Rovar spora eves sikeres!", false);
            else MyLogger.In("Rovar spora eves sikertelen!", false);
        }else MyLogger.In("Rovar spora eves sikertelen!", false);
        MyLogger.Return("Rovar.consume()");
        MyLogger.Return("JatekMotor.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Return("");
        grid[2][2].clear();
        start();
    }
}