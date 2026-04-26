package com.simplewebapp.simplewebapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BingChilling
{
    @RequestMapping("/bingchilling")
    public String bingChilling()
    {
        return "bing chilling huh bing chilling";
    }
}
