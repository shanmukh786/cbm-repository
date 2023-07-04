package com.example.cbm.Service;

import com.example.cbm.Entity.ProductLines;
import com.example.cbm.Repository.ProductLinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;

@Service
public class ProductLinesService {

    ProductLinesRepository productLinesRepository;
    @Autowired
    public void setProductLinesRepository(ProductLinesRepository productLinesRepository) {
        this.productLinesRepository = productLinesRepository;
    }
    public ProductLines addProductLine(ProductLines productLines)
    {
        return productLinesRepository.save(productLines);
    }
    public void updateTextDescription(String productLine, String newTextDescription) {
        ProductLines productLineEntity = productLinesRepository.findByProductLine(productLine);
        if (productLineEntity != null) {
            productLineEntity.setTextDescription(newTextDescription);
            productLinesRepository.save(productLineEntity);
        }
    }
}
