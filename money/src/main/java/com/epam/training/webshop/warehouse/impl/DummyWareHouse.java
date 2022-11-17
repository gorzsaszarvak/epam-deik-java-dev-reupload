package com.epam.training.webshop.warehouse.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.product.persistence.entitiy.Product;
import com.epam.training.webshop.warehouse.WareHouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DummyWareHouse implements WareHouse {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyWareHouse.class);

    @Override
    public void registerOrderedProducts(List<Product> products) {
        LOGGER.info("I have registered the order of products {}", products);
    }

    @Override
    public void notify(Cart cart) {
        registerOrderedProducts(cart.getProducts());
    }
}
