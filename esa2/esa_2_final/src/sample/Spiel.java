package sample;

import java.util.Vector;

/*
 */
public class Spiel {

  private final Integer maxSpieler = 12;

  private static Spiel instance = new Spiel();

    /**
   * 
   * @element-type Spieler
   */
  private Vector<Spieler>  mySpieler = new Vector<Spieler>();

  private Spiel() {
  }

  public static Spiel getInstance() {

    return instance;
  }

  public void addSpieler(Spieler spieler) {
      this.mySpieler.add(spieler);
  }

  public void startSpiel() {
//      ((Spieler)mySpieler.elementAt(0)).spiele();
//      mySpieler.elementAt(0).spiele();

      for (Spieler spieler: mySpieler) {
//          spieler.spiele();
          ((Thread)spieler).start();
//          Thread thread = new Thread(spieler);
//          thread.start();
      }
  }

  public void setMaxSpieler(int anzahl) {
  }

  public int getMaxSpieler() {

      return maxSpieler;
  }

  public void clear(){
      this.mySpieler = new Vector<Spieler>();
  }

}