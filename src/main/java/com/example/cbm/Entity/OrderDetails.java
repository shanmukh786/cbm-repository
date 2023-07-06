package com.example.cbm.Entity;

import lombok.*;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class OrderDetails {
    @EmbeddedId
    private OrderDetailsId id;

    @MapsId("orderNumber")
    @ManyToOne
    @JoinColumn(name = "orderNumber")
    private Orders order;

    @MapsId("productCode")
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Products product;
    @Column
    private Integer quantityOrdered;
    @Column
    private BigDecimal priceEach;
    @Column
    private Short orderLineNumber;
}
