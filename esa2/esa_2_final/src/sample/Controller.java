package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML
    private ImageView imageViewPferd1;
    @FXML
    private ImageView imageViewPferd2;
    @FXML
    private ImageView imageViewPferd3;
    @FXML
    private ImageView imageViewPferd4;
    @FXML
    private ImageView imageViewPferd5;
    @FXML
    private ImageView imageViewPferd6;

    @FXML
    private ImageView imageViewPferd7;
    @FXML
    private ImageView imageViewPferd8;
    @FXML
    private ImageView imageViewPferd9;
    @FXML
    private ImageView imageViewPferd10;
    @FXML
    private ImageView imageViewPferd11;
    @FXML
    private ImageView imageViewPferd12;

    @FXML
    private Label labelWurf1;
    @FXML
    private Label labelWurf2;
    @FXML
    private Label labelWurf3;
    @FXML
    private Label labelWurf4;
    @FXML
    private Label labelWurf5;
    @FXML
    private Label labelWurf6;
    @FXML
    private Label labelWurf7;
    @FXML
    private Label labelWurf8;
    @FXML
    private Label labelWurf9;
    @FXML
    private Label labelWurf10;
    @FXML
    private Label labelWurf11;
    @FXML
    private Label labelWurf12;

    @FXML
    private Label labelPunkte1;
    @FXML
    private Label labelPunkte2;
    @FXML
    private Label labelPunkte3;
    @FXML
    private Label labelPunkte4;
    @FXML
    private Label labelPunkte5;
    @FXML
    private Label labelPunkte6;
    @FXML
    private Label labelPunkte7;
    @FXML
    private Label labelPunkte8;
    @FXML
    private Label labelPunkte9;
    @FXML
    private Label labelPunkte10;
    @FXML
    private Label labelPunkte11;
    @FXML
    private Label labelPunkte12;
    

    @FXML
    private void handleButtonAction(ActionEvent event){
        System.out.println("Starte Spiel...");

        Spiel spiel = Spiel.getInstance();

        for(int i = 0; i < spiel.getMaxSpieler(); i++){
            spiel.addSpieler(new Spieler(this, i));
        }

        spiel.startSpiel();
        spiel.clear();

//        System.out.println("Position ImageView 1: " + imageView1.getLayoutX());
//        imageViewPferd1.setLayoutX(700);
//        System.out.println("Position ImageView 1: " + imageView1.getLayoutX());
    }



    public void updateView(Spieler spieler){

        switch(spieler.getSpielerId() +1 ){
            case 1:
                labelWurf1.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte1.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd1.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 2:
                labelWurf2.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte2.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd2.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 3:
                labelWurf3.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte3.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd3.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 4:
                labelWurf4.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte4.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd4.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 5:
                labelWurf5.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte5.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd5.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 6:
                labelWurf6.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte6.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd6.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 7:
                labelWurf7.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte7.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd7.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 8:
                labelWurf8.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte8.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd8.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 9:
                labelWurf9.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte9.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd9.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 10:
                labelWurf10.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte10.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd10.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 11:
                labelWurf11.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte11.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd11.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
            case 12:
                labelWurf12.setText(Integer.toString(spieler.getWurfAnzahl()));
                labelPunkte12.setText(Integer.toString(spieler.getPferd().getPosition()));
                imageViewPferd12.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
                break;
        }
    }


}
