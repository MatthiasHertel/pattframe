package org.blueberry.spaceinvaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import org.blueberry.spaceinvaders.controller.ScreensController;

import java.util.Properties;

/**
 * Created by matthias on 13.12.16.
 */

public class SpaceInvaders extends Application {
    private static final Properties settings = new Properties();

    public static String screen1ID = "main";
    public static String screen1File = "/views/Screen_01_WelcomeView.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "/views/Screen_02_GameplayView.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "/views/Screen_03_HighscoreView.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {

        settings.load(getClass().getResourceAsStream("/config/application.properties"));

        ScreensController mainContainer = new ScreensController();

        mainContainer.loadScreen(SpaceInvaders.screen1ID, SpaceInvaders.screen1File);
        mainContainer.loadScreen(SpaceInvaders.screen2ID, SpaceInvaders.screen2File);
        mainContainer.loadScreen(SpaceInvaders.screen3ID, SpaceInvaders.screen3File);

        mainContainer.setScreen(SpaceInvaders.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String args[]) {
        SpaceInvaders.launch(args);
    }
    public static String getSettings(String property){
        return settings.getProperty(property);
    }
}

