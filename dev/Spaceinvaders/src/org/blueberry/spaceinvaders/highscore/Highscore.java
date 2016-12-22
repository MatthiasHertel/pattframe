package org.blueberry.spaceinvaders.highscore;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Created by matthias on 15.12.16.
 */
public class Highscore {

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty punkte;

    public Highscore(String xid, String xname, Integer xpunkte) {
        this.id = new SimpleStringProperty(xid);
        this.name = new SimpleStringProperty(xname);
        this.punkte= new SimpleIntegerProperty(xpunkte);

    }
    public String toString(){
        return "ID: " + id + ", name: " + name + ", punkte " + punkte;
    }

    public int getId() {
        return Integer.parseInt(id.get());
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }


    public int getPunkte() {
        return punkte.get();
    }

    public SimpleIntegerProperty punkteProperty() {
        return punkte;
    }

    public void setPunkte(Integer punkte) {
        this.punkte.set(punkte);
    }
}
