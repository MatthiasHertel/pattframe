package com.oliverhuckfeldt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/start.fxml"));
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass()
                .getResource("/stylesheets/main.css")
                .toString());

        stage = primaryStage;
        stage.setScene(scene);
        stage.setTitle("Switch Scene Demo");
        stage.show();
    }
}
