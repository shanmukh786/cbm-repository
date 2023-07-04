package com.example.cbm.Controller;

import com.example.cbm.Entity.ProductLines;
import com.example.cbm.Service.ProductLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/product_lines")
public class ProductLinesController {

    ProductLinesService productLinesService;
    @Autowired
    public void setProductLinesService(ProductLinesService productLinesService) {
        this.productLinesService = productLinesService;
    }
    @PostMapping
    public ProductLines addProductLines(@RequestBody ProductLines productLines)
    {
        return productLinesService.addProductLine(productLines);
    }
    @PutMapping("/product_line /{product_line}/text_description/ {text_description}")
    public String updateProductLineTextDescription(@PathVariable String product_line, @PathVariable String text_description) {
        productLinesService.updateTextDescription(product_line, text_description);
        return "product’s MSRP updated successfully";
    }

}
