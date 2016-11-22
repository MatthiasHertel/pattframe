package sample;

import javafx.beans.property.StringProperty;

public interface ISpieler {

    void spiele();
    ISpielFigur getSpielFigur();
    StringProperty wurfAnzahlPProperty();
}
