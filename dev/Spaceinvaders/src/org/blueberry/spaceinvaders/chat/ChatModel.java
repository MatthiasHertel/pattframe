package org.blueberry.spaceinvaders.chat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by matthias on 15.01.17.
 */
public class ChatModel {
    public final BooleanProperty connected = new SimpleBooleanProperty(false);
    public final BooleanProperty readyToChat = new SimpleBooleanProperty(false);
    public final ObservableList<String> chatHistory = FXCollections.observableArrayList();
    public final StringProperty currentMessage = new SimpleStringProperty();
    public final StringProperty userName = new SimpleStringProperty();
    public final StringProperty color = new SimpleStringProperty();
}
