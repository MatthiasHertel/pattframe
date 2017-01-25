package org.blueberry.spaceinvaders.chat;

import org.apache.commons.lang.StringEscapeUtils;
import java.text.SimpleDateFormat;

/**
 * MessageTypeMessage handle für das Chatobjekttype Message
 */
public class MessageTypeMessage implements ChatObject{
    String time;
    String author;
    String color;
    String text;


    /**
     * Helper toString() Methode
     * @return
     */
    public String toChatString() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        long unixTimestamp = new Long(time);
        String strtime = formatter.format(unixTimestamp);
        String afterDecoding = StringEscapeUtils.unescapeHtml(text);
        return "" + author + '@' + strtime + ": " + afterDecoding;
    }

    /**
     * Handle Methode zum Hinzufuegen eines Chatitems zur chatHistory (List)
     * @param chat
     */
    public void handle(ChatModel chat) {
        chat.chatHistory.add(this);
    }

    /**
     * Helper toString() Methode
     * @return JSON String
     */
    @Override
    public String toString() {
        return "MessageTypeMessage{" +
                "time='" + time + '\'' +
                ", author='" + author + '\'' +
                ", color='" + color + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
