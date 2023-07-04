package com.example.cbm.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductLines {
    @Id
    @GeneratedValue(generator = "randomStringGenerator")
    @GenericGenerator(name = "randomStringGenerator", strategy = "com.example.cbm.Controller.RandomStringGenerator")
    @Column(name = "productLine", length = 7)
    private String productLine;

    @Column
    private String textDescription;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String htmlDescription;

    @Lob
    @Column
    @Type(type="org.hibernate.type.ImageType")
    private byte[] image;
}
