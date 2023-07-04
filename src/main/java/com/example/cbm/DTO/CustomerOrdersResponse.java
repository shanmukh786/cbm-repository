package com.example.cbm.DTO;

import com.example.cbm.Entity.Customers;
import com.example.cbm.Entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CustomerOrdersResponse {
    private Customers customer;
    private List<Orders> orders;

    // Constructor, getters, and setters

    public CustomerOrdersResponse(Customers customer, List<Orders> orders) {
        this.customer = customer;
        this.orders = orders;
    }
}
