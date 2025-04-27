package model.utils;

import java.util.Optional;
import java.util.Random;

public class Constants {
   public static final int gombaTestSporaLovesRes = Optional.of(Integer.parseInt(System.getenv("GOMBATEST_SPORA_LOVES_RES"))).orElse(5);
   public static final int gombaTestSporaMax = Optional.of(Integer.parseInt(System.getenv("GOMBATEST_SPORA_MAX"))).orElse(10);
   public static final double gombaTestSporaGenPerKor = Optional.of(Double.parseDouble(System.getenv("GOMBATEST_SPORA_GEN"))).orElse(0.5);
   public static final double rovarMozgasEnergia = Optional.of(Double.parseDouble(System.getenv("ROVAR_MOZGAS_ENERGIA"))).orElse(5.0);
   public static final double fonalNovesztesEnergia = Optional.of(Double.parseDouble(System.getenv("FONAL_NOVESZTES_ENERGIA"))).orElse(10.0);
   public static final int gombaTestNovesztesEnergia = Optional.of(Integer.parseInt(System.getenv("GOMBATEST_NOVESZTES_ENERGIA"))).orElse(5);
   public static final int sporaTapanyag= Optional.of(Integer.parseInt(System.getenv("SPORA_TAPANYAG"))).orElse(1);
   public static final Random rnd = new Random();
   // Prevent instantiation
   private Constants() {}
}
