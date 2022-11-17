package com.epam.training.webshop.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.epam.training.webshop")
@EntityScan("com.epam.training.webshop")
public class JpaConfig {
}
