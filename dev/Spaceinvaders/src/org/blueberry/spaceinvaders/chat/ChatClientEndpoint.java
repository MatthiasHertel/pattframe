package org.blueberry.spaceinvaders.chat;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;

import java.net.URI;
import javax.websocket.*;

/**
 * ClientEndpoint - Annotation Driven - Darstellung des WebsocketClients als POJO (PlainOldJavaObject)
 * OnOpen	        Darstellung einer Methode als Callback für öffnende Verbindungsevents.
 * OnMessage        Darstellung einer Methode als Callback für eingehende Nachrichten.
 * onClose	        Darstellung einer Methode als Callback für schliessende Verbindungsevents.
 * onError	        Darstellung einer Methode als Callback für jegliche Fehler. (nicht implementiert)
 */
@ClientEndpoint
public class ChatClientEndpoint {
    private Session userSession = null;
    private MessageHandler messageHandler;

    /**
     * Konstruktor Verbindungsaufbau
     * @param endpointURI
     */
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

    /**
     * Callback hook für Verbindungsaufbau (open) Events
     *
     * @param userSession
     *            die userSession die geöffnet wurde.
     */
    @OnOpen
    public void onOpen(final Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Callback hook für Verbindungsabbau (close) Events
     *
     * @param userSession
     *            die userSession welche geschlossen wird.
     * @param reason
     *            der Grund für das Schliessen der Verbindung
     */
    @OnClose
    public void onClose(final Session userSession, final CloseReason reason) {
        this.userSession = null;
    }

    /**
     * Callback hook für Nachrichten Events
     * Diese Methode wird aufgerufen wenn ein Client eine Nachricht sendet.
     *
     * @param message
     *            Die Text Nachricht (String build as JSON)
     */
    @OnMessage
    public void onMessage(final String message) {
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        }
    }

    /**
     * registriert den MesssageHandler
     *
     * @param msgHandler
     */
    public void addMessageHandler(final MessageHandler msgHandler) {
        messageHandler = msgHandler;
    }

    /**
     * Sendet eine Nachricht.
     *
     * @param message
     */
    public void sendMessage(final String message) {
        if (message == null) {
            userSession.getAsyncRemote().sendText("");
        } else {
            userSession.getAsyncRemote().sendText(message);
        }
    }

    /**
     * inner Interface MessageHandler (more readable and maintainabl - does not belong to globalscope)
     */
    public interface MessageHandler {
        void handleMessage(String message);
    }
}
