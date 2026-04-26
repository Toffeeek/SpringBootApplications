package com.in28tutorial.learningspring.game;

public class MarioGame implements  GamingConsole
{
    public void up()
    {
        System.out.println("MARIO JUMPS");
    }
    public void down()
    {
        System.out.println("MARIO GOES INTO A PIPE");
    }
    public void left()
    {
        System.out.println("MARIO GOES BACK");
    }
    public void right()
    {
        System.out.println("MARIO GOES FORWARD");
    }
}
