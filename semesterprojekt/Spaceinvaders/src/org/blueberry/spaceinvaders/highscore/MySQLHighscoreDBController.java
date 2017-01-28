package org.blueberry.spaceinvaders.highscore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class MySQLHighscoreDBController extends AHighscoreDBController {


    private Connection connection;
    private static final MySQLHighscoreDBController instance = new MySQLHighscoreDBController();

    static public MySQLHighscoreDBController getInstance() {
        return instance;
    }

    /**
     * Privater Konstruktor - Singleton
     */
    private MySQLHighscoreDBController() {
    }


    /**
      * Methode zum Verbinden mit der Datenbank
      * @param url URL zum DB-Host
      * @param user Username Anmeldedaten (user credential)
      * @param pw Passwort Anmeldedaten (passphrase credential)
      */
    @Override
    public void connectServer(String url, String user, String pw) {
        if (!isClosed()) return;

        try {
            connection = DriverManager.getConnection(url, user, pw);
        } catch (SQLException e) {
            String message = "Datenbank Connection konnte nicht hergestellt werden.\n" +
                    "Sind die Credentials in der config/application.properties korrekt?\n" +
                    "url:" + url + "\n" +
                    "username:" + user + "\n" +
                    "password:" + pw + "\n" +
                    "Haben sie Internet?";
            System.out.println(message);
            SpaceInvaders.showDialog(message + "\n" + e.getMessage());

//            TODO check ping
        }


        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            System.out.println("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    SpaceInvaders.showDialog(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Speichert einen Punktwert in die Datenbank
     * @param score Punktwert, der in die DB eingetragen werden soll
     */
    @Override
    public void insertScore(Score score) {
        waitForConnection();

        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO highscore (name, punkte) VALUES  (?,?);");
            ps.setString(1, score.getName());
            ps.setInt(2, score.getPunkte());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Handlemethode zur Bestimmung der Anzahl der Datensaetze in der Datenbank
     * @return Dtensatzanzahl
     */
    @Override
    public int getRecordCount() {
        waitForConnection();

        String query = "SELECT COUNT(*) FROM highscore;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            ps.close();
            return count;

        } catch (SQLException e) {
            SpaceInvaders.showDialog("Error in Class: " + this.getClass().getSimpleName() + ".\n" + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Handlemethode für Pagination
     * Holt die Datensätze aus der Datenbank, die für die entsprechende Seite angezeigt werden sollen
     * @param from - LIMIT für MySQL
     * @param count - LIMIT für MySQL
     * @param orderBy - ORDER BY Clause für MySQL
     * @return highscorelist
     */
    @Override
    public ObservableList<Score> getHighscoreList(int from, int count, String orderBy){
        waitForConnection();

        orderBy = orderBy == null ? "" : "ORDER BY " + orderBy;
        String query = "SELECT name, created_at, punkte, (select COUNT(DISTINCT punkte)+1 from highscore WHERE punkte > h.punkte) AS position FROM highscore h " + orderBy + " LIMIT " + from + "," + count ;

        ObservableList<Score> highscoreList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet selectionResult = ps.executeQuery();

            while (selectionResult.next()){
                String name = selectionResult.getString("name");
                Integer punkte = selectionResult.getInt("punkte");
                Integer position = selectionResult.getInt("position");
                DateFormat df = new SimpleDateFormat("dd.MM.YY  HH:mm");
                String created_at = df.format(selectionResult.getTimestamp("created_at")).concat(" Uhr");

                Score selectedHighscore = new Score(position, name, punkte, created_at);
                highscoreList.add(selectedHighscore);
            }

            ps.close();
        } catch (SQLException  e) {
            SpaceInvaders.showDialog("Error in Class: " + this.getClass().getSimpleName() + ".\n" + e.getMessage());
            e.printStackTrace();
        }
        return highscoreList;
    }

    /**
     * Ermittelt den möglichen Platz in der HighscoreListe
     * wird vom DBServer ermittelt
     * @param points - aktuelle Spielerpunkte
     * @return Ranking
     */
    @Override
    public int detectRanking(int points) {
        waitForConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select COUNT(DISTINCT punkte)+1 from highscore WHERE punkte > ?");

            ps.setInt(1, points);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return(rs.getInt(1));

        } catch (SQLException e) {
            SpaceInvaders.showDialog("Error in Class: " + this.getClass().getSimpleName() + ".\n" + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Methode, die wartet bis die Datenbankverbindung steht.
     * wird von anderen Datenbakfunktionen (z.B. getRecordCount, getList...) aufgerufen
     */
    private void waitForConnection(){
        int i = 0;
        while (isClosed() && i < 10){
            i++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i > 10){
                i = 0;
                System.out.println("Waiting for MySQL Connection");
                connect(SpaceInvaders.getSettings("db.url"), SpaceInvaders.getSettings("db.username"), SpaceInvaders.getSettings("db.password"));
            }
        }
    }
    /**
     * Handlemethode zum Verbindungsstatus der Datenbankverbindung
     * @return boolean
     */
    private boolean isClosed()  {

        if (connection == null) return true;
        try {
            if (!connection.isClosed()) {
                return  false;
            }
        } catch (SQLException e) {
            SpaceInvaders.showDialog("Error in Class: " + this.getClass().getSimpleName() + ".\n" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
