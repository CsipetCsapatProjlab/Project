import java.util.*;

import interfaces.IGombasz;
import model.Fungorium;
import model.JatekMotor;
import model.Tekton;
import model.enums.Hatas;
import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;
import model.grid.Grid;
import model.grid.Lava;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Rovarasz;


public class Tests {
    private JatekMotor jm;
    private IGombasz ig;
    private Gombasz g;
    private Rovarasz r;
    private Tekton t1;
    private Tekton t2;
    private Tekton t3;
    private Tekton t4;
    private ArrayList<Tekton> t = new ArrayList<Tekton>();
    private TektonElem te1;
    private TektonElem te2;
    private TektonElem te3;
    private TektonElem te4;
    private Lava l;
    private Spora s1;
    private Spora s2;
    private Spora s3;
    private Spora s4;
    private Spora s5;
    private GombaTest gt;
    private Fonal f;
    private Rovar rovar;
    /*
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

     */
    public static Map<String, Object> ObjectMap = new HashMap<>();

    /**
     * @return 2 Tektonból álló 2db 4*4-es sziget egy 4* lávával elválasztva vertikálisan, amit láva vesz körbe.
     */
    public static Fungorium genPalya() {
        Tekton[] tektons = new Tekton[2];
        tektons[0] = new Tekton(null);
        tektons[1] = new Tekton(null);
        ObjectMap.put("tekton0", tektons[0]);
        ObjectMap.put("tekton1", tektons[1]);
        Grid[][] grid = new Grid[11][6];
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

    public static void newsetup() {
        Fungorium fungorium = genPalya();
        JatekMotor motor = fungorium.getMotor();
        motor.getJatekosValasztas((Grid) ObjectMap.get("tektonelem2,2"));
        motor.getJatekosValasztas((Grid) ObjectMap.get("tektonelem7,2"));
    }
}

/*
    void setup(){

        t1 = new Tekton(null);
        t2 = new Tekton(null);
        t3 = new Tekton(null);
        t4 = new Tekton(Hatas.NO_GOMBATEST);
        t.add(t1);
        t.add(t2);
        t.add(t3);
        te1 = new TektonElem(t1);
        te2 = new TektonElem(t2);
        te3 = new TektonElem(t1);
        te4 = new TektonElem(t4);
        l = new Lava();
        s1 = new Spora(te1);
        s2 = new Spora(te1);
        s3 = new Spora(te1);
        s4 = new Spora(te1);
        s5 = new Spora(te1);
        List<Spora> st = new ArrayList<>();
        st.add(s1);
        st.add(s2);
        st.add(s3);
        st.add(s4);
        gt = new GombaTest(te1, null, null);
        List<GombaTest> gtt = new ArrayList<>();
        gtt.add(gt);
        f = new Fonal(te1, null, null, null);
        List<Fonal> ft = new ArrayList<>();
        ft.add(f);
        g = new Gombasz(st, ft, gtt);
        rovar = new Rovar(te2);
        r = new Rovarasz(rovar);
        List<Gombasz> gat = new ArrayList<>();
        gat.add(g);
        List<Rovarasz> rt = new ArrayList<>();
        rt.add(r);
        jm = new JatekMotor(rt, gat);
    }
    void test1(){
        jm.mentes();
        start();
    }
    void test2(){
        jm.betoltes();
        start();
    }
    void test3(){
        jm.start();
        start();
    }
    void test4(){
        jm.valasztas(true);
        jm.valasztas(false);
        start();
    }
    void test5(){
        f.fonalNovesztes(te3);
        if(te3.getGameObject().isEmpty()) System.out.println("Fonal novesztes sikertelen tektonra!");
        else System.out.println("Fonal novesztes sikeres tektonra!");
        f.gombaTestNovesztes();
        if(te3.getGameObject().isEmpty()) System.out.println("Gombatest novesztes sikertelen!");
        else System.out.println("Gombatest novesztes sikeres!");
        Fonal f2 = new Fonal(te4, null, null, null);
        f2.gombaTestNovesztes();
        f2.remove();
        if(te4.getGameObject().isEmpty()) System.out.println("Gombatest novesztes elutasitas sikeres!");
        else System.out.println("Gombatest novesztes elutasitas sikertelen!");
        te3.clear();
        te4.clear();
        start();
    }
    void test6(){
        f.fonalNovesztes(te3);
        if(te3.getGameObject().isEmpty()) System.out.println("Fonal novesztes sikertelen tektonra!");
        else System.out.println("Fonal novesztes sikeres tektonra!");
        te3.clear();
        f.fonalNovesztes(l);
        if(l.getGameObject().isEmpty()) System.out.println("Fonal novesztes sikertelen lavara!");
        else System.out.println("Fonal novesztes sikeres lavara!");
        l.clear();
        start();
    }
    void test7(){
        gt.sporaKilo(te3, s1);
        if(te3.getGameObject().isEmpty()) System.out.println("Spora loves sikertelen!");
        else System.out.println("Spora loves sikeres!");
        te3.clear();
        GombaTest gt2 = new GombaTest(te3, ig, null);
        gt2.sporaKilo(te1, s5);
        if(te1.getGameObject().isEmpty()) System.out.println("Spora loves elutasitas sikeres!");
        else System.out.println("Spora loves elutasitas sikertelen!");
        te3.clear();
        te1.clear();
        start();
    }
    void test8(){
        gt.setFejlesztett();
        if(gt.getFejlesztett()) System.out.println("Gombatest fejlesztes sikeres!");
        else System.out.println("Gombatest fejlesztes sikertelen!");
        start();
    }
    void test9(){
        rovar.move(te3);
        if(te3.getGameObject().isEmpty()) System.out.println("Rovar mozgas sikertelen!");
        else System.out.println("Rovar mozgas sikeres!");
        te3.clear();
        start();
    }
    void test10(){
        rovar.move(te3);
        f.fonalNovesztes(te3);
        rovar.consume();
        if(te3.getGameObject().size() == 1) System.out.println("Rovar fonal eves sikeres!");
        else System.out.println("Rovar fonal eves sikertelen!");
        te3.clear();
        start();
    }
    void test11(){
        rovar.move(te3);
        gt.sporaKilo(te3, s1);
        rovar.consume();
        if(te3.getGameObject().size() == 1) System.out.println("Rovar spora eves sikeres!");
        else System.out.println("Rovar spora eves sikertelen!");
        te3.clear();
        start();
    }
}
*/