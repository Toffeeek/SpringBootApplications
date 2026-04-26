package com.simplewebapp.simplewebapp.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Product
{
    private int ID;
    private String name;
    private float price;
}

//{
//        "ID": 101,
//        "name": "Tawfiq",
//        "price": 129.0
//}
