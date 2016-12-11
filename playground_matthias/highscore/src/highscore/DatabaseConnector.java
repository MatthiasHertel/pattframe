package highscore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by matthias on 08.12.16.
 */

public class DatabaseConnector {
    private Connection connection;
    private Statement myStatement;
    private ObservableList<Highscore> highscoreList;
    public void launchConnection(){
        //        bind credentials ... TODO come from .env file
        String url = "jdbc:mysql://192.168.111.111:3306/spaceinvaders?autoReconnect=true&useSSL=false";
        String username = "homestead";
        String password = "secret";

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Highscore> getHighscoreList(){
        String query = "SELECT * FROM highscore";

        highscoreList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet selectionResult = ps.executeQuery();


            while (selectionResult.next()){
                Integer dbId = selectionResult.getInt("ID");
                String id = dbId.toString();
                String name = selectionResult.getString("name");
                Integer punkte = selectionResult.getInt("punkte");
                Highscore selectedHighscore = new Highscore(id,name, punkte);
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