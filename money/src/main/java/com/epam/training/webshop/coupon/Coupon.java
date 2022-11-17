package com.epam.training.webshop.coupon;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double value;
}
