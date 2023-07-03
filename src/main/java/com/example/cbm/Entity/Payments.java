package com.example.cbm.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "checkNumber", columnDefinition = "VARCHAR(36)")
    private String checkNumber;
    @ManyToOne
    @JoinColumn(name = "customerNumber")
    private Customers customers;
    @Column
    private Date paymentDate;
    @Column
    private BigDecimal amount;
}
