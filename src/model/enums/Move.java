package model.enums;

/**
 * Mozgasi formak
 */
public enum Move{
    Kezdo_lepes("Kezdő lépés"),
    Fonal_noveszt("Fonál Növesztés"),
    Gombatest_noveszt("Gombatest növesztés"),
    Gombatest_fejleszt("Gombatest fejlesztés"),
    Fonal_fogyaszt("Fonalal rovar megevése"),
    Spora_lo ("Spóra lövés"),
    Rovar_vag ("Rovar vágás"),
    Rovar_eszik ("Rovar evés"),
    Rovar_mozog ("Rovar mozogás");

    private final String niceName;

    Move(String niceName) {
        this.niceName = niceName;
    }

    @Override
    public String toString() {
        return niceName;
    }
}
