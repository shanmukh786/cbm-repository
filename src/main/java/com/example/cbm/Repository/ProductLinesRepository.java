package com.example.cbm.Repository;

import com.example.cbm.Entity.ProductLines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLinesRepository extends JpaRepository<ProductLines,String> {
    ProductLines findByProductLine(String productLines);
}
