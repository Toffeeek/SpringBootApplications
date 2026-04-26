package com.simpleproject.simpleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SimpleProjectApplication {

    public static void main(String[] args)
    {
        ApplicationContext context = SpringApplication.run(SimpleProjectApplication.class, args);
        Dev obj = context.getBean(Dev.class);
        obj.build();
    }

}
