package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Pferd implements ISpielFigur {

    private IntegerProperty position;

    @Override
    public final int getPosition() {
        if (position != null)
            return position.get();
        return 0;
    }

    @Override
    public final void setPosition(int value) {
        positionProperty().set(value);
    }


    @Override
    public final IntegerProperty positionProperty() {
        if (position == null) {
            position = new SimpleIntegerProperty(0);
        }
        return position;
    }


    public Pferd() {
    }

}