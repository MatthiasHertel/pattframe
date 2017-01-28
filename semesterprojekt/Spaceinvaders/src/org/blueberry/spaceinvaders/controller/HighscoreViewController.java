package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.skin.TableColumnHeader;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.gameengine.Game;
import org.blueberry.spaceinvaders.highscore.*;


/**
 * HighscoreViewController-Klasse
 */
public class HighscoreViewController implements Initializable {

    @FXML
    private TableView<Score> crudTable;
    @FXML
    private TableColumn<Score, String> nameColumn;
    @FXML
    private TableColumn<Score, Integer> idColumn;
    @FXML
    private TableColumn<Score, Integer> punkteColumn;
    @FXML
    private TableColumn<Score, String> dateColumn;
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
    @FXML
    private Label messageLabel;

    private int itemsPerPage = Integer.parseInt(SpaceInvaders.getSettings("highscore.itemsperpage"));
    private IHighscoreDBController highscoreDB = MySQLHighscoreDBController.getInstance();
    private IntegerProperty scoreProperty = Game.getInstance().getPlayer().scoreProperty();


    /**
     * Inizialisiert die Controller-Klasse
     * MouseClick fuer die Tableview (Sortierung)
     * KeyEvent fuer Paginationnavigation ueber CursorTasten
     * Bindings
     * Population der Tableview ueber Bindings
     * @param url URL
     * @param rb ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        highscoreDB.connect(SpaceInvaders.getSettings("db.url"), SpaceInvaders.getSettings("db.username"), SpaceInvaders.getSettings("db.password"));

        if (scoreProperty.intValue() > 0) {
            Platform.runLater(()-> nameField.requestFocus());
            highscoreDB.determineRanking(scoreProperty.intValue());
        }

        highscoreDB.setOrderBy("punkte DESC");

        highscoreDB.setItemsPerPage(itemsPerPage);

        highscoreDB.addedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                highscoreDB.setOrderBy("created_at DESC");
                getDBData(0);
                crudTable.requestFocus();
            }
        });

        crudTable.setItems(highscoreDB.highscoreListProperty());

        crudTable.setOnMouseClicked(event ->  {
            if (event.getTarget() instanceof TableColumnHeader) {
                handleTableHeaderClick(((TableColumnHeader) event.getTarget()).getId());
//                event.consume();
            }
        });

//        crudTable.setFocusTraversable(true);
        crudTable.setOnKeyPressed(event -> handleKeyPressed(event.getCode()));

        crudTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //populate the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        punkteColumn.setCellValueFactory(new PropertyValueFactory<>("punkte"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        messageLabel.textProperty().bind(
                new SimpleStringProperty("Sie haben " + scoreProperty.intValue() + " Punkte erreicht und damit Platz ")
                        .concat(highscoreDB.rankingProperty().asString().concat(" in der Score belegt! Bitte geben Sie Ihren Namen ein."))
        );

        hbox_mainmenuBtn.visibleProperty().bind(
                Bindings.equal(0, scoreProperty)
        );

        message_banner.visibleProperty().bind(
                Bindings.greaterThan(scoreProperty, 0)
        );
        hbox_input.visibleProperty().bind(
                Bindings.greaterThan(scoreProperty, 0)
        );

        addButton.disableProperty().bind(
                Bindings.greaterThan(4, nameField.textProperty().length())
        );

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) ->
                getDBData(newValue.intValue() * itemsPerPage)
        );

        pagination.visibleProperty().bind(
                Bindings.greaterThan( highscoreDB.pageCountProperty(), 1)
        );

        pagination.pageCountProperty().bind( highscoreDB.pageCountProperty());
    }


    /**
     * Eventhandlemethode Tastendruck für pagination
     * und zum Wechseln in die Hauptansicht
     * @param keyCode Taste
     */
    private void handleKeyPressed(KeyCode keyCode) {
        int index = pagination.getCurrentPageIndex();

        switch (keyCode) {
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
    }

    /**
     * Eventhandlemethode Klick auf der Tabelle
     * @param columnNane Spaltenname
     */
    private void handleTableHeaderClick(String columnNane) {
        String orderBy = highscoreDB.getOrderBy();

        switch (columnNane){
            case "dateColumn":
                orderBy = orderBy.equals( "created_at DESC") ? "created_at ASC" : "created_at DESC";
                break;
            case "punkteColumn":
                orderBy = orderBy.equals("punkte DESC") ? "punkte ASC" : "punkte DESC";
                break;
            case "idColumn":
                orderBy = orderBy.equals("position ASC") ? "position DESC" : "position ASC";
                break;
            case "nameColumn":
                orderBy = orderBy.equals("name ASC") ? "name DESC" : "name ASC";
                break;
        }

        highscoreDB.setOrderBy(orderBy);

        if (pagination.getCurrentPageIndex() == 0) getDBData(0);
        else pagination.setCurrentPageIndex(0);
    }

    /**
     * Eventhandlemethode zum Aufruf der Handlemethode
     * @param event Klickereignis
     */
    // if there multiple action buttons (for settingsview)
    public void onEventOccured(ActionEvent event) {
        Button button = (Button) event.getSource();

        switch (button.getId()) {
            case "addButton": {
                addScore(scoreProperty.intValue());
                break;
            }
        }
    }

    /**
     * Handlemethode beim Klick auf Button "Speichern" zum Einfügen des Datensatzes
     * @param punkte Spielerpunktwert
     */
    private void addScore(int punkte) {

        Score score = new Score(0 , nameField.getText(), punkte, "");

        highscoreDB.addHighscore(score);

        Game.getInstance().reset();

        // TODO show toast message crud successfully
        // controlfx notification toast message
        // http://controlsfx.bitbucket.org/org/controlsfx/control/Notifications.html
        // TODO adding new highscore value jump to page and highlight this row
    }


    /**
     * Aktualisiert die DB-spezifischen Daten
     * @param from - Ab welchen Datensatz die Daten geladen werden sollen (LIMIT - Start, itemsPerPage ist die Anzahl )
     */
    private void getDBData(int from){
        highscoreDB.setFrom(from);
        highscoreDB.refreshDBData();
    }

    /**
     * Wechselt zur Welcome-View.
     * wird beim Click von "Abbrechen-" und "Hauptmenu-" Button aufgerufen
     */
    @FXML
    private void goToScreenWelcomeView() {
        Game.getInstance().reset();
        SpaceInvaders.setScreen("WelcomeView");
    }
}
