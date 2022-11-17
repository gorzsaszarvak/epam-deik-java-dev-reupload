package com.epam.training.webshop.example;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.product.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
Példa osztály, amely bemutatja a DI stratégiákat.
 */
@Component
public class SpringDependencyInjectionExample {

    /*
        Közvetlenül a mezőbe injektálja/fecskendezi a függőséget a Spring, reflexió segítségével.
        Ha tudjuk, kerüljük el ezt a megközelítést.
     */
    @Autowired
    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    private final Cart cart;

    /*
        A konstroktur alapú függőség injektálás/be fecskendezés ajánlott használatra.

        Amennyiben egynél több konstruktúra van az osztálynak, akkor kötelező az @Autowired-al fel annotálni
        azt amelyiket a Spring-nek használnia kell.
        Ha csak egy konstroktúr van akkor elhagyható az @Autowired használata.
     */
    @Autowired
    public SpringDependencyInjectionExample(Cart cart) {
        this.cart = cart;
    }

    public SpringDependencyInjectionExample(ProductRepository productRepository, OrderRepository orderRepository, Cart cart) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.cart = cart;
    }

    /*
        A setter metódus segítségével injektálja/fecskendezi be a függőséget a Spring, ha lehetséges kerüljök el ezt a megközelítést is.
     */
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
