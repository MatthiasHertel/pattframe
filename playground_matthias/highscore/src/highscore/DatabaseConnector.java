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
        try {
            connection = DriverManager.getConnection("jdbc:mysql://192.168.111.111:3306/spaceinvaders", "homestead", "secret");
            myStatement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Highscore> getHighscoreList(){
        String query = "SELECT * FROM highscore";
        highscoreList = FXCollections.observableArrayList();

        try {
            ResultSet selectionResult = myStatement.executeQuery(query);

            while (selectionResult.next()){
                Integer dbId = selectionResult.getInt("ID");
                String id = dbId.toString();
                String name = selectionResult.getString("name");
                Integer punkte = selectionResult.getInt("punkte");
                Highscore selectedHighscore = new Highscore(id,name, punkte);
                highscoreList.add(selectedHighscore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highscoreList;
    }

    public void insertHighscore(Highscore highscore){
        String query = "INSERT INTO highscore (name, punkte) VALUES  (\'" + highscore.getName() + "\',\'"+ highscore.getPunkte() +"\' );";
        System.out.println(query);
        try {
            myStatement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateHighscore(Highscore highscore){
        String query = "UPDATE highscore SET name = \'" + highscore.getName() + "\', punkte = \'" + highscore.getPunkte() + "\' WHERE ID = " + highscore.getId() + ";";
        System.out.println(query);
        try {
            myStatement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteHighscore(Highscore highscore){
        String query = "DELETE FROM highscore WHERE id = " + highscore.getId() + ";";
        System.out.println(query);

        try {
            myStatement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetHighscore () {
        String query = "TRUNCATE highscore;";
        System.out.println(query);

        try {
            myStatement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}