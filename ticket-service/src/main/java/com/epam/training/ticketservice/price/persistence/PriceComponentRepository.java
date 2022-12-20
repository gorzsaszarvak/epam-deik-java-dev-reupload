package com.epam.training.ticketservice.price.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceComponentRepository extends JpaRepository<PriceComponent, Long> {
    Optional<PriceComponent> findPriceComponentByName(String name);
}
