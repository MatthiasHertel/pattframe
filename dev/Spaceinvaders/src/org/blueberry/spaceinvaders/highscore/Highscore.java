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
    private SimpleStringProperty created_at;

    public Highscore(String xid, String xname, Integer xpunkte, String xcreated_at) {
        this.id = new SimpleStringProperty(xid);
        this.name = new SimpleStringProperty(xname);
        this.punkte= new SimpleIntegerProperty(xpunkte);
        this.created_at = new SimpleStringProperty(xcreated_at);

    }
    public String toString(){
        return "ID: " + id + ", name: " + name + ", punkte " + punkte + ", created_at " + created_at;
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

    public String getDate() {
        return created_at.get();
    }

    public SimpleStringProperty created_atProperty() {
        return created_at;
    }

    public void setDate(String created_at) {
        this.created_at.set(created_at);
    }
}
