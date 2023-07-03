package com.example.cbm.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderNumber;
    @Column
    private Date orderDate;
    @Column
    private Date requiredDate;
    @Column
    private Date shippedDate;
    @Column
    private String status;
    @Column
    @Type(type = "text")
    private String comments;
    @ManyToOne
    @JoinColumn(name = "customerNumber")
    private Customers customers;
}
