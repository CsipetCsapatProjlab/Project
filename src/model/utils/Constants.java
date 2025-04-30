package model.utils;

import java.util.Optional;
import java.util.Random;

public class Constants {
   public static int gombaTestSporaLovesRes;
   public static int gombaTestSporaMax;
   public static double gombaTestSporaGenPerKor;
   public static double rovarMozgasEnergia;
   public static double fonalNovesztesEnergia;
   public static int gombaTestNovesztesEnergia;
   public static int sporaTapanyag;
   public static final Random rnd = new Random();

   public static void loadConstants(){
      gombaTestSporaLovesRes = Integer.parseInt(Optional.ofNullable(System.getenv("GOMBATEST_SPORA_LOVES_RES")).orElse("5"));
      gombaTestSporaMax = Integer.parseInt(Optional.ofNullable(System.getenv("GOMBATEST_SPORA_MAX")).orElse("10"));
      gombaTestSporaGenPerKor = Double.parseDouble(Optional.ofNullable(System.getenv("GOMBATEST_SPORA_GEN")).orElse("0.5"));
      rovarMozgasEnergia = Double.parseDouble(Optional.ofNullable(System.getenv("ROVAR_MOZGAS_ENERGIA")).orElse("5.0"));
      fonalNovesztesEnergia = Double.parseDouble(Optional.ofNullable(System.getenv("FONAL_NOVESZTES_ENERGIA")).orElse("10.0"));
      gombaTestNovesztesEnergia = Integer.parseInt(Optional.ofNullable(System.getenv("GOMBATEST_NOVESZTES_ENERGIA")).orElse("5"));
      sporaTapanyag= Integer.parseInt(Optional.ofNullable(System.getenv("SPORA_TAPANYAG")).orElse("1"));
   }
   // Prevent instantiation
   private Constants() {}
}
