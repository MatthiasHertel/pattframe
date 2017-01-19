package org.blueberry.spaceinvaders.chat;

import org.apache.commons.lang.StringEscapeUtils;
import java.text.SimpleDateFormat;

/**
 * Created by matthias on 15.01.17.
 */
public class MessageTypeMessage implements ChatObject{
    String time;
    String author;
    String color;
    String text;



    public String toChatString() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        long unixTimestamp = new Long(time);
        String strtime = formatter.format(unixTimestamp);

        String afterDecoding = StringEscapeUtils.unescapeHtml(text);

        return "" + "(" + color + ")" + author + '@' + strtime + ": " + afterDecoding;
    }

    public void handle(ChatModel chat) {
        chat.chatHistory.add(this.toChatString());
    }

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
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
