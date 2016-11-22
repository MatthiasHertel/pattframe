package sample;

import java.util.Vector;

public class Spiel implements ISpiel{

  private final Integer SpielerAnzahl = 12;

  private static Spiel instance = new Spiel();


  private Vector<ISpieler>  allSpieler = new Vector<>();

  private Spiel() {
  }

  public static Spiel getInstance() {
      return instance;
  }

  public void addSpieler(ISpieler spieler) {
      if(allSpieler.size() < SpielerAnzahl){
          this.allSpieler.add(spieler);
      }
  }

  @Override
  public void startSpiel() {

      for (ISpieler spieler: allSpieler) {
            spieler.spiele();
      }
  }


  @Override
  public int getSpielerAnzahl() {
      return SpielerAnzahl;
  }

  public void clear(){
      this.allSpieler = new Vector<>();
  }


  @Override
  public Vector<ISpieler> getAllSpieler(){
      return this.allSpieler;
  }

}