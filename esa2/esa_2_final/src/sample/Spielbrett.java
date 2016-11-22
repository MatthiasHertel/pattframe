package sample;

import java.util.Vector;

// Klasse Spielbrett
// verwaltet alle spielbrettrelevanten Elemente (Kugel und Löcher)

public class Spielbrett {
    private Vector<Loch> myLoch = new Vector<Loch>();
    private Kugel myKugel;

    // Jedes Instanz der Klasse Spielbrett  beinhaltet eine feste Anzahl Löcher mit einem fest bestimmten Punktwert
    // Wenn eine Kugel in kein Loch fällt wird das über den Punktwert = 0 ausgedrückt.

    public Spielbrett(int lochAnzahlPunktwert1, int lochAnzahlPunktwert2, int lochAnzahlPunktwert3, int lochAnzahlPunktwert0) {
        this.myKugel = new Kugel(this);

        for (int i = 0; i < lochAnzahlPunktwert1; i++) {
            myLoch.add(new Loch(1));
        }
        for (int i = 0; i < lochAnzahlPunktwert2; i++) {
            myLoch.add(new Loch(2));
        }
        for (int i = 0; i < lochAnzahlPunktwert3; i++) {
            myLoch.add(new Loch(3));
        }
        for (int i = 0; i < lochAnzahlPunktwert0; i++) {
            myLoch.add(new Loch(0));
        }
    }

    public int getPunkt() {
        for (Loch loch : myLoch) {
            if (loch.getIstGetroffen()) {
                return loch.getPunktwert();
            }
        }
        return 0;
    }


    public void clear() {
        for (Loch loch : myLoch) {
            loch.setIstGetroffen(false);
        }
    }

    public Kugel getKugel() {
        return this.myKugel;
    }

    public Vector getLochFeld() {
        return myLoch;
    }
}