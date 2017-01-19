package org.blueberry.spaceinvaders.chat;

/**
 * Created by matthias on 15.01.17.
 */
public class MessageTypeColor implements ChatObject{
    public String color;

    @Override
    public String toString() {
        return "MessageTypeColor{" +
                "color='" + color + '\'' +
                '}';
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void handle(ChatModel chat) {
        // TODO
        chat.color.setValue(color);
    }
}
