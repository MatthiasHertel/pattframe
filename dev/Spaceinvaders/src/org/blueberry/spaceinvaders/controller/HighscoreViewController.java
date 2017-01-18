package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.gameengine.Game;
import org.blueberry.spaceinvaders.highscore.DatabaseConnector;
import org.blueberry.spaceinvaders.highscore.Highscore;

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
    private DatabaseConnector mysqlConnector;

    private Label messageLabel = new Label();
    private String punkt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mysqlConnector = new DatabaseConnector();
        mysqlConnector.launchConnection();

        // set resize policy
        crudTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //populate the table
        idColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("name"));
        punkteColumn.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("punkte"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("created_at"));

        // default inputs not visible
        hbox_input.setVisible(false);

        //Fetch data from gameplay
        punkt = new Integer(Game.getInstance().getPlayer().getScore()).toString();

        // if clause to determine game score
        // TODO there should be a flag from game instance
        if (!"0".equals(punkt)) {
            hbox_mainmenuBtn.setVisible(false);
            String position = mysqlConnector.determinePosition(punkt);
            messageLabel.setText("Sie haben " + punkt + " Punkte erreicht und damit Platz " + position + " in der Highscore belegt!!!");
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setFont(Font.font("Impact", 30));
            message_banner.getChildren().add(messageLabel);
            hbox_input.setVisible(true);
        }

        // disable Button until Namefield has 6 chars
        addButton.disableProperty().bind(
                Bindings.greaterThan(6, nameField.textProperty().length())
        );


        highscore = mysqlConnector.getHighscoreList();
        pageCount = getPageCount(highscore.size(), itemsPerPage);

        // hide pagination if highscore.size items perpage (only one site)
        if (highscore.size() < itemsPerPage) {
            pagination.setVisible(false);
        }

        pagination.setPageCount(pageCount);

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("Pagination Changed from " + oldValue + " , to " + newValue);
                currentPageIndex = newValue.intValue();
                refreshList();
            }
        });

        // call refreshlist to init table
        refreshList();
    }

    /**
     * onEventOccured
     * @param event
     */
    // if there multiple action buttons (for settingsview)
    public void onEventOccured(ActionEvent event) {
        Button button = (Button) event.getSource();

        // case switch
        switch (button.getId()) {
            case "addButton": {
                System.out.println("addButton");
                addNewHighscore();
                refreshList();
                break;
            }
        }
    }

    /**
     * refreshList
     */
    private void refreshList() {
        highscore = mysqlConnector.getHighscoreList();
        crudTable.getItems().setAll(highscore.subList(currentPageIndex * itemsPerPage, ((currentPageIndex * itemsPerPage + itemsPerPage <= highscore.size()) ? currentPageIndex * itemsPerPage + itemsPerPage : highscore.size())));
    }

    /**
     * addNewHighscore
     */
    public void addNewHighscore() {
        String id = "1";
        String name = nameField.getText();
        Integer punkte = Integer.parseInt(punkt);
        String date = " ";
        Highscore newHighscore = new Highscore(id, name, punkte, date);
        mysqlConnector.insertHighscore(newHighscore);
        refreshList();
        hbox_input.setVisible(false);
        // after save data set score to 0
        Game.getInstance().getPlayer().setScore(0);
        message_banner.setVisible(false);
        // TODO show toast message crud successfully
        // controlfx notification toast message
        // http://controlsfx.bitbucket.org/org/controlsfx/control/Notifications.html
        // TODO adding new highscore value jump to page and highlight this row
        // pagination.currentPageIndexProperty().setValue(2);
    }

    /**
     * getPageCount
     * @param totalCount
     * @param itemsPerPage
     * @return
     */
    // determine pagecount for pagination
    private int getPageCount(int totalCount, int itemsPerPage) {
        float floatCount = Float.valueOf(totalCount) / Float.valueOf(itemsPerPage);
        int intCount = totalCount / itemsPerPage;
        return ((floatCount > intCount) ? ++intCount : intCount);
    }

    /**
     * Wechselt zur Welcome-View.
     * @param event
     */
    @FXML
    private void goToScreenWelcomeView(ActionEvent event) {
        SpaceInvaders.setScreen("WelcomeView");
    }


}
