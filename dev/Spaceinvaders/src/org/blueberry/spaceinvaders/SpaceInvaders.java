package org.blueberry.spaceinvaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import org.blueberry.spaceinvaders.controller.ScreensController;

import java.util.Properties;

/**
 * Created by matthias hertel on 13.12.16.
 */

public class SpaceInvaders extends Application {
    private static final Properties settings = new Properties();

    private static ScreensController mainContainer = new ScreensController();

    @Override
    public void start(Stage primaryStage) throws Exception {

        settings.load(getClass().getResourceAsStream("/config/application.properties"));

        setScreen("WelcomeView");

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

    public static void setScreen(String viewName){
        mainContainer.unloadScreen("screen");
        mainContainer.loadScreen("screen", "/views/" + viewName + ".fxml");
        mainContainer.setScreen("screen");
    }
}

