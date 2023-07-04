package com.example.cbm.Repository;

import com.example.cbm.Entity.Products;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products,String> {
    Products findByProductCode(String code);
    Products findByProductName(String name);
}
