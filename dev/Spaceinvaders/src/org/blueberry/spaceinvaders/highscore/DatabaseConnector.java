package org.blueberry.spaceinvaders.highscore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Created by matthias on 22.12.16.
 */
public class DatabaseConnector {
    private Connection connection;
    private Statement myStatement;
    private ObservableList<Highscore> highscoreList;
    public void launchConnection(){

        //        bind credentials ... TODO come from .env file by herzlchen

//        String url = "TODO";
        String url = SpaceInvaders.getSettings("db.url");
//        String username = "TODO";
        String username = SpaceInvaders.getSettings("db.username");

//        String password = "TODO";
        String password = SpaceInvaders.getSettings("db.password");

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Datenbank Connection konnte nicht hergestellt werden.");
            System.out.println("Sind die Credentials in der config/application.properties korrekt ?");
            System.out.println("url:" + url);
            System.out.println("username:" + username);
            System.out.println("password:" + password);
            System.out.println("Haben sie Internet ?");
//            TODO check ping




//            e.printStackTrace();
        }
    }
    public ObservableList<Highscore> getHighscoreList(){
        String query = "SELECT * FROM highscore ORDER BY punkte DESC";

        highscoreList = FXCollections.observableArrayList();
        Integer ranking = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet selectionResult = ps.executeQuery();


            while (selectionResult.next()){
                ranking++;
//                Integer dbId = selectionResult.getInt("ID");
                String id = ranking.toString();
                String name = selectionResult.getString("name");
                Integer punkte = selectionResult.getInt("punkte");
                DateFormat df = new SimpleDateFormat("dd.MM.YY  HH:mm");

                String created_at = df.format(selectionResult.getTimestamp("created_at")).concat(" Uhr");
//                String created_at = selectionResult.getTimestamp("created_at").toString();

                Highscore selectedHighscore = new Highscore(id,name, punkte, created_at);
                highscoreList.add(selectedHighscore);
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highscoreList;
    }

    public void insertHighscore(Highscore highscore){
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO highscore (name, punkte, created_at, updated_at ) VALUES  (?,?,?,?);");
            ps.setString(1, highscore.getName());
            ps.setInt(2, highscore.getPunkte());
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            ps.setString(3, currentTime);
            ps.setString(4, currentTime);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            e.printStackTrace();
        }
    }

    public void deleteHighscore(Highscore highscore){
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM highscore WHERE id = ?;");
            ps.setInt(1, highscore.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetHighscore () {
        String query = "TRUNCATE highscore;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
