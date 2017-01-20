package org.blueberry.spaceinvaders.highscore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Created by KK on 19.01.2017.
 */
public class MySQLDBConnector implements IDatabaseConnector {

    private Connection connection;
    private static final MySQLDBConnector mySQLDBConnector = new MySQLDBConnector();


    private MySQLDBConnector(){}

    public static MySQLDBConnector getInstance(){
        return mySQLDBConnector;
    }

    @Override
    public void connect(String url, String user, String pw) {

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

    @Override
    public ObservableList<Highscore> getHighscoreList(){
        String query = "SELECT * FROM highscore ORDER BY punkte DESC";

        ObservableList<Highscore> highscoreList = FXCollections.observableArrayList();
        Integer ranking = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet selectionResult = ps.executeQuery();


            while (selectionResult.next()){
                ranking++;
//                Integer dbId = selectionResult.getInt("ID");
                String name = selectionResult.getString("name");
                Integer punkte = selectionResult.getInt("punkte");
                DateFormat df = new SimpleDateFormat("dd.MM.YY  HH:mm");

                String created_at = df.format(selectionResult.getTimestamp("created_at")).concat(" Uhr");
//                String created_at = selectionResult.getTimestamp("created_at").toString();

                Highscore selectedHighscore = new Highscore(ranking, name, punkte, created_at);
                highscoreList.add(selectedHighscore);
            }

            ps.close();
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
        return highscoreList;
    }



    @Override
    public void insertHighscore(Highscore highscore){
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO highscore (name, punkte) VALUES  (?,?);");
            ps.setString(1, highscore.getName());
            ps.setInt(2, highscore.getPunkte());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void updateHighscore(Highscore highscore){
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE highscore SET name = ? , punkte = ? , updated_at = ? WHERE ID = ?");
            ps.setString(1, highscore.getName());
            ps.setInt(2, highscore.getPunkte());
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            ps.setString(3, currentTime);
            ps.setInt(4, highscore.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void deleteHighscore(Highscore highscore){
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM highscore WHERE id = ?;");
            ps.setInt(1, highscore.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public int determinePosition(int punkte) {
        try {
//            PreparedStatement ps = connection.prepareStatement("select COUNT(*)+1 from highscore WHERE punkte >= ?");
            PreparedStatement ps = connection.prepareStatement("select COUNT(DISTINCT punkte)+1 from highscore WHERE punkte > ?");

            ps.setInt(1, punkte);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return(rs.getInt(1));

        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public void resetHighscoreTable() {
        String query = "TRUNCATE highscore;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        String query = "SELECT COUNT(*) FROM highscore;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            ps.close();
            return count;

        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public ObservableList<Highscore> getHighscoreListPage(int from, int till, String orderBy){
//        String query = "SELECT * FROM highscore ORDER BY " + orderBy + " LIMIT " + from + "," + till ;
        String query = "SELECT name, created_at, punkte, (select COUNT(DISTINCT punkte)+1 from highscore WHERE punkte > h.punkte)AS position FROM highscore h order by " + orderBy + " LIMIT " + from + "," + till ;

        ObservableList<Highscore> highscoreList = FXCollections.observableArrayList();
        Integer ranking = from;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet selectionResult = ps.executeQuery();


            while (selectionResult.next()){
                ranking++;
//                Integer dbId = selectionResult.getInt("ID");
                String name = selectionResult.getString("name");
                Integer punkte = selectionResult.getInt("punkte");
                Integer position = selectionResult.getInt("position");
                DateFormat df = new SimpleDateFormat("dd.MM.YY  HH:mm");

                String created_at = df.format(selectionResult.getTimestamp("created_at")).concat(" Uhr");
//                String created_at = selectionResult.getTimestamp("created_at").toString();

                Highscore selectedHighscore = new Highscore(position, name, punkte, created_at);
                highscoreList.add(selectedHighscore);
            }

            ps.close();
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
        return highscoreList;
    }

    public boolean isClosed()  {

        if (connection == null) return true;
        try {
            if (!connection.isClosed()) {
                return  false;
            }
        } catch (SQLException e) {
            SpaceInvaders.showDialog(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
