package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.skin.TableColumnHeader;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.chat.ChatMessageListViewCell;
import org.blueberry.spaceinvaders.chat.ChatObject;
import org.blueberry.spaceinvaders.gameengine.Game;
import org.blueberry.spaceinvaders.gameengine.InvaderGroup;
import org.blueberry.spaceinvaders.highscore.Highscore;
import org.blueberry.spaceinvaders.highscore.IDatabaseConnector;
import org.blueberry.spaceinvaders.highscore.MySQLDBConnector;

import static org.blueberry.spaceinvaders.gameengine.GameStatus.PAUSE;
import static org.blueberry.spaceinvaders.gameengine.GameStatus.PLAY;

/**
 * HighscoreViewController-Klasse
 */
public class HighscoreViewController implements Initializable {

    @FXML
    private TableView<Highscore> crudTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn punkteColumn;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private TextField nameField;
    @FXML
    private Button addButton;
    @FXML
    private Pagination pagination;
    @FXML
    private HBox hbox_input;
    @FXML
    private HBox hbox_mainmenuBtn;
    @FXML
    private HBox message_banner;

    // TODO should be in application.properties
    private int pageCount = 5;
    private int itemsPerPage = 15;
    private int currentPageIndex = 0;

    private ObservableList<Highscore> highscore = FXCollections.observableArrayList();
//    private DatabaseConnector mysqlConnector;
    private IDatabaseConnector mysqlConnector = MySQLDBConnector.getInstance();

    private Label messageLabel = new Label();
    private int punkt = 0;
    private String orderBy = "punkte DESC";
    private SimpleIntegerProperty highScoreCompleteCount= new SimpleIntegerProperty(0);

    /**
     * Inizialisiert die Controller-Klasse
     * Eventfilter fuer die Tableview (Sortierung)
     * KeyEvent fuer Paginationnavigation ueber CursorTasten
     * Population der Tableview ueber Bindings
     * Abhaengig vom Gamestate einblenden des Platzierungsbanner
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        crudTable.addEventFilter(
            MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getTarget() instanceof TableColumnHeader) {

                    switch (((TableColumnHeader) event.getTarget()).getId()){
                        case "dateColumn":
                            orderBy = orderBy == "created_at DESC" ? "created_at ASC" : "created_at DESC";
                            break;
                        case "punkteColumn":
                            orderBy = orderBy == "punkte DESC" ? "punkte ASC" : "punkte DESC";
                            break;
                        case "idColumn":
                            orderBy = orderBy == "position ASC" ? "position DESC" : "position ASC";
                            break;
                        case "nameColumn":
                            orderBy = orderBy == "name ASC" ? "name DESC" : "name ASC";
                            break;
                    }

                    if (pagination.getCurrentPageIndex() == 0){
                        getDBData(0);
                    }
                    else {
                        pagination.setCurrentPageIndex(0);
                    }
                    event.consume();
                }
            }
        );



        crudTable.setFocusTraversable(true);

        crudTable.setOnKeyPressed(event -> {

            int index = pagination.getCurrentPageIndex();

            switch (event.getCode()) {
                case LEFT:
                    if (index > 0) pagination.setCurrentPageIndex(--index);
                    break;
                case RIGHT:
                    if (index < pagination.getPageCount()) pagination.setCurrentPageIndex(++index);
                    break;
                case ESCAPE:
                    SpaceInvaders.setScreen("WelcomeView");
                    break;

            }
        });


//        long start = System.currentTimeMillis();
//        long end = System.currentTimeMillis();
//        System.out.println("Zeit: " + (end - start));

        // set resize policy
        crudTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //populate the table
        idColumn.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("name"));
        punkteColumn.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("punkte"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("created_at"));

        // default inputs not visible
        hbox_input.setVisible(false);

        //Fetch data from gameplay
        punkt = Game.getInstance().getPlayer().getScore();

        // if clause to determine game score
        // TODO there should be a flag from game instance
        if (punkt != 0) {

            RankingService rankingService = new RankingService();
            rankingService.setOnSucceeded(t -> {
                hbox_mainmenuBtn.setVisible(false);
                messageLabel.setText("Sie haben " + punkt + " Punkte erreicht und damit Platz " + t.getSource().getValue() + " in der Highscore belegt! Bitte geben Sie Ihren Namen ein.");
                message_banner.getChildren().add(messageLabel);
                hbox_input.setVisible(true);
                nameField.requestFocus();
            });
            rankingService.start();
        }

        // disable Button until Namefield has 4 chars
        addButton.disableProperty().bind(
                Bindings.greaterThan(4, nameField.textProperty().length())
        );

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) ->
                getDBData(newValue.intValue())
        );


        highScoreCompleteCount.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.intValue() < itemsPerPage) {
                    pagination.setVisible(false);
                }
                else {
                    pagination.setPageCount(getPageCount(newValue.intValue(), itemsPerPage));
                    pagination.setVisible(true);
                }
            });
        });


        crudTable.setItems(highscore);
        getDBData(0);
    }

    /**
     * Eventhandlemethode zum Aufruf der Handlemethode
     * @param event
     */
    // if there multiple action buttons (for settingsview)
    public void onEventOccured(ActionEvent event) {
        Button button = (Button) event.getSource();

        // case switch
        switch (button.getId()) {
            case "addButton": {
                System.out.println("addButton");
                addNewHighscore(punkt);
                orderBy = "created_at DESC";
                getDBData(0);
                break;
            }
        }
    }

    /**
     * Handlemethode zum Einfügen des Datensatzes
     * @param punkte
     */
    public void addNewHighscore(int punkte) {
        int id = 1;
        String name = nameField.getText();
        String date = " ";
        Highscore newHighscore = new Highscore(id, name, punkte, date);
        mysqlConnector.insertHighscore(newHighscore);
//        refreshList();
        hbox_input.setVisible(false);

        Game.getInstance().reset();
        message_banner.setVisible(false);
        // TODO show toast message crud successfully
        // controlfx notification toast message
        // http://controlsfx.bitbucket.org/org/controlsfx/control/Notifications.html
        // TODO adding new highscore value jump to page and highlight this row
        // pagination.currentPageIndexProperty().setValue(2);
        hbox_mainmenuBtn.setVisible(true);
    }

    /**
     * Task zum Abrufen des Datensatzes einer Seite
     * @param page
     */
    public void getDBData(int page){

        Task task = new Task<Void>() {
            @Override public Void call() {

                if(mysqlConnector.isClosed()) {
                    mysqlConnector.connect(SpaceInvaders.getSettings("db.url"), SpaceInvaders.getSettings("db.username"), SpaceInvaders.getSettings("db.password"));
                }

                highscore.setAll(mysqlConnector.getHighscoreListPage(page * itemsPerPage, itemsPerPage, orderBy));

                int currentHighScoreCompleteCount = mysqlConnector.getCount();
                if (currentHighScoreCompleteCount != highScoreCompleteCount.get()) {
                    highScoreCompleteCount.set(currentHighScoreCompleteCount);
                }

                return null;
            }
        };

//        task.run();
        new Thread(task).start();
    }


    /**
     * getPageCount
     * @param totalCount
     * @param itemsPerPage
     * @return
     */
    private int getPageCount(int totalCount, int itemsPerPage) {
        float floatCount = Float.valueOf(totalCount) / Float.valueOf(itemsPerPage);
        int intCount = totalCount / itemsPerPage;
        return ((floatCount > intCount) ? ++intCount : intCount);

    }

    /**
     * Nested Class - Service zur Platzierungsbestimmung
     */
    private class RankingService extends Service<Integer> {

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {

                    if(mysqlConnector.isClosed()) {
                        mysqlConnector.connect(SpaceInvaders.getSettings("db.url"), SpaceInvaders.getSettings("db.username"), SpaceInvaders.getSettings("db.password"));
                    }
                    return mysqlConnector.determinePosition(punkt);
                }
            };
        }
    }

    /**
     * Wechselt zur Welcome-View.
     * @param event
     */
    @FXML
    private void goToScreenWelcomeView(ActionEvent event) {
        Game.getInstance().reset();
        SpaceInvaders.setScreen("WelcomeView");
    }
}
