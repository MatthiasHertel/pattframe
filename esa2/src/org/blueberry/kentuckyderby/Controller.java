package org.blueberry.kentuckyderby;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class Controller {

    @FXML
    TextField numberOfPlayersText;

    @FXML
    Region racetrack;

    public Controller() {

    }

    @FXML
    private void startGame(ActionEvent event) {
        int numberOfPlayers = Integer.parseInt(numberOfPlayersText.getText());
        Game game = Game.getInstance();
        game.setNumberOfPlayers(numberOfPlayers);
        game.run();
    }
}
