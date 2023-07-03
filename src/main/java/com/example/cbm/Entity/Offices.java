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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "officeCode", columnDefinition = "VARCHAR(36)")
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
