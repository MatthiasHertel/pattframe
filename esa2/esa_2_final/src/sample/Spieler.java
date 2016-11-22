package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

// Klasse Spieler
// als konkreter Spieler implementiert


public class Spieler implements ISpieler{

    private StringProperty wurfAnzahlP;
    private Spielbrett mySpielbrett;
//    private Pferd myPferd;
    private ISpielFigur myPferd;
    private int wurfAnzahl = 0;


    // Task parallelisiert die Spieleraktivität (rollt Kugel, Pferdposition und Wurfanzahl wird über Properties aktualisiert.)


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

                        mySpielbrett.clear();
                    }
                    return null;
                }
            };
        }
    }

    // implementiert methoden aus dem interface
    @Override
    public final StringProperty wurfAnzahlPProperty() {
        if (wurfAnzahlP == null) {
            wurfAnzahlP = new SimpleStringProperty("");
        }
        return wurfAnzahlP;
    }

    // Jeder Spieler bekommt ein Spielbrett mit einer festen Lochanzahl verschiedenen Punktwerten und ein Pferd zugeordnet.

    public Spieler() {
        this.mySpielbrett = new Spielbrett(7, 7, 2, 10);
        this.myPferd = new Pferd();
    }

    // Methode zum Binding der Properties und zum Aufruf des Services
    public void spiele() {
        SpieleService spieleService = new SpieleService();
        myPferd.positionProperty().bind(spieleService.valueProperty());
        wurfAnzahlPProperty().bind(spieleService.messageProperty());
        spieleService.start();
    }

    // implementiert methoden aus dem interface
    @Override
    public ISpielFigur getSpielFigur() {
        return this.myPferd;
    }

}