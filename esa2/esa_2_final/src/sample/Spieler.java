package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class Spieler extends Thread{

    private int wurfAnzahl = 0;
    private Spielbrett mySpielbrett;
    private Pferd myPferd;
//    private PferdService myPferdService;
    private int spielerId;
    private Controller viewController;
    private Spieler thisSpieler;


    public class MyService extends Service {
        @Override
        protected Task createTask() {
            return new Task() {

                @Override
                protected Void call() {
                    while(myPferd.getPosition() < 27){
                        System.out.println("Schleife in task... Pferdeposition: " + myPferd.getPosition());
                        mySpielbrett.getKugel().rolle();
//                        myPferd.setzeZug(mySpielbrett.getPunkt());
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }

                        System.out.println( "Task: spielbrettpunkt: " +  mySpielbrett.getPunkt());
                        updateValue(myPferd.getPosition() + mySpielbrett.getPunkt());
                    }

                    return null;
                }
            };
        }

    }


    @Override
    public void run(){spiele();}

    public void spielen() {
        MyService myService = new MyService();
        myPferd.positionProperty().bind(myService.valueProperty());
        myService.start();


//        spiele();
    }


  public Spieler(Controller viewController, int id) {
      this.mySpielbrett = new Spielbrett();
      this.myPferd = new Pferd();
//      this.myPferdService = new PferdService();
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


//      Platform.runLater(new Runnable() {
//          @Override
//          public void run() {
//              myPferd.setzeZug(mySpielbrett.getPunkt());
//          }
//      });


      myPferd.setzeZug(mySpielbrett.getPunkt());



//      System.out.println("MyPferd  positiosnProperty: " + myPferd.positionProperty().getValue());
      System.out.println("MyPferd  positiosnProperty: " + myPferd.positionProperty().getValue());


//      viewController.updatePferd(this);

//      synchronized (viewController) {
//          viewController.updatePferd(this);
//      }



//      Platform.runLater(new Runnable() {
//          @Override
//          public void run() {
//              viewController.updateView(thisSpieler);
//          }
//      });
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