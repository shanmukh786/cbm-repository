package com.example.cbm.Controller;

import com.example.cbm.Entity.Payments;
import com.example.cbm.Entity.Products;
import com.example.cbm.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductsController {

    ProductsService productsService;
    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }
    @PostMapping(value = "/")
    public ResponseEntity<String> saveProducts(@RequestBody Products products)
    {
        productsService.addProduct(products);
        return new ResponseEntity<String>("product details added successfully", HttpStatus.CREATED);
    }
}
