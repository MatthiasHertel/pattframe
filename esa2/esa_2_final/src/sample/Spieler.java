package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class Spieler {

    private StringProperty wurfAnzahlProperty;
    private Spielbrett mySpielbrett;
    private Pferd myPferd;
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


    public final StringProperty wurfAnzahlPropertyProperty() {
        if (wurfAnzahlProperty == null) {
            wurfAnzahlProperty = new SimpleStringProperty("");
        }
        return wurfAnzahlProperty;
    }


    public Spieler() {
        this.mySpielbrett = new Spielbrett(7, 7, 2, 10);
        this.myPferd = new Pferd();
    }

    public void spiele() {
        SpieleService spieleService = new SpieleService();
        myPferd.positionProperty().bind(spieleService.valueProperty());
        wurfAnzahlPropertyProperty().bind(spieleService.messageProperty());
        spieleService.start();
    }


    public Pferd getPferd() {
        return this.myPferd;
    }

}