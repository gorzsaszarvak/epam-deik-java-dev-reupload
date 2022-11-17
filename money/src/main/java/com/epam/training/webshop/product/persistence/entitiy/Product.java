package com.epam.training.webshop.product.persistence.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    private double netPrice;
    private String packaging;

    public Product(String name, double netPrice, String packaging) {
        this.name = name;
        this.netPrice = netPrice;
        this.packaging = packaging;
    }
}
