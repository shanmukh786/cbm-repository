package com.example.cbm.DTO;

import com.example.cbm.Entity.Orders;
import com.example.cbm.Entity.Payments;

public class CustomerOrderPaymentDetails {
    private Orders order;
    private Payments payment;

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Payments getPayment() {
        return payment;
    }

    public void setPayment(Payments payment) {
        this.payment = payment;
    }

    public CustomerOrderPaymentDetails(Orders order, Payments payment) {
        this.order = order;
        this.payment = payment;
    }
}
