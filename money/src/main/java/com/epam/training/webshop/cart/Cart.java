package com.epam.training.webshop.cart;

import com.epam.training.webshop.coupon.Coupon;
import com.epam.training.webshop.product.persistence.entitiy.Product;

import java.util.List;

public interface Cart {

    List<Product> getProducts();

    void addProduct(Product product);

    void removeProduct(Product productToRemove);

    void addCoupon(Coupon coupon);

    List<Coupon> getCouponsFromBasket();

}
