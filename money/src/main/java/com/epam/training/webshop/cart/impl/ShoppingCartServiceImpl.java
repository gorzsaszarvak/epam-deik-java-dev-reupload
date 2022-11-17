package com.epam.training.webshop.cart.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.cart.exception.UnknownProductException;
import com.epam.training.webshop.coupon.Coupon;
import com.epam.training.webshop.gross.GrossPriceCalculator;
import com.epam.training.webshop.order.Observer;
import com.epam.training.webshop.order.Order;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.product.persistence.repository.ProductRepository;
import com.epam.training.webshop.product.persistence.entitiy.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Cart cart;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final GrossPriceCalculator grossPriceCalculator;
    private final List<Observer> observers;

    @Autowired
    public ShoppingCartServiceImpl(final Cart cart,
                                   final OrderRepository orderRepository,
                                   final ProductRepository productRepository,
                                   final GrossPriceCalculator grossPriceCalculator,
                                   final List<Observer> observers
    ) {
        this.cart = cart;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.grossPriceCalculator = grossPriceCalculator;
        this.observers = observers;
    }

    public ShoppingCartServiceImpl(Cart cart, OrderRepository orderRepository, ProductRepository productRepository, GrossPriceCalculator grossPriceCalculator) {
        this.cart = cart;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.grossPriceCalculator = grossPriceCalculator;
        this.observers = new ArrayList<>();
    }

    @Override
    public List<Product> getProducts() {
        return cart.getProducts();
    }

    @Override
    public void addProduct(Product product) {
        cart.addProduct(product);
    }

    @Override
    public void addProduct(final String productName) {
        productRepository.findProductByName(productName)
                .ifPresentOrElse(cart::addProduct,
                        () -> {
                            throw new UnknownProductException(productName);
                        }
                );
    }

    @Override
    public void removeProduct(Product productToRemove) {
        cart.removeProduct(productToRemove);
    }

    @Override
    public void addCoupon(Coupon coupon) {
        cart.addCoupon(coupon);
    }

    @Override
    public List<Coupon> getCouponsFromBasket() {
        return cart.getCouponsFromBasket();
    }

    @Override
    public double getTotalNetPrice() {
        final double basePrice = getBasePrice();
        final double discount = getDiscountForCoupons();
        return basePrice - discount;
    }

    @Override
    public double getTotalGrossPrice() {
        return grossPriceCalculator.getAggregatedGrossPrice(this);
    }

    @Override
    public Order order() {
        final Order order = new Order(this.getProducts(), this.getTotalNetPrice(), this.getTotalGrossPrice());
        orderRepository.saveOrder(order);
        observers.forEach(observer -> observer.notify(cart));
        return order;
    }

    @Override
    public double getBasePrice() {
        double basePrice = 0;
        for (final Product currentProduct : cart.getProducts()) {
            basePrice += currentProduct.getNetPrice();
        }
        return basePrice;
    }

    @Override
    public double getDiscountForCoupons() {
        double discount = 0;
        for (Coupon coupon : cart.getCouponsFromBasket()) {
//            discount += coupon.getDiscountForProducts(cart.getProducts());
        }
        return discount;
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
}
