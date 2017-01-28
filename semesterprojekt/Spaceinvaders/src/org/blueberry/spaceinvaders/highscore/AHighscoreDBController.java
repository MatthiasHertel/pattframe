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

/**
 * Abstrakter Highscore DB-Controller
 * implementiert Highscore Interface
 */
abstract class AHighscoreDBController implements IHighscoreDBController {

    private String orderBy = "punkte DESC";
    private int from;
    private int itemsPerPage;
    private int points;
    private String url;
    private String user;
    private String pw;
    private Highscore highscore;

    private IntegerProperty pageCount = new SimpleIntegerProperty(0);
    private IntegerProperty ranking = new SimpleIntegerProperty(0);
    private ObservableList<Highscore> highscoreList = FXCollections.observableArrayList();
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
            highscoreList.setAll((ObservableList<Highscore>)event.getSource().getValue());
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


    /**
     * Abstrakte Methode, die von der konkreten Klasse implementiert wurden müssen
     * @return Anzahl der gesamten Datensätze in der Datenbank
     */
    abstract public int getRecordCount();

    /**
     * Abstrakte Methode, die von der konkreten Klasse implementiert wurden müssen
     *
     * @param from Startpunkt der Query
     * @param count Anzahl der gewünschten Datensätze
     * @param orderBy ORDER BY Klausel
     * @return Liste der Datensätze (Ergebnisse des SELECET-Statements)
     */
    abstract public ObservableList<Highscore> getHighscoreList(int from, int count, String orderBy);

    /**
     * Abstrakte Methode, die von der konkreten Klasse implementiert wurden müssen
     *
     * Bestimmt die Platzierung
     * @param points - Spielerpunkte
     * @return Platz
     */
    abstract public int detectRanking(int points);

    /**
     * Abstrakte Methode, die von der konkreten Klasse implementiert wurden müssen
     *
     * Verbidung zum DB Server
     * @param url URL
     * @param user USER
     * @param pw PASSWORD
     */
    abstract public void connectServer(String url, String user, String pw);

    /**
     * Abstrakte Methode, die von der konkreten Klasse implementiert wurden müssen
     *
     * Fügt einen neuen Datensatz ein
     * @param highscore HIGHSCORE (Name und Punkte des Spielers)
     */
    abstract public void insertScore(Highscore highscore);


    /**
     * Getter-Methode
     * wonach und in welcher Reihenfolge sortiert wird
     * @return ORDER BY Klausel
     */
    @Override
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Setter-Methode
     * wonach und in welcher Reihenfolge sortiert wird
     * @param orderBy -DESC/ASC Spaltennname
     */
    @Override
    public void setOrderBy(String orderBy){
        this.orderBy = orderBy;
    }

    /**
     * Setter-Methode
     * relevant für die pagination-Logik
     * @param from -für DB Query Index
     */
    @Override
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * Setter-Methode
     * @param itemsPerPage -für DB Query Anzahl der Datensätze
     */
    @Override
    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }


    /**
     * Getter-Methode für das Property
     * @return ranking
     */
    @Override
    public IntegerProperty rankingProperty(){
        return ranking;
    }

    /**
     * Getter-Methode für das Property
     * @return pageCount
     */
    @Override
    public IntegerProperty pageCountProperty(){
        return pageCount;
    }

    /**
     * Getter-Methode für das Property
     * @return highscoreListe
     */
    @Override
    public ObservableList<Highscore> highscoreListProperty() {
        return highscoreList;
    }


    /**
     * Getter-Methode für das Property
     * Flag, Wann ist der Insert in der Datenbank abgeschlossen?
     * @return added
     */
    @Override
    public BooleanProperty addedProperty(){
        return added;
    }

    /**
     * Bestimmt die Platzierung (auf dem Server)
     * @param points Spielerpunkte
     */
    @Override
    public void determineRanking(int points){
        this.points = points;

        System.out.println("points from determine: " + points);
        rankingService.start();
    }

    /**
     * Akualisiert die Daten vom DBServer
     */
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

    /**
     * Verbindung zum Remote DBMS
     *
     * @param url URL
     * @param user USER
     * @param pw PASSWORD
     */
    @Override
    public void connect(String url, String user, String pw){
        this.url = url;
        this.user = user;
        this.pw = pw;

        connectService.start();
    }

    /**
     * Fügt einen einen Highscore in die DB ein (insert)
     * @param highscore HIGHSCORE (Punktwert + Spielername)
     */
    @Override
    public void addHighscore(Highscore highscore){
        this.highscore = highscore;
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
     * Nested Class - Service zum Einfügen eines Spieler Highscore in die DB
     */
    private class ScoreService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    added.set(false);
                    insertScore(highscore);
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
    private class ListService extends Service<ObservableList<Highscore>> {
        @Override
        protected Task<ObservableList<Highscore>> createTask() {
            return new Task<ObservableList<Highscore>>() {
                @Override
                protected ObservableList<Highscore> call() {
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
