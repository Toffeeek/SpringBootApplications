package com.simplewebapp.simplewebapp.service;

import com.simplewebapp.simplewebapp.model.Product;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Getter
public class ProductService
{
    List<Product> products = new ArrayList<>(Arrays.asList
            (
                    new Product(101, "Iphone11", 5000),
                    new Product(102, "Iphone12", 5000),
                    new Product(103, "Iphone13", 5000),
                    new Product(104, "Iphone14", 5000),
                    new Product(105, "Iphone15", 5000)
            ));

    public Product getProductById(int ID)
    {
        for(Product p : products)
        {
            if(p.getID() == ID)
                return p;
        }
        return null;
    }

    public void listProduct(Product p)
    {
        products.add(p);
    }

    public void updateProduct(Product up_p)
    {
        int idx = -1;
        for(int i = 0; i < products.size(); i++)
        {
            if(products.get(i).getID() == up_p.getID())
            {
                idx = i;
            }
        }

        if(idx != -1)
        {
            products.set(idx, up_p);
        }
    }

    public void deleteProductById(int id)
    {
        int idx = -1;
        for(int i = 0; i < products.size(); i++)
        {
            if(products.get(i).getID() == id)
            {
                idx = i;
            }
        }

        if(idx != -1)
        {
            products.remove(idx);
        }
    }
}


