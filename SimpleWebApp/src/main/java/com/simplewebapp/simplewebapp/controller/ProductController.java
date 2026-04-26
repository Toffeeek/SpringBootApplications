package com.simplewebapp.simplewebapp.controller;


import com.simplewebapp.simplewebapp.model.Product;
import com.simplewebapp.simplewebapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController
{
    @Autowired
    ProductService service;


    @RequestMapping("/products")
    public List<Product> getProducts()
    {
        return service.getProducts();
    }

    @RequestMapping("/products/{ID}")
    public Product getProductById(@PathVariable int ID)
    {
        return service.getProductById(ID);
    }

    @PostMapping("/products")
    public void listProduct(@RequestBody Product p)
    {
        service.listProduct(p);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody Product p)
    {
        service.updateProduct(p);
    }

    @DeleteMapping("/products/{ID}")
    public void deleteProductById(@PathVariable int ID)
    {
        service.deleteProductById(ID);
    }
}
