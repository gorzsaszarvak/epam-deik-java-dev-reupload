package com.epam.training.webshop.config;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.ShoppingCartService;
import com.epam.training.webshop.cart.impl.ShoppingCartServiceImpl;
import com.epam.training.webshop.gross.GrossPriceCalculator;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.order.confirmation.OrderConfirmationService;
import com.epam.training.webshop.product.persistence.repository.ProductRepository;
import com.epam.training.webshop.warehouse.WareHouse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ApplicationConfig {

    @Bean
    public static ShoppingCartService cart(
            final Cart cart,
            final WareHouse wareHouse,
            final OrderConfirmationService orderConfirmationService,
            final OrderRepository orderRepository,
            final ProductRepository productRepository,
            @Qualifier("hungarianGrossPriceCalculator") final GrossPriceCalculator grossPriceCalculator
    ) {
        final ShoppingCartService shoppingCart = new ShoppingCartServiceImpl(cart, orderRepository, productRepository, grossPriceCalculator, new ArrayList<>());
        shoppingCart.subscribe(wareHouse);
        shoppingCart.subscribe(orderConfirmationService);
        return shoppingCart;
    }
}
