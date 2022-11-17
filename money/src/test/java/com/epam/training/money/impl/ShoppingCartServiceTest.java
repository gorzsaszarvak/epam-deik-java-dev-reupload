//package com.epam.training.money.impl;
//
//import com.epam.training.webshop.cart.Cart;
//import com.epam.training.webshop.cart.ShoppingCartService;
//import com.epam.training.webshop.cart.impl.ShoppingCart;
//import com.epam.training.webshop.cart.impl.ShoppingCartServiceImpl;
//import com.epam.training.webshop.gross.impl.GrossPriceCalculatorDecorator;
//import com.epam.training.webshop.order.Observer;
//import com.epam.training.webshop.order.OrderRepository;
//import com.epam.training.webshop.order.confirmation.impl.DummyOrderConfirmationService;
//import com.epam.training.webshop.product.persistence.repository.ProductRepository;
//import com.epam.training.webshop.product.persistence.entitiy.Product;
//import com.epam.training.webshop.warehouse.impl.DummyWareHouse;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//class ShoppingCartServiceTest {
//
//    private static final String APPLE_PRODUCT_NAME = "Alma";
//    public static final String WEIGHT_PACKAGING = "1kg";
//    private static final Product ALMA = Product.builder().name(APPLE_PRODUCT_NAME)
//            .netPrice(100)
//            .packaging(WEIGHT_PACKAGING)
//            .build();
//    private static final Product DINNYE = Product.builder()
//            .name("Dinnye")
//            .netPrice(199)
//            .packaging(WEIGHT_PACKAGING)
//            .build();
//
//    @Mock
//    private GrossPriceCalculatorDecorator grossPriceCalculatorDecorator;
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private Cart cart;
//
//
//    private ShoppingCartService underTest;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        underTest = new ShoppingCartServiceImpl(
//                cart,
//                orderRepository,
//                productRepository,
//                grossPriceCalculatorDecorator
//        );
//    }
//
//    @Test
//    void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
//        // Given
//        // When
//        List<Product> actualResult = underTest.getProducts();
//        // Then
//        Assertions.assertEquals(Collections.emptyList(), actualResult);
//    }
//
//    @Test
//    void testListProductShouldReturnTheListOfProductsWhenNotEmpty() {
//        // Given
//        List<Product> products = Collections.singletonList(ALMA);
//        BDDMockito.given(cart.getProducts()).willReturn(products);
//        // When
//        final List<Product> actual = underTest.getProducts();
//        // Then
//        Assertions.assertEquals(products, actual);
//    }
//
//    @Test
//    void testAddProductShouldAddProductWhenGivenOne() {
//        // Given
//        Cart cart = new ShoppingCart();
//        underTest = new ShoppingCartServiceImpl(cart, orderRepository, productRepository, grossPriceCalculatorDecorator);
//        BDDMockito.given(productRepository.getProductByName(APPLE_PRODUCT_NAME)).willReturn(Optional.of(ALMA));
//        // When
//        underTest.addProduct(APPLE_PRODUCT_NAME);
//        // Then
//        Assertions.assertEquals(List.of(ALMA), cart.getProducts());
//    }
//
//    @Test
//    void testOrderShouldCallSaveOrderOnOrderRepositoryWhenCallOrder() {
//        // Given
//        Observer confirmationService = Mockito.mock(DummyOrderConfirmationService.class);
//        Observer wareHouse = Mockito.mock(DummyWareHouse.class);
//        final List<Observer> observers = List.of(confirmationService, wareHouse);
//        underTest = new ShoppingCartServiceImpl(cart, orderRepository, productRepository, grossPriceCalculatorDecorator, observers);
//        // When
//        underTest.order();
//        // Then
//        /*
//        A Mockito.times(1) VerificationMode-t elhagyhatjuk, ha a hívások elvárt száma egy.
//        Mockito.verify(orderRepository).saveOrder(ArgumentMatchers.any());
//         */
//        Mockito.verify(orderRepository, Mockito.times(1)).saveOrder(ArgumentMatchers.any());
//        Mockito.verify(confirmationService).notify(cart);
//        Mockito.verify(wareHouse).notify(cart);
//    }
//
//    @Test
//    void testGetTotalGrossPriceShouldReturnAggregatedPriceOfGivenCart() {
//        // Given
//        double aggregatedPrice = 999.1;
//        BDDMockito.given(grossPriceCalculatorDecorator.getAggregatedGrossPrice(underTest)).willReturn(aggregatedPrice);
//        // When
//        double actual = underTest.getTotalGrossPrice();
//        // Then
//        Assertions.assertEquals(aggregatedPrice, actual);
//    }
//
//    @Test
//    void testGetTotalNetPriceShouldReturnAggregatedPriceOfProductsWhenGivenCartWithProducts() {
//        // Given
//        final List<Product> products = List.of(ALMA, DINNYE);
//        BDDMockito.given(cart.getProducts()).willReturn(products);
//        // When
//        final double actual = underTest.getTotalNetPrice();
//        // Then
//        Assertions.assertEquals(299, actual);
//    }
//
//    @Test
//    void testGetDiscountForCouponsShouldReturnZeroWhenGivenCartWithoutAnyCoupons() {
//        // Given
//        // When
//        final double actual = underTest.getDiscountForCoupons();
//        // Then
//        Assertions.assertEquals(0, actual);
//    }
//
//    @Test
//    void testSubscribeShouldAddNewObserverWhenGivenOne() {
//        // Given
//        final List<Observer> observers = Mockito.spy(new ArrayList<>());
//        underTest = new ShoppingCartServiceImpl(cart, orderRepository, productRepository, grossPriceCalculatorDecorator, observers);
//        Observer confirmationService = new DummyOrderConfirmationService();
//        // When
//        underTest.subscribe(confirmationService);
//        // Then
//        Mockito.verify(observers).add(confirmationService);
//    }
//}