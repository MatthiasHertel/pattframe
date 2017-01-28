package org.blueberry.spaceinvaders.chat;

/**
 * MessageTypeColor handle für das Chatobjekttype Farbe
 */
public class MessageTypeColor implements ChatObject{
    public String color;

    /**
     * Helper toString() Methode
     * @return JSON String
     */
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

    /**
     * Handle Methode zum setzen der Farbe
     * @param chat
     */
    @Override
    public void handle(ChatModel chat) {
        // TODO
        chat.color.setValue(color);
    }
}
