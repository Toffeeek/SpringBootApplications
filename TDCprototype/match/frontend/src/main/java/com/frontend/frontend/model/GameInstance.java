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
    @Setter
    private int myID;
    @Getter
    private Pair<Integer, Integer> myCoordinates;
    private Map<Integer, Pair<Integer, Integer>> enemyCoordinates = new HashMap<>();

    public GameInstance(int ID, Pair<Integer, Integer> startingPoint)
    {
        this.myID = ID;
        myCoordinates = startingPoint;
    }


    public void updateFromPacket(Packet p)
    {
        int packetID = p.getID();

        if (p.getAction() == Action.LEAVE)
        {
            enemyCoordinates.remove(packetID);
            return;
        }

        if (p.getFinalPosition() == null)
        {
            return;
        }

        if (myID == packetID)
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

        for(int i = 0; i < mapSize + 2; i++)
        {
            System.out.print("_");
        }
        System.out.println();
        for(int i = 0; i < mapSize; i++)
        {
            System.out.print("|");
            for(int j = 0; j < mapSize; j++)
            {
                if(myCoordinates.first == i && myCoordinates.second == j)
                {
                    System.out.print("M");
                }
                else
                {
                    boolean enemyPresent = false;
                    for(var coordinate : enemyCoordinates)
                    {
                        if(coordinate.first == i && coordinate.second == j)
                        {
                            enemyPresent = true;
                            break;
                        }
                    }
                    if(enemyPresent)
                    {
                        System.out.print("E");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println("|");
        }
        for(int i = 0; i < mapSize + 2; i++)
        {
            System.out.print("_");
        }
        System.out.println();
    }
}
