package org.blueberry.spaceinvaders.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        connectButton.disableProperty().bind(
                Bindings.or(
                        model.readyToChat.not(),
                        model.connected));
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
                        String color = model.color.get();
                        if (color == null) {
                            color = "black";
                        }
                        userNameLabel.setStyle("-fx-text-fill:" + color);
                        chatListView.scrollTo(model.chatHistory.size());
                        chatListView.setCellFactory(chatListView -> new ChatMessageListViewCell());
                    });
                });
                clientEndPoint.sendMessage(model.userName.getValueSafe());
                userNameTextfield.setDisable(true);
                model.connected.set(true);
                userNameLabel.textProperty().setValue(model.userName.getValue());
            } catch (Exception e) {
                SpaceInvaders.showDialog("Error in Class: " + this.getClass().getSimpleName() + ".\n" + e.getMessage());
                System.out.println(e.getMessage());
            }

        });

    }


    private void handleSendMessage() {
        clientEndPoint.sendMessage(model.currentMessage.get());
        model.currentMessage.set("");
        messageTextField.requestFocus();
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
                String colorname = "black";
                try {
                    colorname = data.getAsString();
                } catch(NullPointerException e) {
                    System.out.println(e.getMessage());
                }
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
