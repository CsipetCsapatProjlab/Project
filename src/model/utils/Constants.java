package model.utils;

import java.util.Optional;
import java.util.Random;

public class Constants {
   public static int gombaTestSporaLovesRes = Integer.parseInt(Optional.ofNullable(System.getenv("GOMBATEST_SPORA_LOVES_RES")).orElse("5"));
   public static int gombaTestSporaMax = Integer.parseInt(Optional.ofNullable(System.getenv("GOMBATEST_SPORA_MAX")).orElse("10"));
   public static double gombaTestSporaGenPerKor = Double.parseDouble(Optional.ofNullable(System.getenv("GOMBATEST_SPORA_GEN")).orElse("0.5"));
   public static double rovarMozgasEnergia = Double.parseDouble(Optional.ofNullable(System.getenv("ROVAR_MOZGAS_ENERGIA")).orElse("5.0"));
   public static double fonalNovesztesEnergia = Double.parseDouble(Optional.ofNullable(System.getenv("FONAL_NOVESZTES_ENERGIA")).orElse("10.0"));
   public static int gombaTestNovesztesEnergia = Integer.parseInt(Optional.ofNullable(System.getenv("GOMBATEST_NOVESZTES_ENERGIA")).orElse("5"));
   public static int sporaTapanyag = Integer.parseInt(Optional.ofNullable(System.getenv("SPORA_TAPANYAG")).orElse("1"));
   public static final Random rnd = new Random();
   // Prevent instantiation
   private Constants() {}
}
