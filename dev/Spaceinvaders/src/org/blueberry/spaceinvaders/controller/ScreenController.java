package org.blueberry.spaceinvaders.controller;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

/**
 * ScreenController
 */
public class ScreenController extends StackPane {

    private Node screen;

    public ScreenController() {
        super();
    }


    public Node getScreen() {
        return screen;
    }

    public boolean loadScreen(String resource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            this.screen = fxmlLoader.load();
            int paddingTop = (int) (Screen.getPrimary().getBounds().getHeight() - 600) / 2;
            int paddingRight = (int) (Screen.getPrimary().getBounds().getWidth() - 1024) / 2;
            screen.setStyle("-fx-padding:" + paddingTop + "px " + paddingRight +"px");

            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    public boolean setScreen(final String resource) {
        if (!loadScreen(resource)) {
            System.out.println("ScreenController: view konnte nicht geladen werden - " + resource);
        }
        if (!getChildren().isEmpty()) {
            getChildren().remove(0);
            getChildren().add(0, screen);
        } else {
            getChildren().add(screen);
        }
//            final DoubleProperty opacity = opacityProperty();
//            if (!getChildren().isEmpty()) {    //if there is more than one screen
//                Timeline fade = new Timeline(
//                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
//                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
//                            @Override
//                            public void handle(ActionEvent t) {
//                                getChildren().remove(0);                    //remove the displayed screen
//                                getChildren().add(0, screens.get(name));     //add the screen
//                                Timeline fadeIn = new Timeline(
//                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
//                                        new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
//                                fadeIn.play();
//                            }
//                        }, new KeyValue(opacity, 0.0)));
//                fade.play();
//
//            } else {
//                setOpacity(0.0);
//                getChildren().add(screens.get(name));       //no one else been displayed, then just show
//                Timeline fadeIn = new Timeline(
//                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
//                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
//                fadeIn.play();
//            }
        return true;



        /*Node screenToRemove;
         if(screens.get(name) != null){   //screen loaded
         if(!getChildren().isEmpty()){    //if there is more than one screen
         getChildren().add(0, screens.get(name));     //add the screen
         screenToRemove = getChildren().get(1);
         getChildren().remove(1);                    //remove the displayed screen
         }else{
         getChildren().add(screens.get(name));       //no one else been displayed, then just show
         }
         return true;
         }else {
         System.out.println("screen hasn't been loaded!!! \n");
         return false;
         }*/
    }

}