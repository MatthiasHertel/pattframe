package org.blueberry.spaceinvaders.highscore;

        import javafx.collections.ObservableList;

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
    ObservableList<Score> getHighscoreListPage(int from, int count, String orderBy);

    /**
     * Handlemethode zur Einfuegen eines Datensatzes in die Datenbank
     * @param score
     */
    void insertHighscore(Score score);

    /**
     * Handlemethode zur Updaten eines Datensatzes
     * @param score
     */
    void updateHighscore(Score score);

    /**
     * Handlemethode zur Läschen eines Datensatzes
     * @param score
     */
    void deleteHighscore(Score score);

    /**
     * Handlemethode zur Bestimmung der Platzierung in der Score
     * @param punkte
     * @return Platzierung in der Score (int)
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
