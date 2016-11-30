package sample;

import javafx.beans.property.StringProperty;

// Interface Spieler
// methode fuer das starten des spieles
// methode fuer das ermitteln der spielfigur
// property zum ermitteln der wurfanzahl fuer das binding am guielement

public interface ISpieler {

    void spiele();
    ISpielFigur getSpielFigur();
    StringProperty wurfAnzahlPProperty();
}
