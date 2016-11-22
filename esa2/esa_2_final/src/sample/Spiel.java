package sample;

import java.util.Vector;

public class Spiel {

  private final Integer maxSpieler = 12;

  private static Spiel instance = new Spiel();


  private Vector<Spieler>  mySpieler = new Vector<Spieler>();

  private Spiel() {
  }

  public static Spiel getInstance() {
      return instance;
  }

  public void addSpieler(Spieler spieler) {
      if(mySpieler.size() < maxSpieler){
          this.mySpieler.add(spieler);
      }
  }

  public void startSpiel() {

      for (Spieler spieler: mySpieler) {
            spieler.spiele();
      }
  }


  public int getMaxSpieler() {
      return maxSpieler;
  }

  public void clear(){
      this.mySpieler = new Vector<Spieler>();
  }


  public Vector<Spieler> getMySpieler(){
      return this.mySpieler;
  }

}