package sample;

import java.util.Random;

public class Kugel {

    private Spielbrett mySpielbrett;

    public Kugel(Spielbrett spielbrett) {
        this.mySpielbrett = spielbrett;
    }

    public void rolle() {

        Random randomGenerator = new Random();
        int randomLochNummer = randomGenerator.nextInt(mySpielbrett.getLochFeld().size());

        ((Loch) mySpielbrett.getLochFeld().get(randomLochNummer)).setIstGetroffen(true);
//      mySpielbrett.getLochFeld().get(randomInt).setIstGetroffen(true);
    }

}