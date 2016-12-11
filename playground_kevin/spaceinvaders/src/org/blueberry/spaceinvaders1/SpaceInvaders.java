package org.blueberry.spaceinvaders1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SpaceInvaders extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../../views/SpaceInvaders.fxml"));
        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Space Invaders");
//        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("../../../images/beuth-logo.png"))));
        primaryStage.getIcons().add(new Image("images/beuth-logo.png"));

        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
