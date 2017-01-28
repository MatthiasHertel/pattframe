package org.blueberry.spaceinvaders.highscore;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

public interface IHighscoreDBController {

    String getOrderBy();
    void setOrderBy(String orderBy);
    void setFrom(int from);
    void setItemsPerPage(int itemsPerPage);
    void connect(String url, String user, String pw);
    void refreshDBData();
    void determineRanking(int points);
    void addHighscore(Score score);
    IntegerProperty pageCountProperty();
    ObservableList<Score> highscoreListProperty();
    IntegerProperty rankingProperty();
    BooleanProperty addedProperty();

}
