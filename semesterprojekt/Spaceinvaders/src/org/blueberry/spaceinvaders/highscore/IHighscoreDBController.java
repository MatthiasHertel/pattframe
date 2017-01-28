package org.blueberry.spaceinvaders.highscore;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

/**
 * Interface Highscore Datenbank
 * bietet die Highscore DB relevanten Funktionen an
 * zum Zugriff aus dem Controller
 */
public interface IHighscoreDBController {

    /**
     * Getter-Methode
     * wonach und in welcher Reihenfolge sortiert wird
     * @return DESC/ASC Spaltennname
     */
    String getOrderBy();

    /**
     * Setter-Methode
     * wonach und in welcher Reihenfolge sortiert wird
     * @param orderBy -DESC/ASC Spaltennname
     */
    void setOrderBy(String orderBy);

    /**
     * Setter-Methode
     * relevant für die pagination-Logik
     * @param from -für DB Query Index
     */
    void setFrom(int from);

    /**
     * Setter-Methode
     * @param itemsPerPage -für DB Query Anzahl der Datensätze
     */
    void setItemsPerPage(int itemsPerPage);

    /**
     * Verbindung zum Remote DBMS
     *
     * @param url URL
     * @param user USER
     * @param pw PASSWORD
     */
    void connect(String url, String user, String pw);

    /**
     * Akualisiert die Daten vom DBServer
     */
    void refreshDBData();

    /**
     * Bestimmt die Platzierung (auf dem Server)
     * @param points Spielerpunkte
     */
    void determineRanking(int points);

    /**
     * Fügt einen einen Highscore in die DB ein (insert)
     * @param highscore HIGHSCORE (Punktwert + Spielername)
     */
    void addHighscore(Highscore highscore);

    IntegerProperty pageCountProperty();
    ObservableList<Highscore> highscoreListProperty();
    IntegerProperty rankingProperty();
    BooleanProperty addedProperty();

}
