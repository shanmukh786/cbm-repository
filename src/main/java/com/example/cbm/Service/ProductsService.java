package com.example.cbm.Service;

import com.example.cbm.Entity.Products;
import com.example.cbm.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Products addProduct(Products products)
    {
        return productRepository.save(products);
    }
}
