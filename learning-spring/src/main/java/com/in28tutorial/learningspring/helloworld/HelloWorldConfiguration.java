package com.in28tutorial.learningspring.helloworld;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

record Address(String firstLine, String city) {}
record Person(String name, int age, Address address) {}


@Configuration
public class HelloWorldConfiguration
{
    @Bean
    public String firstLine()
    {
        return "Farmgate";
    }

    @Bean
    public String city()
    {
        return "Dhaka";
    }


    @Bean
    public Address address()
    {
        return new Address(firstLine(), city());
    }

    @Bean
    public String name() {
        return "Tawfiq";
    }

    @Bean
    public int age() {
        return 22;
    }

    @Bean
    @Primary
    public Person person()
    {
        return new Person("Tawfiq", 22, new Address("Farmgate", "Dhaka"));
    }

    @Bean
    public Person personMethod(String name, int age, Address address)
    {
        return new Person(name, age, address);
    }
}