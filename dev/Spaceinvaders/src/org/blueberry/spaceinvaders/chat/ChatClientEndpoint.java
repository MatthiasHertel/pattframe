package org.blueberry.spaceinvaders.chat;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;

import java.net.URI;
import javax.websocket.*;

/**
 * Created by matthias on 15.01.17.
 */
@ClientEndpoint
public class ChatClientEndpoint {
    private Session userSession = null;
    private MessageHandler messageHandler;

    public ChatClientEndpoint(final URI endpointURI) {

        try {
            // maybe needed for local cert
//			System.getProperties().put(SSLContextConfigurator.KEY_STORE_FILE, your_new_keystore_path);
//			System.getProperties().put(SSLContextConfigurator.TRUST_STORE_FILE, your_new_keystore_path);
//			System.getProperties().put(SSLContextConfigurator.KEY_STORE_PASSWORD, the_password_you_entered);
//			System.getProperties().put(SSLContextConfigurator.TRUST_STORE_PASSWORD, the_password_you_enterede);

            // for production connection
            final ClientManager client = ClientManager.createClient();
//			System.getProperties().put("javax.net.debug", "all"); //usefull to understand ssl problems
            SslEngineConfigurator sslEngineConfigurator = new SslEngineConfigurator(new SslContextConfigurator());
            sslEngineConfigurator.setHostVerificationEnabled(false); //skip host verification
            client.getProperties().put(ClientProperties.SSL_ENGINE_CONFIGURATOR, sslEngineConfigurator);
            client.connectToServer(this, endpointURI);

            //for development environment
//			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//			container.connectToServer(this, endpointURI);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(final Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(final Session userSession, final CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(final String message) {
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        }
    }

    public void addMessageHandler(final MessageHandler msgHandler) {
        messageHandler = msgHandler;
    }

    public void sendMessage(final String message) {
        userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}
