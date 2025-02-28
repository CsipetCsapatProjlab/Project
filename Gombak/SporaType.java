package Gombak;

import Rovarok.Rovar;

public enum SporaType {
    LASSIT(3),
    BLOKK(0),
    GYORSIT(7);

    private final int lepesErtek;

    SporaType(int l) {
        lepesErtek = l;
    }

    public void alkalmaz(Rovar rovar) {
        rovar.setLepesek(lepesErtek);
    }
}
