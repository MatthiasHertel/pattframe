package sample;

import java.util.Vector;

// Interface Spiel
// methode fuer hinzufuegen der Spieler
// methode fuer das Starten des Spiels
// methode fuer das ermitteln der spieleranzahl
// reset der Spieler

public interface ISpiel {

    void addSpieler(ISpieler spieler);
    void startSpiel();
    int getSpielerAnzahl();
    void clear();
    Vector<ISpieler> getAllSpieler();
}
