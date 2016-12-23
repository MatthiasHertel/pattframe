package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    private String addHighscore = "Punkteanzahl: ";
    private String modifyHighscore = "Bearbeite Eintrag";

    private String addingButtonID = "addButton";
    private String savingButtonID = "saveButton";
    private String deletingButtonID = "deleteButton";
    private String reloadButtonID = "reloadButton";

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

        //Fetch data from gameplay
        punkteField.setText(new Integer(Game.getInstance().getPlayer().getScore()).toString());

        refreshList();
    }

    @FXML
    private Label punkte;

    @FXML
    private void goToScreen1(ActionEvent event){
        SpaceInvaders.setScreen("WelcomeView");
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
        SpaceInvaders.setScreen("GameplayView");
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
        crudTable.getItems().setAll(this.highscore);
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
}
