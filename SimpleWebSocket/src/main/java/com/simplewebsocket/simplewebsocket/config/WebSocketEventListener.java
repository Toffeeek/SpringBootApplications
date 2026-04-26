package com.simplewebsocket.simplewebsocket.config;


import com.simplewebsocket.simplewebsocket.chat.ChatMessage;
import com.simplewebsocket.simplewebsocket.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener
{
    /**
     * Used to manually send messages to WebSocket/STOMP destinations.
     *
     * In controller methods, @SendTo can automatically broadcast the return value.
     * But this class is an event listener, not a @MessageMapping controller method,
     * so we use messageTemplate.convertAndSend(...) to broadcast messages manually.
     *
     * @RequiredArgsConstructor generates a constructor for this final field.
     * Spring then uses constructor injection to provide the SimpMessageSendingOperations
     * bean, similar to @Autowired but through the constructor.
     */
    private final SimpMessageSendingOperations messageTemplate;

    /**
     * Called automatically when Spring publishes a SessionDisconnectEvent.
     *
     * @EventListener tells Spring that this method should listen for application events.
     * Because this method takes a SessionDisconnectEvent parameter, Spring calls it only
     * for disconnect events, not for every type of event.
     *
     * The event contains the message/headers for the disconnected WebSocket session.
     * We wrap the event message with StompHeaderAccessor so we can read the STOMP headers
     * and session attributes.
     *
     * The username was previously saved in the session attributes when the user joined:
     * sessionAttributes["username"] = senderName.
     *
     * If a username exists, we create a LEAVE message and broadcast it to "/topic/public"
     * so all remaining clients subscribed to that destination know that the user left.
     */
    @EventListener
    public void handleWebSocketListener(SessionDisconnectEvent event)
    {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor
                .getSessionAttributes()
                .get("username");

        if(username != null)
        {
            log.info("User disconnected: {}", username);

            var msg = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            messageTemplate.convertAndSend("/topic/public", msg);
        }
    }
}