package com.msh.WorkoutGameClient.logic;

import com.msh.WorkoutGameClient.config.MyStompSessionHandler;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class WebSocketManager {

    //private static final String URL = "ws://localhost:8082/action";
    private WebSocketClient client;
    private WebSocketStompClient stompClient;
    String sessionId;

    public WebSocketManager() {
        client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    public void establishConnection(String name, String URL) {
        StompSessionHandler sessionHandler = new MyStompSessionHandler(name);
        try {
            sessionId = stompClient.connect(URL, sessionHandler).get().getSessionId();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
