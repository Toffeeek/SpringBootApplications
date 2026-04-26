package com.backend.backend.model;


import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameInstance
{
    private final int mapSize = 10;
    private Pair<Integer, Integer>[] playerCoordinates = new Pair[2];
}
