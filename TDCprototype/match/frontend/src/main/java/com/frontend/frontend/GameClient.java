package com.frontend.frontend;

import com.frontend.frontend.model.Action;
import com.frontend.frontend.model.GameInstance;
import com.frontend.frontend.model.Packet;
import com.frontend.frontend.model.Pair;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class GameClient
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your ID: ");
        String myID = scanner.nextLine();

        System.out.print("Enter starting x and y coordinates: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        scanner.nextLine();

        GameInstance game = new GameInstance(myID, new Pair<>(x,y));

        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new JacksonJsonMessageConverter());
        String url = "ws://localhost:8080/ws";

        StompSession session = stompClient.connectAsync
        (
            url,
            /*
             * Callback handler
             * */
            new StompSessionHandlerAdapter()
            {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders)
                {
                    System.out.println("Connected to match server.");
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
        ("/match/public",
            new StompFrameHandler()
            {
                @Override
                public Type getPayloadType(StompHeaders headers)
                {
                    return Packet.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload)
                {
                    Packet p = (Packet) payload;

                    if (p.getAction() == Action.JOIN)
                    {
                        System.out.println("[SERVER] " + p.getID() + " joined at position "
                                + "(" + p.getFinalPosition().first + ","
                                + p.getFinalPosition().second + ")");
                        game.updateFromPacket(p);
                    }
                    else if (p.getAction() == Action.LEAVE)
                    {
                        System.out.println("[SERVER] " + p.getID() + " left");
                        game.updateFromPacket(p);
                    }
                    else if (p.getAction() == Action.PRIMARY)
                    {
                        System.out.println(p.getID() + " performs a primary attack from "
                                + "(" + p.getFinalPosition().first + ","
                                + p.getFinalPosition().second + ")");
                    }
                    else if (p.getAction() == Action.SECONDARY)
                    {
                        System.out.println(p.getID() + " performs a secondary attack from "
                                + "(" + p.getFinalPosition().first + ","
                                + p.getFinalPosition().second + ")");
                    }
                    else if (p.getAction() == Action.ULTIMATE)
                    {
                        System.out.println(p.getID() + " performs an ultimate attack from "
                                + "(" + p.getFinalPosition().first + ","
                                + p.getFinalPosition().second + ")");
                    }
                    else if (p.getAction() == Action.MOVE)
                    {
                        System.out.println(p.getID() + " moves to "
                                + "(" + p.getFinalPosition().first + ","
                                + p.getFinalPosition().second + ")");
                        game.updateFromPacket(p);
                    }
                }
            }
        );

        Packet joinPacket = new Packet(myID, new Pair<>(x,y), Action.JOIN);
        session.send("/app/game.joinGame", joinPacket);

//        System.out.println("Type messages. Type /quit to exit.");

        while (true)
        {
            int choice;
            while(true)
            {
                System.out.println
                (
                    "1. Primary Attack\n" +
                    "2. Secondary Attack\n" +
                    "3. Ultimate Attack\n" +
                    "4. Move\n" +
                    "5. Quit Game\n" +
                    "Select an action to perform: "
                );
                choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice)
                {
                    case 1:
                    {
                        Packet p = new Packet(myID, game.getMyCoordinates(), Action.PRIMARY);
                        session.send("/app/game.takeAction", p);
                        break;
                    }
                    case 2:
                    {
                        Packet p = new Packet(myID, game.getMyCoordinates(), Action.SECONDARY);
                        session.send("/app/game.takeAction", p);
                        break;
                    }
                    case 3:
                    {
                        Packet p = new Packet(myID, game.getMyCoordinates(), Action.ULTIMATE);
                        session.send("/app/game.takeAction", p);
                        break;
                    }
                    case 4:
                    {

                        System.out.print("Enter final x and y coordinates: ");
                        int newX = scanner.nextInt();
                        int newY = scanner.nextInt();
                        scanner.nextLine();
                        Packet p = new Packet(myID, new Pair<>(newX, newY), Action.MOVE);
                        session.send("/app/game.takeAction", p);
                        break;
                    }
                    case 5:
                    {
                        Packet p = new Packet(myID, null, Action.LEAVE);
                        session.send("/app/game.takeAction", p);
                        session.disconnect();
                        break;
                    }
                    default: break;
                }
                if(choice >= 1 && choice <= 5)
                {
                    break;
                }
            }
            if(choice == 5)
            {
                break;
            }
        }

        scanner.close();
        System.exit(0);




    }
}
