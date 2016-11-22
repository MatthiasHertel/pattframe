package sample;

import javafx.beans.property.IntegerProperty;

public interface ISpielFigur {

    int getPosition();
    void setPosition(int value);
    IntegerProperty positionProperty();

}
