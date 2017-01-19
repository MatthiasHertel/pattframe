package org.blueberry.spaceinvaders.chat;

import java.util.Collection;

/**
 * Created by matthias on 15.01.17.
 */
public class History implements ChatObject{
    public Collection<Message> messages;

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "History{" +
                "messages=" + messages +
                '}';
    }

    @Override
    public void handle(ChatModel chat) {
        for (Message message : messages) {
            message.handle(chat);
        }
    }
}
