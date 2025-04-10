import model.Fungorium;

public class Main {
    public static void main(String[] args) {
        Fungorium f = new Fungorium(10,10);
        System.out.println(f);
        System.out.println(f.getSzigetSzam());
        f.ujKor();
        System.out.println(f.getSzigetSzam());
    }
}