package model.utils;

import java.util.Optional;
import java.util.Random;

public class Constants {
   public static final double rovarMozgasEnergia= Optional.of(Double.parseDouble(System.getenv("ROVAR_MOZGAS_ENERGIA"))).orElse(5.0);
   public static final double fonalNovesztesEnergia= Optional.of(Double.parseDouble(System.getenv("FONAL_NOVESZTES_ENERGIA"))).orElse(10.0);
   public static final int gombaTestNovesztesEnergia = Optional.of(Integer.parseInt(System.getenv("GOMBATEST_NOVESZTES_ENERGIA"))).orElse(5);
   public static final int sporaTapanyag= Optional.of(Integer.parseInt(System.getenv("SPORA_TAPANYAG"))).orElse(1);
   public static final Random rnd = new Random();
   // Prevent instantiation
   private Constants() {}
}
