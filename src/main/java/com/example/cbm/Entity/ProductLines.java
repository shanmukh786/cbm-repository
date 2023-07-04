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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "productLine", columnDefinition = "VARCHAR(36)")
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
