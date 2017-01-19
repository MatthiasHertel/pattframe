package org.blueberry.spaceinvaders.chat;

import java.util.Collection;

/**
 * Created by matthias on 15.01.17.
 */
public class MessageTypeHistory implements ChatObject{
    public Collection<MessageTypeMessage> messageTypeMessages;

    public Collection<MessageTypeMessage> getMessageTypeMessages() {
        return messageTypeMessages;
    }

    public void setMessageTypeMessages(Collection<MessageTypeMessage> messageTypeMessages) {
        this.messageTypeMessages = messageTypeMessages;
    }

    @Override
    public String toString() {
        return "MessageTypeHistory{" +
                "messageTypeMessages=" + messageTypeMessages +
                '}';
    }

    @Override
    public void handle(ChatModel chat) {
        for (MessageTypeMessage messageTypeMessage : messageTypeMessages) {
            messageTypeMessage.handle(chat);
        }
    }
}
