import java.util.Scanner;

public class MyLogger{
    private static int depth = 0;

    /**
     * Fv.-be valo belepeskor hivando
     * @param s0 A fv. fejlece - Pl.: "bab.isBab(tal, szin)"
     */
    public static void Visit(String s0){
        for (int i = 0; i < depth; ++i){
            System.out.printf("%10s", " ");
        }
        System.out.printf("--> %s%n", s0);
        depth++;
    }

    /**
     * Fv.-bol valo visszatereskor hivando
     * @param s0 A fv. [helye].[neve]: [visszerteke] - Pl.: "bab.isBab: true"
     */
    public static void Return(String s0){
        depth--;
        for (int i = 0; i < depth; ++i){
            System.out.printf("%10s", " ");
        }
        System.out.printf("<-- %s%n", s0);
    }

    /**
     * A Visit-elt fv.-ben felmerulo plusz kiirasok
     * @param s0 A kiiras - Pl.: "bab1 created", "Van bab? [y/n]"
     * @param vanKerdes Ha a kiiras kerdes - "true"
     * @return A felhasznalo valasza - Pl.: "y"
     */
    public static String In(String s0, boolean vanKerdes){
        String input = "";
        if (vanKerdes){
            for (int i = 1; i < depth; ++i){
                System.out.printf("%10s", " ");
            }

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.printf("%4s ", s0);
                input = scanner.nextLine();
            }
        }
        else{
            for (int i = 0; i < depth; ++i){
                System.out.printf("%10s", " ");
            }
            System.out.printf("%4s%n", s0);
        }
        return input;
    }
}