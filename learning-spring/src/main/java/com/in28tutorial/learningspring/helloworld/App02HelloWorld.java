package com.in28tutorial.learningspring.helloworld;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App02HelloWorld
{

    public static void main(String[] args)
    {

        try(var context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class))
        {
            // launch a spring application/context


    //        retrieving all the bean names

    //        Method: 1
    //        var beanDefs = new ArrayList<>(Arrays.asList(context.getBeanDefinitionNames()));
    //        for(String beanDef : beanDefs)
    //        {
//                System.out.println(beanDef);
    //        }

    //        Method: 2
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);



    //        configure the things we want the spring framework to manage - @Configuration class
    //        System.out.println(context.getBean("name"));
    //        System.out.println(context.getBean("age"));


    //        System.out.println(context.getBean("person"));
            System.out.println(context.getBean(Person.class));

    //        System.out.println(context.getBean("personMethod"));

        }

    }

}
