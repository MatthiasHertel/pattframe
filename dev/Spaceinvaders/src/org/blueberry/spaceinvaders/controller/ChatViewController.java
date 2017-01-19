package org.blueberry.spaceinvaders.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.chat.*;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * ChatViewController-Klasse
 */
public class ChatViewController implements Initializable {

    @FXML
    private Button connectButton;

    @FXML
    private TextField userNameTextfield;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button chatButton;


    @FXML
    private ListView<MessageTypeMessage> chatListView;

    private final ChatModel model = new ChatModel();

    private ChatClientEndpoint clientEndPoint;

    /**
     * Inizialisiert die Controller-Klasse.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model.userName.bindBidirectional(userNameTextfield.textProperty());
        model.readyToChat.bind(model.userName.isNotEmpty());
//        connectButton.disableProperty().bind(model.readyToChat.not());
        chatButton.disableProperty().bind(model.connected.not());
        messageTextField.disableProperty().bind(model.connected.not());
        messageTextField.textProperty().bindBidirectional(model.currentMessage);

        chatListView.setItems(model.chatHistory);
        messageTextField.setOnAction(event -> {
            handleSendMessage();
        });
        chatButton.setOnAction(evt -> {
            handleSendMessage();
        });
        connectButton.setOnAction(evt -> {
            try {
                // for production environment (wss required)
                clientEndPoint = new ChatClientEndpoint(new URI("wss://mhertel.de:1337"));
                // for development environment (ws)
//				clientEndPoint = new ChatClientEndpoint(new URI("ws://localhost:1337"));
                clientEndPoint.addMessageHandler(responseString -> {
                    Platform.runLater(() -> {
                        System.out.println(responseString);
                        ChatObject chatObject = getChatObject(responseString);
                        chatObject.handle(model);
                        userNameLabel.setStyle("-fx-text-fill:" + model.color.get());
                        chatListView.scrollTo(model.chatHistory.size());
                        chatListView.setCellFactory(list -> {
                            ListCell<MessageTypeMessage> cell = new ListCell<MessageTypeMessage>() {
                                @Override
                                protected void updateItem(MessageTypeMessage item, boolean empty) {
//                                    System.out.println(item);
                                    super.updateItem(item, empty);
                                    setText(empty ? null : item.toChatString());
                                    if (!isEmpty() && item.getColor() != null) {
                                        String color = item.getColor();
                                        if (color.isEmpty() || color.equals("null")) {
                                           color = "black";
                                        }
//                                        System.out.println(color);
                                        this.setStyle("-fx-text-fill: " + color);
                                    }
//                                    if()
//                                    System.out.println(item.getColor());
//                                     String currentcolor fetch here
//                                     posible colors from socket server 'red', 'green', 'blue', 'magenta', 'purple', 'plum', 'orange'
//                                    if (!isEmpty()) {
//                                        if(itemasd.contains("(red)"))
//                                            this.setStyle("-fx-text-fill: red");
//                                        if(item.contains("(green)"))
//                                            this.setStyle("-fx-text-fill: green");
//                                        if(item.contains("(blue)"))
//                                            this.setStyle("-fx-text-fill: blue");
//                                        if(item.contains("(magenta)"))
//                                            this.setStyle("-fx-text-fill: magenta");
//                                        if(item.contains("(purple)"))
//                                            this.setStyle("-fx-text-fill: purple");
//                                        if(item.contains("(plum)"))
//                                            this.setStyle("-fx-text-fill: plum");
//                                        if(item.contains("(orange)"))
//                                            this.setStyle("-fx-text-fill: orange");
//                                         the color information is within the to chatstring method of the messagetype
//                                        this.setText(item.substring(item.lastIndexOf(")")+1));
//                                    }
                                }
                            };
                            return cell;
                        });
                    });
                });
                clientEndPoint.sendMessage(model.userName.getValueSafe());
                connectButton.setDisable(true);
                userNameTextfield.setDisable(true);
                model.connected.set(true);
                userNameLabel.textProperty().setValue(model.userName.getValue());
            } catch (Exception e) {
                showDialog("Error: " + e.getMessage());
                System.out.println(e.getMessage());
            }

        });

    }


    private void handleSendMessage() {
        clientEndPoint.sendMessage(model.currentMessage.get());
        model.currentMessage.set("");
        messageTextField.requestFocus();
    }

    private void showDialog(final String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        VBox box = new VBox();
        box.getChildren().addAll(new Label(message));
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5));
        dialogStage.setScene(new Scene(box));
        dialogStage.show();
    }

    public static ChatObject getChatObject(String json) {
        // wenn typ color -> ColorObject
        // wenn text -> MessageObject
        // wenn history -> HistoryObject
        Gson gson = new Gson();
        com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
        com.google.gson.JsonObject jo = (com.google.gson.JsonObject)jsonParser.parse(json);
        String msgType = jo.get("type").getAsString();

        com.google.gson.JsonElement data = jo.get("data");
        switch (msgType) {
            case "history": {
                Type collectionMessagesType = new TypeToken<Collection<MessageTypeMessage>>(){}.getType();
                Collection<MessageTypeMessage> messageTypeMessages = gson.fromJson(data, collectionMessagesType);
                MessageTypeHistory messageTypeHistory = new MessageTypeHistory();
                messageTypeHistory.messageTypeMessages = messageTypeMessages;
                return messageTypeHistory;
            }
            case "color": {
                String colorname = data.getAsString();
                MessageTypeColor messageTypeColor = new MessageTypeColor();
                messageTypeColor.color = colorname;
                return messageTypeColor;
            }
            case "message": {
                MessageTypeMessage msg = gson.fromJson(data, MessageTypeMessage.class);
                return msg;

            }
            default: throw new RuntimeException("unknown messagetype: " + msgType);
        }
    }

    /**
     * Wechselt zur Welcome-View
     * @param event
     */
    @FXML
    private void goToScreenWelcomeView(ActionEvent event) {
        SpaceInvaders.setScreen("WelcomeView");
    }
}
