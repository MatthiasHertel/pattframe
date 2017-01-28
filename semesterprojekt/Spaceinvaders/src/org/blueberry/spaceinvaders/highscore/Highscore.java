package org.blueberry.spaceinvaders.highscore;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Highscore - Entspricht dem Model welches das Datenbankschema repräsentiert (Darstellung eines Datensatzes)
 */
public class Highscore {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty punkte;
    private SimpleStringProperty created_at;

    /**
     * Konstruktor der Highscoreklasse
     * @param xid id
     * @param xname spielername
     * @param xpunkte spielerpunkte
     * @param xcreated_at Zeitstempel für das Erzeugen der row
     */
    public Highscore(Integer xid, String xname, Integer xpunkte, String xcreated_at) {
        this.id = new SimpleIntegerProperty(xid);
        this.name = new SimpleStringProperty(xname);
        this.punkte= new SimpleIntegerProperty(xpunkte);
        this.created_at = new SimpleStringProperty(xcreated_at);

    }

    /**
     * Hilfsmethode für die Sout Abgabe
     * @return
     */
    public String toString(){
        return "ID: " + id + ", name: " + name + ", punkte " + punkte + ", created_at " + created_at;
    }
    /**
     * Getter-Methode Id
     * @return Id
     */
    public int getId() {
        return id.get();
    }
    /**
     * Bind-Methode
     * @return Id
     */
    public SimpleIntegerProperty idProperty() {
        return id;
    }
    /**
     * Setter-Methode Id
     * @param id
     */
    public void setId(Integer id) {
        this.id.set(id);
    }
    /**
     * Getter-Methode Name
     * @return
     */
    public String getName() {
        return name.get();
    }
    /**
     * Bind-Methode
     * @return Name
     */
    public SimpleStringProperty nameProperty() {
        return name;
    }
    /**
     * Setter-Methode Name
     * @param name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Getter-Methode Punkte
     * @return Punkte
     */
    public int getPunkte() {
        return punkte.get();
    }
    /**
     * Bind-Methode
     * @return punkte
     */
    public SimpleIntegerProperty punkteProperty() {
        return punkte;
    }
    /**
     * Setter-Methode Name
     * @param punkte
     */
    public void setPunkte(Integer punkte) {
        this.punkte.set(punkte);
    }

    /**
     * Getter-Methode Zeitstempel
     * @return Zeitstempel
     */
    public String getDate() {
        return created_at.get();
    }
    /**
     * Bind-Methode
     * @return Zeitstempel
     */
    public SimpleStringProperty created_atProperty() {
        return created_at;
    }
    /**
     * Setter-Methode Zeitstempel
     * @param created_at
     */
    public void setDate(String created_at) {
        this.created_at.set(created_at);
    }
}
