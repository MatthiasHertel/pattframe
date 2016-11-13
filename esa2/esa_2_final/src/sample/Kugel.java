package sample;

import java.util.Vector;
import java.util.Random;

public class Kugel {


    private Spielbrett mySpielbrett;
  
    public Kugel(Spielbrett spielbrett) {
        this.mySpielbrett = spielbrett;
  }

  public void rolle() {
      System.out.println("Kugel rollt");

      Random randomGenerator = new Random();
      int randomInt = randomGenerator.nextInt(mySpielbrett.getLochFeld().size());
//      (mySpielbrett.getLochFeld()).elementaAt(randomInt).getPunktWert();

      ((Loch)mySpielbrett.getLochFeld().get(randomInt)).setIstGetroffen(true);

//      System.out.println("MyLoch random: " + randomInt + " Punktwert:  "  + ((Loch)mySpielbrett.getLochFeld().get(randomInt)).getPunktwert());

  }

}