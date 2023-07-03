package com.example.cbm.Entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class OrderDetailsId implements Serializable {
    private Integer order_number;
    private String product_code;

    public Integer getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public OrderDetailsId(Integer order_number, String product_code) {
        this.order_number = order_number;
        this.product_code = product_code;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }



    public OrderDetailsId() {
    }
}
