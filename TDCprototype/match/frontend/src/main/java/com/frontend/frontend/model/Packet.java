package com.frontend.frontend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Packet
{
    private String ID;
    private Pair<Integer, Integer> finalPosition;
    private Action action;
}