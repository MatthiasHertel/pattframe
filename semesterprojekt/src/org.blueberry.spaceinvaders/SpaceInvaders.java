package org.blueberry.spaceinvaders1;

        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;

public class SpaceInvaders extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        Pane display = new Pane();
        Scene scene = new Scene(root, 800.0, 600.0);
        ImageView invader = new ImageView(new Image(String.valueOf(getClass().getResource("../../../images/invader.png"))));

        AnchorPane.setTopAnchor(display, 10.0);
        AnchorPane.setLeftAnchor(display, 10.0);
        AnchorPane.setRightAnchor(display, 10.0);

        display.setPrefHeight(400.0);
        display.setStyle("-fx-background-color: black");
        display.getChildren().add(invader);

        root.getChildren().add(display);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Invaders");
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("../../../images/beuth-logo.png"))));
        primaryStage.show();
    }

    public static void main(String args[]) {
        SpaceInvaders.launch(args);
    }
}
