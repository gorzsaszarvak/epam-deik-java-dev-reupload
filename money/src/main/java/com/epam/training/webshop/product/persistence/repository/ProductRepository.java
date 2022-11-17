package com.epam.training.webshop.product.persistence.repository;

import com.epam.training.webshop.product.persistence.entitiy.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findProductByName(String name);
}
