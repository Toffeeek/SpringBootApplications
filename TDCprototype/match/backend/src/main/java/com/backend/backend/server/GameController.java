package com.backend.backend.server;


import com.backend.backend.model.Packet;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class GameController
{
    @MessageMapping("/game.takeAction")
    @SendTo("/match/public")
    public Packet takeAction(@Payload Packet p)
    {
        return p;
    }

    @MessageMapping("/game.joinGame")
    @SendTo("/match/public")
    public Packet addPlayer(@Payload Packet p, SimpMessageHeaderAccessor headerAccessor)
    {
        headerAccessor.getSessionAttributes().put("ID", p.getID());
        return p;
    }
}
