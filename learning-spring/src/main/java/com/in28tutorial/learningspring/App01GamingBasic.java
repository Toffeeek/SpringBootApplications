package com.in28tutorial.learningspring;

import com.in28tutorial.learningspring.game.GameRunner;
import com.in28tutorial.learningspring.game.MarioGame;
import com.in28tutorial.learningspring.game.PacmanGame;
import com.in28tutorial.learningspring.game.SuperContraGame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class App01GamingBasic
{

    public static void main(String[] args)
    {
        // object creation
        var game = new PacmanGame();

        // obj creation + wiring dependencies
        // game is a dependency of GameRunner
        var gameRunner = new GameRunner(game);


        gameRunner.run();
    }

}
