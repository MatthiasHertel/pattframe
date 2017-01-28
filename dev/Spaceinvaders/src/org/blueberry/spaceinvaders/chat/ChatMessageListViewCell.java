package org.blueberry.spaceinvaders.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import org.apache.commons.lang.StringEscapeUtils;
import java.text.SimpleDateFormat;



/**
 * Klasse zur Darstellung einer Listzelle
 */
public class ChatMessageListViewCell extends ListCell<MessageTypeMessage>{
	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private Label label3;

	@FXML
	private Label label4;

	@FXML
	private Label label5;


	@FXML
	private GridPane gridPane;

	private FXMLLoader mLLoader;

	/**
	 * Override für ListCellFactorymethode
	 * @param message
	 * @param empty
	 */
	@Override
	protected void updateItem(MessageTypeMessage message, boolean empty) {
		super.updateItem(message, empty);

		if(empty || message == null) {

			setText(null);
			setGraphic(null);

		} else {
			if (mLLoader == null) {
				mLLoader = new FXMLLoader(getClass().getResource("/views/ChatMessageListCell.fxml"));
				mLLoader.setController(this);

				try {
					mLLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			label1.setText(message.getAuthor());
			String color = message.getColor();
			// set default color wenn der socketserver keine farbe mehr liefert
			if (color == null) {
				color = "black";
			}

			label1.setStyle("-fx-text-fill:" + color + "; -fx-font-weight: bold;");
            label1.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			long unixTimestamp = new Long(message.getTime());
			String strtime = formatter.format(unixTimestamp);
			label3.setText(strtime);
            label3.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));

			String afterDecoding = StringEscapeUtils.unescapeHtml(message.getText());
			label5.setText(afterDecoding);
            label5.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));



			setText(null);
			setGraphic(gridPane);
		}

	}

}