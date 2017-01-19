package org.blueberry.spaceinvaders.chat;

/**
 * Created by matthias on 15.01.17.
 */
public class Color implements ChatObject{
    public String color;

    @Override
    public String toString() {
        return "Color{" +
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
    }
}
