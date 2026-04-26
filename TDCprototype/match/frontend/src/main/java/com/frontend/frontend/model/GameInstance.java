package com.frontend.frontend.model;

import com.frontend.frontend.GameClient;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameInstance
{
    private final int mapSize = 10;
    private String myID;
    @Getter
    private Pair<Integer, Integer> myCoordinates;
    private Map<String, Pair<Integer, Integer>> enemyCoordinates = new HashMap<>();

    public GameInstance(String ID, Pair<Integer, Integer> startingPoint)
    {
        this.myID = ID;
        myCoordinates = startingPoint;
    }

    public void updateFromPacket(Packet p)
    {
        String packetID = p.getID();

        if (p.getAction() == Action.LEAVE)
        {
            enemyCoordinates.remove(packetID);
            return;
        }

        if (p.getFinalPosition() == null)
        {
            return;
        }

        if (myID.equals(packetID))
        {
            myCoordinates = p.getFinalPosition();
        }
        else
        {
            enemyCoordinates.put(packetID, p.getFinalPosition());
        }
    }

    public void drawMap()
    {
        ArrayList<Pair<Integer, Integer>> enemyCoordinates = new ArrayList<>();
        for(var entry : this.enemyCoordinates.entrySet())
        {
            enemyCoordinates.add(entry.getValue());
        }

        for(int i = 0; i < mapSize; i++)
        {
            for(int j = 0; j < mapSize; j++)
            {
                
            }
        }
    }


}
