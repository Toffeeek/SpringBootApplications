package com.simplewebsocket.simplewebsocket.chat;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController
{
    /**The annotation @MessageMapping determines which method is called when a client sends
     * a message to the server. The client sends to /app/chat.sendMessage
     * (/app is the prefix set in WebSocketConfig, /chat.sendMessage is the
     * mapping defined here).
     * The returned ChatMessage object is then broadcast by @SendTo to every
     * client currently subscribed to /topic/public.
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage msg)
    {
        return msg;
    }

    /**
     * Handles a new user joining the chat.
     *
     * @param msg the incoming ChatMessage payload converted from JSON.
     *            It contains the sender name, which we save in the WebSocket
     *            session attributes so we can identify this user later,
     *            for example when they disconnect.
     *
     * @param headerAccessor provided automatically by Spring. It gives access
     *                       to the STOMP/WebSocket message headers and the
     *                       current WebSocket session attributes.
     *
     * @return the incoming ChatMessage object, which is then broadcast to all
     *         clients subscribed to "/topic/public" because of @SendTo.
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage msg, SimpMessageHeaderAccessor headerAccessor)
    {
        // Save the username in this WebSocket session's attributes map.
        headerAccessor.getSessionAttributes().put("username", msg.getSender());

        return msg;
    }

}
