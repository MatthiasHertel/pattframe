package org.blueberry.spaceinvaders.highscore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 19.01.2017.
 */
public class MySQLDBConnector implements IDatabaseConnector {

    private Connection connection;


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
            e.printStackTrace();
        }
    }



    @Override
    public int determinePosition(int punkte) {
        try {
            PreparedStatement ps = connection.prepareStatement("select COUNT(*)+1 from highscore WHERE punkte >= ?");
            ps.setInt(1, punkte);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return(rs.getInt(1));

        } catch (SQLException e) {
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
            e.printStackTrace();
        }
    }
}
