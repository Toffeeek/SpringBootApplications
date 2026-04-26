package com.in28tutorial.learningspring;

import com.in28tutorial.learningspring.game.GameConfiguration;
import com.in28tutorial.learningspring.game.GameRunner;
import com.in28tutorial.learningspring.game.GamingConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App03GamingBasicJava
{
    public static void main(String[] args)
    {
        try(var context = new AnnotationConfigApplicationContext(GameConfiguration.class))
        {
//            GameRunner runner = new GameRunner(context.getBean(GamingConsole.class));
            context.getBean(GameRunner.class).run();
//            context.getBean(GameRunner.class).run();
            context.getBean("gameRunner", GameRunner.class).run();


        }
    }
}
