package com.epam.training.webshop.warehouse;

import com.epam.training.webshop.order.Observer;
import com.epam.training.webshop.product.persistence.entitiy.Product;

import java.util.List;

public interface WareHouse extends Observer {

    void registerOrderedProducts(List<Product> products);
}
