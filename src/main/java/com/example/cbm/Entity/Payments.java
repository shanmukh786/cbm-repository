package com.example.cbm.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class Payments {
    @Id
    private String checkNumber;
    @ManyToOne
    @JoinColumn(name = "customerNumber")
    private Customers customers;
    @Column
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date paymentDate;
    @Column
    private BigDecimal amount;
}
