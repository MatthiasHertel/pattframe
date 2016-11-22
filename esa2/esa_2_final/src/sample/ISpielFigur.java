package sample;

import javafx.beans.property.IntegerProperty;

// Interface Spielfigur
// methode fuer das ermitteln der SPielfigurposition
// methode fuer das setzen der Spielfigur
// property zum ermitteln der Position fuer das binding am guielement

public interface ISpielFigur {

    int getPosition();
    void setPosition(int value);
    IntegerProperty positionProperty();

}
