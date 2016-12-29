package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.gameengine.Game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.blueberry.spaceinvaders.highscore.DatabaseConnector;
import org.blueberry.spaceinvaders.highscore.Highscore;

import java.net.URL;
import java.util.ResourceBundle;

public class HighscoreViewController implements Initializable{

    @FXML
    TableView<Highscore> crudTable;

    @FXML private TableColumn nameColumn;
    @FXML private TableColumn idColumn;
    @FXML private TableColumn punkteColumn;
    @FXML private TableColumn dateColumn;

    private ObservableList<Highscore> highscore = FXCollections.observableArrayList();

    @FXML private TextField nameField;
    @FXML private Label punkteField;


    @FXML private Label label;

    @FXML
    private Label punkte;

    @FXML
    private Pagination pagination;

    @FXML
    private HBox hbox_input;

    @FXML
    private void goToScreenWelcomeView(ActionEvent event){
        SpaceInvaders.setScreen("WelcomeView");
    }

    @FXML
    private void goToScreenGameplayView(ActionEvent event){
        SpaceInvaders.setScreen("GameplayView");
    }

    private String addHighscore = "Punkteanzahl: ";
    private String modifyHighscore = "Bearbeite Eintrag";

    private String addingButtonID = "addButton";
    private String savingButtonID = "saveButton";
    private String deletingButtonID = "deleteButton";
    private String reloadButtonID = "reloadButton";

    private int pageCount = 5;
    private int itemsPerPage = 15;
    private int currentPageIndex = 0;

    private DatabaseConnector mysqlConnector;

    private Highscore temporaryHighscore;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        String game = String.valueOf(Game.getInstance().getGameStatus());
//        System.out.println(game);
//        punkte.textProperty().bind(Game.getInstance().getPlayer().scoreProperty().asString());
//        punkte.setText(new Integer(Game.getInstance().getPlayer().getScore()).toString());
        mysqlConnector = new DatabaseConnector();
        mysqlConnector.launchConnection();

        crudTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        idColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("name"));
        punkteColumn.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("punkte"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("created_at"));

        label.setText(addHighscore);

        hbox_input.setVisible(false);

        //Fetch data from gameplay
        String punkt = new Integer(Game.getInstance().getPlayer().getScore()).toString();

        // if clause to determine game state
        // TODO there should be a flag from game instance
        if (!"0".equals(punkt)) {
            //method call show inputs
            show_inputs(punkt);
        }
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
        refreshList();
    }

    public void onEventOccured(ActionEvent event) {
        Button button = (Button) event.getSource();

        // case switch
        switch(button.getId()) {
            case "addButton":               {
                System.out.println("addButton");
                addNewHighscore();
                refreshList();
                break;
            }

            case "saveButton":              {
                System.out.println("saveButton");
                saveHighscore();
                refreshList();
                break;
            }

            case "deleteButton":            {
                System.out.println("deleteButton");
                deleteHighscore();
                refreshList();
                break;
            }

            case "reloadButton":            {
                // for background seeds
                System.out.println("reloadButton");
                refreshList();
                break;
            }
            case "resetButton":             {
                System.out.println("resetButton");
                resetHighscore();
                refreshList();
            }

        }

    }


    public void rowClicked(){
        ObservableList<Highscore> singleRow;
        singleRow = crudTable.getSelectionModel().getSelectedItems();

        if(singleRow.get(0) instanceof Highscore){
            label.setText(modifyHighscore);
            temporaryHighscore = singleRow.get(0);
            getHighscoreDetails(temporaryHighscore);
        }
    }

    private void refreshList(){
        highscore = mysqlConnector.getHighscoreList();
        crudTable.getItems().setAll(highscore.subList(currentPageIndex * itemsPerPage, ((currentPageIndex * itemsPerPage + itemsPerPage <= highscore.size()) ? currentPageIndex * itemsPerPage + itemsPerPage : highscore.size())));
    }

    private void getHighscoreDetails(Highscore highscore) {
        nameField.setText(highscore.getName());
        punkteField.setText(String.valueOf(highscore.getPunkte()));
    }

    public void addNewHighscore(){
        String id = "1";
        String name = nameField.getText();
        Integer punkte = Integer.parseInt(punkteField.getText());
        String date = " ";
        Highscore newHighscore = new Highscore(id, name, punkte, date);
        mysqlConnector.insertHighscore(newHighscore);
        refreshList();
        hbox_input.setVisible(false);
        // after save data set score to 0
        Game.getInstance().getPlayer().setScore(0);

        // TODO show message crud successfully
    }

    public void saveHighscore() {

        System.out.println(temporaryHighscore);
        String name = nameField.getText();
        Integer punkte = Integer.parseInt(punkteField.getText());
        temporaryHighscore.setName(name);
        temporaryHighscore.setPunkte(punkte);
        System.out.println(temporaryHighscore);
        mysqlConnector.updateHighscore(temporaryHighscore);
    }

    public void deleteHighscore() {
        mysqlConnector.deleteHighscore(temporaryHighscore);
    }

    public void resetHighscore() {
        mysqlConnector.resetHighscore();
    }

    public void show_inputs(String punkt) {

        // simple hack only here show inputs hbox
        hbox_input.setVisible(true);
        // bind punkt to label
        punkteField.setText(punkt);
        // TODO show message (position in highscore)
    }

    // determine pagecount for pagination
    private int getPageCount(int totalCount, int itemsPerPage) {
        float floatCount = Float.valueOf(totalCount) / Float.valueOf(itemsPerPage);
        int intCount = totalCount / itemsPerPage;
//        System.out.println("floatCount=" + floatCount + ", intCount=" + intCount);
        return ((floatCount > intCount) ? ++intCount : intCount);
    }
}
