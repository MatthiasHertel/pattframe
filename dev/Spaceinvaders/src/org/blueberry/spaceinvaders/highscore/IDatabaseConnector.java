package org.blueberry.spaceinvaders.highscore;

import javafx.collections.ObservableList;

import java.util.List;

/**
 * Interface für die Datenbankconnectoren
 */
public interface IDatabaseConnector {

    /**
     * Methode zum Verbinden mit der Datenbank
     * @param url URL zum DB-Host
     * @param user Username Anmeldedaten (user credential)
     * @param pw Passwort Anmeldedaten (passphrase credential)
     */
    void connect(String url, String user, String pw);
//    List<Object> getRecords(String table);
    ObservableList<Highscore> getHighscoreList();
    ObservableList<Highscore> getHighscoreListPage(int from, int till, String orderBy);

    /**
     * Handlemethode zur Einfuegen eines Datensatzes in die Datenbank
     * @param highscore
     */
    void insertHighscore(Highscore highscore);

    /**
     * Handlemethode zur Updaten eines Datensatzes
     * @param highscore
     */
    void updateHighscore(Highscore highscore);

    /**
     * Handlemethode zur Läschen eines Datensatzes
     * @param highscore
     */
    void deleteHighscore(Highscore highscore);

    /**
     * Handlemethode zur Bestimmung der Platzierung in der Highscore
     * @param punkte
     * @return Platzierung in der Highscore (int)
     */
    int determinePosition(int punkte);

    /**
     * Handlemethode zum Loeschen der Datenbank
     */
    void resetHighscoreTable();

    /**
     * Handlemethode zur Bestimmung der Anzahl der Datensaetze in der Datenbank
     * @return
     */
    int getCount();

    /**
     * Handlemethode zum Verbindungsstatus der Datenbankverbindung
     * @return true/false
     */
    boolean isClosed();

}
