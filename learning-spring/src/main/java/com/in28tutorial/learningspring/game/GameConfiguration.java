package com.in28tutorial.learningspring.game;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration
{
    @Bean
    public GamingConsole game()
    {
        return new MarioGame();
    }

    @Bean
    public GameRunner gameRunner(GamingConsole game)
    {
        return new GameRunner(game);
    }

}
