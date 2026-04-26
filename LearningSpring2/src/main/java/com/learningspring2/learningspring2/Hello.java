package com.learningspring2.learningspring2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello
{
    @RequestMapping("/")
    public String greet()
    {
        return "Hello world - tawfiq ahmed";
    }

}
