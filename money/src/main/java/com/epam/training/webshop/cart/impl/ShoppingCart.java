package com.epam.training.webshop.cart.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.coupon.Coupon;
import com.epam.training.webshop.product.persistence.entitiy.Product;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShoppingCart implements Cart {

    private final List<Product> products;
    private final List<Coupon> coupons;

    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.coupons = new ArrayList<>();
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public void removeProduct(Product productToRemove) {
        this.products.remove(productToRemove);
    }

    @Override
    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    @Override
    public List<Coupon> getCouponsFromBasket() {
        return coupons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCart that = (ShoppingCart) o;

        return new EqualsBuilder().append(products, that.products).append(coupons, that.coupons).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(products).append(coupons).toHashCode();
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "products=" + products +
                ", coupons=" + coupons +
                '}';
    }
}
