package sample;

import java.util.Random;

// Kugel Klasse
// simuliert das rollen der kugel auf dem spielbrett und ermittelt das getroffene loch (random)


public class Kugel {

    private Spielbrett mySpielbrett;

    public Kugel(Spielbrett spielbrett) {
        this.mySpielbrett = spielbrett;
    }

    public void rolle() {

        Random randomGenerator = new Random();
        int randomLochNummer = randomGenerator.nextInt(mySpielbrett.getLochFeld().size());

        ((Loch) mySpielbrett.getLochFeld().get(randomLochNummer)).setIstGetroffen(true);

    }

}