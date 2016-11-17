package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Pferd {

    private IntegerProperty position;

    public final int getPosition() {
        if (position != null)
            return position.get();
        return 0;
    }

    public final void setPosition(int start) {
        this.positionProperty().set(start);
    }



    public final IntegerProperty positionProperty() {
        if (position == null) {
            position = new SimpleIntegerProperty(0);
        }
        return position;
    }

  
  public Pferd() {

  }

  public void setzeZug(int punktwert) {
      setPosition(getPosition() + punktwert);
  }

}