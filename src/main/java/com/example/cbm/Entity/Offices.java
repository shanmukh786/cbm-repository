package com.example.cbm.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Offices {
    @Id
    @GeneratedValue(generator = "randomStringGenerator")
    @GenericGenerator(name = "randomStringGenerator", strategy = "com.example.cbm.Controller.RandomStringGenerator")
    @Column(name = "officeCode", length = 4)
    private String officeCode;
    @Column
    private String city;
    @Column
    private String Phone;
    @Column
    private String addressLine1;
    @Column
    private String addressLine2;
    @Column
    private String state;
    @Column
    private String country;
    @Column
    private String postalCode;
    @Column
    private String territory;
}
