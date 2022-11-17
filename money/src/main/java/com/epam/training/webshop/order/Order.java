package com.epam.training.webshop.order;

import com.epam.training.webshop.product.persistence.entitiy.Product;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class Order {

    private final List<Product> orderedProducts;
    private final double totalNetPrice;
    private final double totalGrossPrice;

    public Order(final List<Product> orderedProducts, final double totalNetPrice, final double totalGrossPrice) {
        this.orderedProducts = orderedProducts;
        this.totalNetPrice = totalNetPrice;
        this.totalGrossPrice = totalGrossPrice;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public double getTotalNetPrice() {
        return totalNetPrice;
    }

    public double getTotalGrossPrice() {
        return totalGrossPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        return new EqualsBuilder().append(totalNetPrice, order.totalNetPrice).append(totalGrossPrice, order.totalGrossPrice).append(orderedProducts, order.orderedProducts).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(orderedProducts).append(totalNetPrice).append(totalGrossPrice).toHashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderProducts=" + orderedProducts +
                ", totalNetPrice=" + totalNetPrice +
                ", totalGrossPrice=" + totalGrossPrice +
                '}';
    }
}
