package com.simpleproject.simpleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Dev
{
    @Autowired // field injection - not good - wont work for loose coupling
    @Qualifier("desktop")
    private Computer computer;

    

//    Dev(Laptop laptop)
//    {
//        this.laptop = laptop;
//    }

//    @Autowired
//    void setComputer(Computer computer)
//    {
//        this.computer = computer;
//    }
    public void build()
    {
        System.out.println("Dev is building");
        computer.compile();
    }
}
