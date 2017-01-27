package org.blueberry.spaceinvaders;

import com.google.gson.Gson;
import org.blueberry.spaceinvaders.chat.MessageTypeColor;
import org.blueberry.spaceinvaders.chat.MessageTypeHistory;
import org.blueberry.spaceinvaders.chat.MessageTypeMessage;
import org.blueberry.spaceinvaders.controller.ChatViewController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test zum Parsen des JSON-Strings in ein Objekt
 */
public class JsonTest {

    @Test
    public void testParseJsonColor() {
        Gson gson = new Gson();
        String json = "{\"type\":\"color\", \"data\":\"green\"}";
        MessageTypeColor color = (MessageTypeColor) ChatViewController.getChatObject(json);

        assertEquals(color.getColor(), "green");
    }

    @Test
    public void testParseJsonMessage() {
        Gson gson = new Gson();

        String json = "{\"type\":\"message\", \"data\":" +
                "{\"author\":\"Matthias\", \"time\":\"now\", \"text\":\"nur ein test\", \"color\":\"blue\"}}";
        MessageTypeMessage msg = (MessageTypeMessage) ChatViewController.getChatObject(json);

        assertEquals(msg.getAuthor(), "Matthias");
        assertEquals(msg.getTime(), "now");
        assertEquals(msg.getText(), "nur ein test");
        assertEquals(msg.getColor(), "blue");
    }

    @Test
    public void testParseJsonHistory() {
        Gson gson = new Gson();

        String json = 	"{\"type\":\"history\",\"data\":" +
                "[{\"author\":\"Matthias\", \"time\":\"1484170408205\", \"text\":\"nur ein test\", \"color\":\"blue\"}" +
                ",{\"author\":\"Matthias\", \"time\":\"1484170408205\", \"text\":\"noch ein test\", \"color\":\"blue\"}" +
                ",{\"author\":\"hans\", \"time\":\"1484170429125\", \"text\":\"und noch ein test\", \"color\":\"green\"}]}";

        MessageTypeHistory hist = (MessageTypeHistory) ChatViewController.getChatObject(json);

        assertEquals(hist.getMessageTypeMessages().size(), 3);

    }

}
