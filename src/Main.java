public class Main {
    public static void main(String[] args) {

        //Pelda sorozat
        /*MyLogger.Visit("asd");
        MyLogger.Visit("asd2");
        MyLogger.In("Van bab? [y/n]", true); //Valaszt var
        MyLogger.Return("asd2");
        MyLogger.In("bab2 created", false); //Nem var valaszt
        MyLogger.Visit("asd3");
        MyLogger.Return("asd3");
        MyLogger.Return("asd");*/
        Tests t = new Tests();
        t.newsetup();
        t.start();
    }
}