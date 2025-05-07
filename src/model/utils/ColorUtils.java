package model.utils;
import java.awt.Color;

public class ColorUtils {

    /**
     * Color objektum konvertálása #RRGGBB formátumú Stringgé
     * @param color - a konvertálandó Color objektum
     * @return hexadecimális színkód (pl. "#FF00A2")
     */
    public static String colorToHex(Color color) {
        if (color == null) {
            return "#000000"; // Alapértelmezett fekete szín null esetén
        }

        // RGB komponensek kinyerése (0-255)
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        // Formázás két hexa számjeggyel minden komponenshez
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    /**
     * Hexadecimális String konvertálása Color objektummá
     * @param hex - a hexadecimális színkód (pl. "#FF00A2" vagy "FF00A2")
     * @return Color objektum
     * @throws IllegalArgumentException ha érvénytelen a formátum
     */
    public static Color hexToColor(String hex) {
        if (hex == null || hex.trim().isEmpty()) {
            throw new IllegalArgumentException("Üres hexadecimális színkód");
        }

        // Eltávolítjuk a # jelet ha van
        String hexValue = hex.startsWith("#") ? hex.substring(1) : hex;

        // Ellenőrizzük a hosszt
        if (hexValue.length() != 6) {
            throw new IllegalArgumentException("Hexadecimális színkódnak 6 karakter hosszúnak kell lennie");
        }

        try {
            // Színkomponensek kinyerése
            int red = Integer.parseInt(hexValue.substring(0, 2), 16);
            int green = Integer.parseInt(hexValue.substring(2, 4), 16);
            int blue = Integer.parseInt(hexValue.substring(4, 6), 16);

            return new Color(red, green, blue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Érvénytelen hexadecimális színkód", e);
        }
    }
}