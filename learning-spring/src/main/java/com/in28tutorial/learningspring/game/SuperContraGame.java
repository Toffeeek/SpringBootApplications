package com.in28tutorial.learningspring.game;

public class SuperContraGame implements GamingConsole
{
    public void up()
    {
        System.out.println("UP");
    }
    public void down()
    {
        System.out.println("SUPER CONTRA SITS");
    }
    public void left()
    {
        System.out.println("SUPER CONTRA GOES BACK");
    }
    public void right()
    {
        System.out.println("SUPER CONTRA SHOOTS");
    }
}
