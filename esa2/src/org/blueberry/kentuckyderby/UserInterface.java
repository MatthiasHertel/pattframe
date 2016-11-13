package org.blueberry.kentuckyderby;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class UserInterface {

    private AnchorPane root;
    private Image icon;
    private String title;
    private double width, height;

    public void construct(Configuration config) {
        width = Double.parseDouble(config.getItem("window.width"));
        height = Double.parseDouble(config.getItem("window.height"));
        title = config.getItem("application.title");
        icon = new Image(config.getItem("window.icon"));

        root = new AnchorPane();
        Region raceTrack = new Region();
        AnchorPane.setTopAnchor(raceTrack, 10.0);
        AnchorPane.setLeftAnchor(raceTrack, 10.0);
        AnchorPane.setRightAnchor(raceTrack, 10.0);

        root.getChildren().add(raceTrack);
        String style = "-fx-background-image: url(" + config.getItem("racetrack.background-image") + "); -fx-background-repeat: repeat-x;";
        raceTrack.setStyle(style);
        raceTrack.setPrefHeight(400.0);
    }

    public void assemble(Stage stage) {
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.getIcons().add(icon);
        stage.show();
    }
}
