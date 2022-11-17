package com.epam.training.webshop.product.init;

import com.epam.training.webshop.product.config.ProductConfigurations;
import com.epam.training.webshop.product.persistence.entitiy.Product;
import com.epam.training.webshop.product.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
/*
Csak a nem `prod` profil esetén fog létre jönni a Bean.
 */
@Profile("! prod")
@RequiredArgsConstructor
@Slf4j
public class ProductInitializer {

    private final ProductConfigurations productConfigurations;
    private final ProductRepository productRepository;

    @PostConstruct
    public void initProducts() {
        log.info("Save products...");
        final List<Product> products = List.of(
                new Product(productConfigurations.getAppleName(), productConfigurations.getApplePrice(), productConfigurations.getApplePackaging()),
                new Product(productConfigurations.getMelonName(), productConfigurations.getMelonPrice(), productConfigurations.getMelonPackaging()),
                new Product(productConfigurations.getCaviarName(), productConfigurations.getCaviarPrice(), productConfigurations.getCaviarPackaging())
        );
        productRepository.saveAll(products);
        productRepository.findAll().forEach(System.out::println);
        log.info("Saving products process is complete.");
    }
}
