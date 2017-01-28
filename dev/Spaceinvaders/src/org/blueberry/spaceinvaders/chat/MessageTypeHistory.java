package org.blueberry.spaceinvaders.chat;

import java.util.Collection;

/**
 * MessageTypeHistory handle für das Chatobjekttype History
 */
public class MessageTypeHistory implements ChatObject{
    public Collection<MessageTypeMessage> messageTypeMessages;

    public Collection<MessageTypeMessage> getMessageTypeMessages() {
        return messageTypeMessages;
    }

    public void setMessageTypeMessages(Collection<MessageTypeMessage> messageTypeMessages) {
        this.messageTypeMessages = messageTypeMessages;
    }

    /**
     * Helper toString() Methode
     * @return JSON String
     */
    @Override
    public String toString() {
        return "MessageTypeHistory{" +
                "messageTypeMessages=" + messageTypeMessages +
                '}';
    }

    /**
     * Handlemethode fuer jede Message einer History rufe handle der Message auf
     * @param chat
     */
    @Override
    public void handle(ChatModel chat) {
        for (MessageTypeMessage messageTypeMessage : messageTypeMessages) {
            messageTypeMessage.handle(chat);
        }
    }
}
