package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class Spieler implements ISpieler{

    private StringProperty wurfAnzahlP;
    private Spielbrett mySpielbrett;
//    private Pferd myPferd;
    private ISpielFigur myPferd;
    private int wurfAnzahl = 0;


    public class SpieleService extends Service {
        @Override
        protected Task createTask() {
            return new Task() {

                @Override
                protected Void call() {
                    while (myPferd.getPosition() < 27) {

                        mySpielbrett.getKugel().rolle();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }

                        updateValue(myPferd.getPosition() + mySpielbrett.getPunkt());
                        updateMessage(Integer.toString(wurfAnzahl++));
//                        updateMessage(Integer.toString(Integer.parseInt(getWurfAnzahlProperty()) +1));

                        mySpielbrett.clear();
                    }
                    return null;
                }
            };
        }
    }


    @Override
    public final StringProperty wurfAnzahlPProperty() {
        if (wurfAnzahlP == null) {
            wurfAnzahlP = new SimpleStringProperty("");
        }
        return wurfAnzahlP;
    }


    public Spieler() {
        this.mySpielbrett = new Spielbrett(7, 7, 2, 10);
        this.myPferd = new Pferd();
    }

    public void spiele() {
        SpieleService spieleService = new SpieleService();
        myPferd.positionProperty().bind(spieleService.valueProperty());
        wurfAnzahlPProperty().bind(spieleService.messageProperty());
        spieleService.start();
    }


//    public Pferd getPferd() {
//        return this.myPferd;
//    }

    @Override
    public ISpielFigur getSpielFigur() {
        return this.myPferd;
    }

}