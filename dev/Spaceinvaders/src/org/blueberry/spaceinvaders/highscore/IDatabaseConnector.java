package org.blueberry.spaceinvaders.highscore;

import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by KK on 19.01.2017.
 */
public interface IDatabaseConnector {


    void connect(String url, String user, String pw);
//    List<Object> getRecords(String table);
    ObservableList<Highscore> getHighscoreList();

    void insertHighscore(Highscore highscore);
    void updateHighscore(Highscore highscore);
    void deleteHighscore(Highscore highscore);

    int determinePosition(int punkte);
    void resetHighscoreTable();

}
