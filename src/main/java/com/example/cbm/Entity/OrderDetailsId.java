package com.example.cbm.Entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class OrderDetailsId implements Serializable {
    private Integer orderNumber;
    private String productCode;

    public Integer getOrder_number() {
        return orderNumber;
    }

    public void setOrder_number(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProduct_code() {
        return productCode;
    }

    public void setProduct_code(String productCode) {
        this.productCode = productCode;
    }

    public OrderDetailsId(Integer orderNumber, String productCode) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
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
