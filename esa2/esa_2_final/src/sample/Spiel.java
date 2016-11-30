package sample;

import java.util.Vector;

// Klasse Spiel
// als konkretes Kentucky Derby Spiel (Singleton Pattern)
// verwaltet alle spielrelevaten Elemente


public class Spiel implements ISpiel{

  private final Integer SpielerAnzahl = 12;
  private Vector<ISpieler>  allSpieler = new Vector<>();

  // Singleton Pattern

  private static Spiel instance = new Spiel();

  private Spiel() {
  }

  public static Spiel getInstance() {
      return instance;
  }

  // implementiert methoden aus dem interface

  @Override
  public void addSpieler(ISpieler spieler) {
      if(allSpieler.size() < SpielerAnzahl){
          this.allSpieler.add(spieler);
      }
  }

  // implementiert methoden aus dem interface

  @Override
  public void startSpiel() {

      for (ISpieler spieler: allSpieler) {
            spieler.spiele();
      }
  }

  // implementiert methoden aus dem interface

  @Override
  public int getSpielerAnzahl() {
      return SpielerAnzahl;
  }

  // implementiert methoden aus dem interface
  @Override
  public void clear(){
      this.allSpieler = new Vector<>();
  }

  // implementiert methoden aus dem interface
  @Override
  public Vector<ISpieler> getAllSpieler(){
      return this.allSpieler;
  }

}