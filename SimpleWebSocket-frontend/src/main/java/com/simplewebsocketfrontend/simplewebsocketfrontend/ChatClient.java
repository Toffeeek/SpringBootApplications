package com.simplewebsocketfrontend.simplewebsocketfrontend;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ChatClient
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());

        stompClient.setMessageConverter(new JacksonJsonMessageConverter());
        String url = "ws://localhost:8080/ws";

        StompSession session = stompClient.connectAsync
        (
            url,
            new StompSessionHandlerAdapter()
            {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders)
                {
                    System.out.println("Connected to chat server.");
                }

                @Override
                public void handleException
                (
                    StompSession session,
                    StompCommand command,
                    StompHeaders headers,
                    byte[] payload,
                    Throwable exception
                )
                {
                    System.out.println("STOMP error: " + exception.getMessage());
                }

                @Override
                public void handleTransportError(StompSession session, Throwable exception)
                {
                    System.out.println("Transport error: " + exception.getMessage());
                }
            }
        ).get();
        session.subscribe
        ("/topic/public",
            new StompFrameHandler()
            {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return ChatMessage.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload)
                {
                    ChatMessage message = (ChatMessage) payload;

                    if (message.getType() == MessageType.JOIN)
                    {
                        System.out.println("[SERVER] " + message.getSender() + " joined");
                    }
                    else if (message.getType() == MessageType.LEAVE) {

                        System.out.println("[SERVER] " + message.getSender() + " left");
                    }
                    else if (message.getType() == MessageType.CHAT)
                    {
                        System.out.println(message.getSender() + ": " + message.getContent());
                    }
                }
            }
        );

        ChatMessage joinMessage = new ChatMessage(null, username, MessageType.JOIN);
        session.send("/app/chat.addUser", joinMessage);

        System.out.println("Type messages. Type /quit to exit.");

        while (true)
        {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("/quit"))
            {
                session.disconnect();
                break;
            }

            ChatMessage chatMessage = new ChatMessage(input, username, MessageType.CHAT);
            session.send("/app/chat.sendMessage", chatMessage);
        }

        scanner.close();
        System.exit(0);
    }

}