package testing;

import java.io.PrintStream;
import java.util.Scanner;

import model.JatekMotor;
import model.Tekton;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.grid.Grid;
import model.grid.TektonElem;
import model.players.Gombasz;

public class Tests {
    JatekMotor motor;
    Grid[][] grid;
    Tekton[] tektons;
    static String y ="y";
    private PrintStream out = System.out;
    public void start(){
        Scanner scanner = new Scanner(System.in);
        out.println("1. Teszt: Jatek mentese");
        out.println("2. Teszt: Jatek betoltese");
        out.println("3. Teszt: Uj jatek kezdese");
        out.println("4. Teszt: Karaktervalasztas");
        out.println("5. Teszt: Gombatest novesztes");
        out.println("6. Teszt: Fonal novesztes");
        out.println("7. Teszt: Spora loves");
        out.println("8. Teszt: Test fejlesztes");
        out.println("9. Teszt: Rovar mozgatas");
        out.println("10. Teszt: Fonal vagas");
        out.println("11. Teszt: Spora eves");
        out.println("0: exit");
        out.println("Adja meg a kivalasztott tesztet (A teszt sorszámának beutesevel):\n");
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
                out.println("Nincsen ilyen teszt, kerlek valassz ujra a tesztek kozul!");
                start();
        }
        scanner.close();
    }

    /**
     * @return 2 Tektonból álló 2db 4*4-es sziget egy 4* lávával elválasztva vertikálisan, amit láva vesz körbe.
     */
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
        MyLogger.Visit("Spora loves");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("gombaTest.sporaKilo(Grid, Spora)");
        if(MyLogger.In("Van eleg spora a kiindulo gombatestben? [y/n]", true).equals(y)){
            if(MyLogger.In("Szomszedos a kijelolt tekton? [y/n]", true).equals(y)){
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
        MyLogger.Visit("Rovar mozgas");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Rovar.move(Grid)");
        if(MyLogger.In("Van odavezeto ut? [y/n]", true).equals(y)){
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
        MyLogger.Visit("Rovar fonal eves");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Rovar.move(Grid)");
        if(MyLogger.In("Van odavezeto ut? [y/n]", true).equals(y)){
            if(grid[2][2].getGameObject().isEmpty()) MyLogger.In("Rovar mozgas sikertelen!", false);
            else MyLogger.In("Rovar mozgas sikeres!", false);
        }else MyLogger.In("Rovar mozgas sikertelen!", false);
        MyLogger.Return("Rovar.move(Grid)");
        MyLogger.Visit("Rovar.consume()");
        if(MyLogger.In("Van ott fonal? [y/n]", true).equals(y)){
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
        MyLogger.Visit("Rovar spora eves");
        MyLogger.Visit("JatekMoror.jelenlegiJatekos().lepes(Grid, Grid, Move)");
        MyLogger.Visit("Rovar.move(Grid)");
        if(MyLogger.In("Van odavezeto ut? [y/n]", true).equals(y)){
            if(grid[2][2].getGameObject().isEmpty()) MyLogger.In("Rovar mozgas sikertelen!", false);
            else MyLogger.In("Rovar mozgas sikeres!", false);
        }else MyLogger.In("Rovar mozgas sikertelen!", false);
        MyLogger.Return("Rovar.move(Grid)");
        MyLogger.Visit("Rovar.consume()");
        if(MyLogger.In("Van ott spora? [y/n]", true).equals(y)){
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