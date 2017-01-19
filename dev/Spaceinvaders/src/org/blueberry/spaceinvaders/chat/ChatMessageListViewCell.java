package org.blueberry.spaceinvaders.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import org.apache.commons.lang.StringEscapeUtils;
import java.text.SimpleDateFormat;



/**
 * Created by mhertel on 19.01.17.
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

			label1.setStyle("-fx-text-fill:" + color);
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			long unixTimestamp = new Long(message.getTime());
			String strtime = formatter.format(unixTimestamp);
			label3.setText(strtime);

			String afterDecoding = StringEscapeUtils.unescapeHtml(message.getText());
			label5.setText(afterDecoding);



			setText(null);
			setGraphic(gridPane);
		}

	}

}


//chatListView.setCellFactory(list -> {
//		ListCell<MessageTypeMessage> cell = new ListCell<MessageTypeMessage>() {
//@Override
//protected void updateItem(MessageTypeMessage item, boolean empty) {
////                                    System.out.println(item);
//		super.updateItem(item, empty);
//
//		setText(empty ? null : item.toChatString());
////                                    setText();
////                                    List<String> message = new ArrayList<String>();
////                                    // this.setStyle("-fx-text-fill: " + color);
////                                    message.add(item.getAuthor());
////                                    // this.setStyle("-fx-text-fill: black");
////                                    message.add(item.getTime());
////                                    message.add(item.getText());
////                                    for (int i=0; i<message.size(); i++) {
////                                        Label lbl = new Label(message.get(i));
////                                        lbl.setStyle("-fx-text-fill: " + item.getColor());
////                                        setText(lbl.toString());
////                                    }
////                                    // nur getAuthor soll textfill bekommen
////                                    System.out.println(item.getAuthor());
////                                    // black
////                                    System.out.println(item.getTime());
////                                    System.out.println(item.getText());
//
//		if (!isEmpty()){
//
//		String color = item.getColor();
//		if (color == null || color.isEmpty() || color.equals("null")) {
//		color = "black";
//		}
//		setStyle("-fx-text-fill: " + color);
//
//		}
//		}
//		};
//		return cell;
//		});
