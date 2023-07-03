package com.example.cbm.Entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Employees {
    @Id
    private Integer employeeNumber;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private String extension;
    @Column
    private String email;
    @ManyToOne
    @JoinColumn(name="officeCode")
    private Offices offices;
    @Column
    private Integer reportsTo;
    @Column
    private String jobTitle;
}
