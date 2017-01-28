package highscore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by matthias on 08.12.16.
 */

public class Controller implements Initializable{
    @FXML
    TableView<Highscore> crudTable;

    @FXML private TableColumn nameColumn;
    @FXML private TableColumn idColumn;
    @FXML private TableColumn punkteColumn;

    private ObservableList<Highscore> highscore = FXCollections.observableArrayList();

    @FXML private TextField nameField;
    @FXML private TextField punkteField;


    @FXML private Label label;

    private String addHighscore = "Neuer Eintrag";
    private String modifyHighscore = "Bearbeite Eintrag";

    private String addingButtonID = "addButton";
    private String savingButtonID = "saveButton";
    private String deletingButtonID = "deleteButton";
    private String reloadButtonID = "reloadButton";

    private DatabaseConnector mysqlConnector;

    private Highscore temporaryHighscore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mysqlConnector = new DatabaseConnector();
        mysqlConnector.launchConnection();

        idColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Highscore, String>("name"));
        punkteColumn.setCellValueFactory(new PropertyValueFactory<Highscore, Integer>("punkte"));
        label.setText(addHighscore);

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
        Highscore newHighscore = new Highscore(id, name, punkte);
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
