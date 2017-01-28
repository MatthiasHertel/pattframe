package org.blueberry.spaceinvaders.highscore;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

abstract class AHighscoreDBController implements IHighscoreDBController {

    private String orderBy = "punkte DESC";
    private int from;
    private int itemsPerPage;
    private int points;
    private String url;
    private String user;
    private String pw;
    private Score score;

    private IntegerProperty pageCount = new SimpleIntegerProperty(0);
    private IntegerProperty ranking = new SimpleIntegerProperty(0);
    private ObservableList<Score> highscoreList = FXCollections.observableArrayList();
    private BooleanProperty added = new SimpleBooleanProperty(false);

    private RankingService rankingService = new RankingService();
    private ListService listService = new ListService();
    private ConnectService connectService = new ConnectService();
    private ScoreService scoreService = new ScoreService();
    private PageCountService pageCountService = new PageCountService();


    /**
     * Konstruktor
     * setzt die Ereignishandler der benötigten Services
     */
    AHighscoreDBController(){

        rankingService.setOnSucceeded(t -> {
            ranking.set((Integer) t.getSource().getValue());
            rankingService.reset();
        });

        rankingService.setOnFailed(event -> {
            System.out.println("rankingservice failed... try again");
            try {
                Thread.sleep(300);
                rankingService.reset();
                rankingService.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        listService.setOnSucceeded(event -> {
            highscoreList.setAll((ObservableList<Score>)event.getSource().getValue());
            listService.reset();
        });
        listService.setOnCancelled(event -> {
            listService.reset();
            listService.start();
        });

        connectService.setOnSucceeded(event -> {
            refreshDBData();
            connectService.reset();
        });

        pageCountService.setOnSucceeded(event -> {
            pageCount.set((Integer) event.getSource().getValue());
            pageCountService.reset();
        });
        pageCountService.setOnCancelled(event -> {
            pageCountService.reset();
            pageCountService.start();
        });

        scoreService.setOnSucceeded(event -> {
            added.set(true);
            scoreService.reset();
        });
    }


    abstract public int getRecordCount();
    abstract public ObservableList<Score> getHighscoreList(int from, int count, String orderBy);
    abstract public int detectRanking(int points);
    abstract public void connectServer(String url, String user, String pw);
    abstract public void insertScore(Score score);



    @Override
    public String getOrderBy() {
        return orderBy;
    }

    @Override
    public void setOrderBy(String orderBy){
        this.orderBy = orderBy;
    }

    @Override
    public void setFrom(int from) {
        this.from = from;
    }

    @Override
    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public IntegerProperty rankingProperty(){
        return ranking;
    }

    @Override
    public IntegerProperty pageCountProperty(){
        return pageCount;
    }

    @Override
    public ObservableList<Score> highscoreListProperty() {
        return highscoreList;
    }


    @Override
    public BooleanProperty addedProperty(){
        return added;
    }

    @Override
    public void determineRanking(int points){
        this.points = points;

        System.out.println("points from determine: " + points);
        rankingService.start();
    }

    @Override
    public void refreshDBData(){
        if (listService.getState() != Worker.State.READY){
            listService.cancel();
        }
        else listService.start();

        if (pageCountService.getState() != Worker.State.READY){
            pageCountService.cancel();
        }
        else pageCountService.start();
    }

    @Override
    public void connect(String url, String user, String pw){
        this.url = url;
        this.user = user;
        this.pw = pw;

        connectService.start();
    }

    @Override
    public void addHighscore(Score score){
        this.score = score;
        scoreService.start();
    }


    /**
     * Hilfsmethode zur Bestimmung bzw. Setzen der ListView-Seitenanzahl
     * @param totalCount - Anzahl aller Highscores
     * @param itemsPerPage - Anzahl der darzustellenden Highscores pro ListView Seite
     * @return Seitenanzahl
     */
    private int calcPageCount(int totalCount, int itemsPerPage) {
        float floatCount = (float) totalCount / (float) itemsPerPage;
        int intCount = totalCount / itemsPerPage;
        return ((floatCount > intCount) ? intCount + 1 : intCount);
    }

    /**
     * Nested Class - Service zum Einfügen eines Spieler Score in die DB
     */
    private class ScoreService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    added.set(false);
                    insertScore(score);
                    return null;
                }
            };
        }
    }


    /**
     * Nested Class - Service zum Berechnen der Seitenanzahl für die ListView
     */
    private class PageCountService extends Service<Integer> {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    return calcPageCount(getRecordCount(), itemsPerPage);
                }
            };
        }
    }


    /**
     * Nested Class - Service zum Herstellen der Serververbindung
     */
    private class ConnectService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    connectServer(url, user, pw);
                    return null;
                }
            };
        }
    }


    /**
     * Nested Class - Service zum Holen der Datensätze aus der Datenbank
     */
    private class ListService extends Service<ObservableList<Score>> {
        @Override
        protected Task<ObservableList<Score>> createTask() {
            return new Task<ObservableList<Score>>() {
                @Override
                protected ObservableList<Score> call() {
                    return getHighscoreList(from, itemsPerPage, orderBy);
                }
            };
        }
    }


    /**
     * Nested Class - Service zur Platzierungsbestimmung
     */
    private class RankingService extends Service<Integer> {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws InterruptedException {
                    return detectRanking(points);
                }
            };
        }
    }


}
