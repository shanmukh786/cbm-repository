package com.example.cbm.Entity;

import jdk.jfr.Enabled;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Entity
public class Customers {
    @Id

    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer customerNumber;

    @Column
    private String customerName;

    @Column
    private String contactLastName;

    @Column
    private String contactFirstName;

    @Column
    private String phone;

    @Column
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String postalCode;

    @Column
    private String country;
    @ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber", referencedColumnName = "employeeNumber")
    private Employees employees;
    @Column
    private BigDecimal creditLimit;
}
