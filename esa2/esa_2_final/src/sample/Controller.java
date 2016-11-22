package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Controller {

    @FXML    private ImageView imageViewPferd1;
    @FXML    private ImageView imageViewPferd2;
    @FXML    private ImageView imageViewPferd3;
    @FXML    private ImageView imageViewPferd4;
    @FXML    private ImageView imageViewPferd5;
    @FXML    private ImageView imageViewPferd6;
    @FXML    private ImageView imageViewPferd7;
    @FXML    private ImageView imageViewPferd8;
    @FXML    private ImageView imageViewPferd9;
    @FXML    private ImageView imageViewPferd10;
    @FXML    private ImageView imageViewPferd11;
    @FXML    private ImageView imageViewPferd12;

    @FXML    private Label labelWurf1;
    @FXML    private Label labelWurf2;
    @FXML    private Label labelWurf3;
    @FXML    private Label labelWurf4;
    @FXML    private Label labelWurf5;
    @FXML    private Label labelWurf6;
    @FXML    private Label labelWurf7;
    @FXML    private Label labelWurf8;
    @FXML    private Label labelWurf9;
    @FXML    private Label labelWurf10;
    @FXML    private Label labelWurf11;
    @FXML    private Label labelWurf12;

    @FXML    private Label labelPunkte1;
    @FXML    private Label labelPunkte2;
    @FXML    private Label labelPunkte3;
    @FXML    private Label labelPunkte4;
    @FXML    private Label labelPunkte5;
    @FXML    private Label labelPunkte6;
    @FXML    private Label labelPunkte7;
    @FXML    private Label labelPunkte8;
    @FXML    private Label labelPunkte9;
    @FXML    private Label labelPunkte10;
    @FXML    private Label labelPunkte11;
    @FXML    private Label labelPunkte12;


    @FXML
    private void handleButtonAction(ActionEvent event){

        System.out.println("Starte Spiel...");

        Spiel spiel = Spiel.getInstance();

        for(int i = 0; i < spiel.getMaxSpieler(); i++){
            spiel.addSpieler(new Spieler());
        }

        imageViewPferd1.xProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd2.xProperty().bind(spiel.getMySpieler().get(1).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd3.xProperty().bind(spiel.getMySpieler().get(2).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd4.xProperty().bind(spiel.getMySpieler().get(3).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd5.xProperty().bind(spiel.getMySpieler().get(4).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd6.xProperty().bind(spiel.getMySpieler().get(5).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd7.xProperty().bind(spiel.getMySpieler().get(6).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd8.xProperty().bind(spiel.getMySpieler().get(7).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd9.xProperty().bind(spiel.getMySpieler().get(8).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd10.xProperty().bind(spiel.getMySpieler().get(9).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd11.xProperty().bind(spiel.getMySpieler().get(10).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd12.xProperty().bind(spiel.getMySpieler().get(11).getPferd().positionProperty().multiply(20).negate());

        labelPunkte1.textProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty().asString());
        labelPunkte2.textProperty().bind(spiel.getMySpieler().get(1).getPferd().positionProperty().asString());
        labelPunkte3.textProperty().bind(spiel.getMySpieler().get(2).getPferd().positionProperty().asString());
        labelPunkte4.textProperty().bind(spiel.getMySpieler().get(3).getPferd().positionProperty().asString());
        labelPunkte5.textProperty().bind(spiel.getMySpieler().get(4).getPferd().positionProperty().asString());
        labelPunkte6.textProperty().bind(spiel.getMySpieler().get(5).getPferd().positionProperty().asString());
        labelPunkte7.textProperty().bind(spiel.getMySpieler().get(6).getPferd().positionProperty().asString());
        labelPunkte8.textProperty().bind(spiel.getMySpieler().get(7).getPferd().positionProperty().asString());
        labelPunkte9.textProperty().bind(spiel.getMySpieler().get(8).getPferd().positionProperty().asString());
        labelPunkte10.textProperty().bind(spiel.getMySpieler().get(9).getPferd().positionProperty().asString());
        labelPunkte11.textProperty().bind(spiel.getMySpieler().get(10).getPferd().positionProperty().asString());
        labelPunkte12.textProperty().bind(spiel.getMySpieler().get(11).getPferd().positionProperty().asString());

        labelWurf1.textProperty().bind(spiel.getMySpieler().get(0).wurfAnzahlPropertyProperty());
        labelWurf2.textProperty().bind(spiel.getMySpieler().get(1).wurfAnzahlPropertyProperty());
        labelWurf3.textProperty().bind(spiel.getMySpieler().get(2).wurfAnzahlPropertyProperty());
        labelWurf4.textProperty().bind(spiel.getMySpieler().get(3).wurfAnzahlPropertyProperty());
        labelWurf5.textProperty().bind(spiel.getMySpieler().get(4).wurfAnzahlPropertyProperty());
        labelWurf6.textProperty().bind(spiel.getMySpieler().get(5).wurfAnzahlPropertyProperty());
        labelWurf7.textProperty().bind(spiel.getMySpieler().get(6).wurfAnzahlPropertyProperty());
        labelWurf8.textProperty().bind(spiel.getMySpieler().get(7).wurfAnzahlPropertyProperty());
        labelWurf9.textProperty().bind(spiel.getMySpieler().get(8).wurfAnzahlPropertyProperty());
        labelWurf10.textProperty().bind(spiel.getMySpieler().get(9).wurfAnzahlPropertyProperty());
        labelWurf11.textProperty().bind(spiel.getMySpieler().get(10).wurfAnzahlPropertyProperty());
        labelWurf12.textProperty().bind(spiel.getMySpieler().get(11).wurfAnzahlPropertyProperty());

        spiel.startSpiel();
        spiel.clear();
    }

}
