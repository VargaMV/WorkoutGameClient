package com.msh.WorkoutGameClient.run;

import com.msh.WorkoutGameClient.config.MyStompSessionHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

@Component
public class ClientRunner implements CommandLineRunner {

    private static final String URL = "ws://localhost:8082/action";
    private String sessionId;

    @Override
    public void run(String... args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name: ");
        String name = scanner.nextLine();

        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler(name);
        sessionId = stompClient.connect(URL, sessionHandler).get().getSessionId();
    }
}
