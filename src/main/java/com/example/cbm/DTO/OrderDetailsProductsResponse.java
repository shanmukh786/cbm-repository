package com.example.cbm.DTO;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.OrderDetails;
import com.example.cbm.Entity.Orders;
import com.example.cbm.Entity.Products;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailsProductsResponse {

    private OrderDetails orderDetails;
    private List<Products> products;

    // Constructor, getters, and setters

    public OrderDetailsProductsResponse(OrderDetails orderDetails, List<Products> products) {
        this.orderDetails= orderDetails;
        this.products = products;
    }
}
