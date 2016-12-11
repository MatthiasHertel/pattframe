package org.blueberry.spaceinvaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SpaceInvaders extends Application {

    private static final Properties settings = new Properties();

    @Override
    public void start(Stage primaryStage) throws Exception {

//        settings.load(getClass().getResourceAsStream("../../../config/application.properties"));
        settings.load(getClass().getResourceAsStream("/config/application.properties"));

//        Parent root = FXMLLoader.load(getClass().getResource("../../../views/SpaaceInvaders.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/views/SpaceInvaders.fxml"));
        primaryStage.setScene(new Scene(root));
//        primaryStage.setFullScreen(true);

        primaryStage.setTitle("Space Invaders");
//        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("../../../images/beuth-logo.png"))));
        primaryStage.getIcons().add(new Image("/images/invader.png"));

        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }



    public static String getSettings(String property){
        return settings.getProperty(property);
    }
}



