package sample;

import javafx.application.Platform;

public class Spieler extends Thread{

    private int wurfAnzahl = 0;
    private Spielbrett mySpielbrett;
    private Pferd myPferd;
    private int spielerId;
    private Controller viewController;
    private Spieler thisSpieler;

    @Override
    public  void run(){
        spiele();
    }

  public Spieler(Controller viewController, int id) {
      this.mySpielbrett = new Spielbrett();
      this.myPferd = new Pferd();
      this.spielerId = id;
      this.viewController = viewController;

      thisSpieler = this;
  }

  public void spiele() {
      while(this.myPferd.getPosition() < 27){
          spieleEineRunde();
          mySpielbrett.clear();
          this.wurfAnzahl += 1;
          try {
              Thread.sleep(200);
          } catch (InterruptedException e) {
              e.printStackTrace();
              Thread.currentThread().interrupt();
          }
      }

//      viewController.updateLabel(this);

//      synchronized (viewController) {
//          viewController.updateLabel(this);
//      }


//      Platform.runLater(new Runnable() {
//          @Override
//          public void run() {
//              viewController.updateLabelWurf(thisSpieler);
//          }
//      });

  }

  private void spieleEineRunde() {
      mySpielbrett.getKugel().rolle();
      System.out.println("Punktwert nach Wurf vom Spielbrett: " + mySpielbrett.getPunkt());
      myPferd.setzeZug(mySpielbrett.getPunkt());
//      viewController.updatePferd(this);

//      synchronized (viewController) {
//          viewController.updatePferd(this);
//      }



      Platform.runLater(new Runnable() {
          @Override
          public void run() {
              viewController.updateView(thisSpieler);;
          }
      });
  }

  public int getWurfAnzahl() {
      return this.wurfAnzahl;
  }

  public int getSpielerId(){
      return this.spielerId;
  }

    public Pferd getPferd() {
        return this.myPferd;
    }

}